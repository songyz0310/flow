package org.flow.boot.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * By using command pattern to provide FTP basic operation: mkdir, rmdir, delete
 * file, put files, get files
 * 
 * Invoke use execute interface to run FTP command, if any one command failed,
 * following command will not be executed
 * 
 * Pay attention to: should configure following parameter in properties file and
 * put to classpath:/config ftp.server.ip ftp.server.username
 * ftp.server.password ftp.server.encoding ftp.server.port ftp.server.workpath
 * ftp.server.mode
 * 
 * @author songyz
 */
public class FTPUtil {

	private static final Logger log = LoggerFactory.getLogger(FTPUtil.class);

	private static int BUFF_SIZE = 256000;

	/**
	 * if start with '/', consider that is absolute path, all directory will be
	 * created from root if start is './', system will create new directory
	 * under working path
	 * 
	 * @param relativePath
	 * @return
	 */
	public static FTPCommand createMakeDirCmd(String relativePath) {
		return new MakeDirCommand(relativePath);
	}

	/**
	 * delete remote path, if path doesn't exist, don't report error all
	 * children files and directory are all deleted
	 * 
	 * @param relativePath
	 *            can be relative path with current working path
	 * @return
	 */
	public static FTPCommand createDelDirCmd(String relativePath) {
		return new DeleteDirCommand(relativePath);
	}

	/**
	 * delete special file from FTP server, if file doesn't exist, don't report
	 * error
	 * 
	 * @param relativeFilePath
	 *            can be relative path with current working path
	 * @return
	 */
	public static FTPCommand createDelFileCmd(String relativeFilePath) {
		return new DeleteFileCommand(relativeFilePath);
	}

	/**
	 * upload files to FTP server
	 * 
	 * @param localFilePath
	 *            absolute file path
	 * @param relativePath
	 *            can be absolute path or relative path with current working
	 *            path
	 * @return
	 */
	public static FTPCommand createPutFileCmd(String[] localFilePath, String relativePath) {
		return new PutFileCommand(localFilePath, null, relativePath);
	}

	/**
	 * upload files to FTP server
	 * 
	 * @param localFilePath
	 *            absolute file path
	 * @param newNames
	 *            new name list
	 * @param relativePath
	 *            can be absolute path or relative path with current working
	 *            path
	 * @return
	 */
	public static FTPCommand createPutFileCmd(String[] localFilePath, String[] newNames, String relativePath) {
		return new PutFileCommand(localFilePath, newNames, relativePath);
	}

	/**
	 * upload files to FTP server, after upload input stream will be closed, but
	 * if upload failed, invoker should close input stream himself.
	 * 
	 * @param localFiles
	 *            input stream
	 * @param relativeDir
	 *            can be absolute path or relative path with current working
	 *            path
	 * @return
	 */
	public static FTPCommand createPutStreamCmd(Map<String, InputStream> localFiles, String relativeDir) {
		return new PutStreamCommand(localFiles, relativeDir);
	}

	/**
	 * download file to special local path
	 * 
	 * @param relativeFilePaths
	 * @param localDir
	 * @return
	 */
	public static FTPCommand createGetFileCmd(String[] relativeFilePaths, String localDir) {
		return new GetFileCommand(relativeFilePaths, localDir);
	}

	/**
	 * download all files under remote directory to special local path
	 * 
	 * @param remoteFilePath
	 * @param localDir
	 * @return
	 */
	public static FTPCommand createGetDirFilesCmd(String[] relativePaths, String localDir) {
		return new GetDirFilesCommand(relativePaths, localDir);
	}

	/**
	 * copy file from ftp server source path to target path
	 * 
	 * @param srcFiles
	 * @param targetDir
	 * @return
	 */
	public static FTPCommand createCopyFileCmd(String[] srcFiles, String targetDir) {
		return new CopyFileCommand(srcFiles, targetDir);
	}

	public static FTPCommand createGetInputStringCommand(String relativePath, InputStream in) {
		return new GetInputStringCommand(relativePath, in);
	}

	public static FTPCommand createGetOutputStreamCommand(String remoteDir, OutputStream out) {
		return new GetOutputStreamCommand(remoteDir, out);
	}

	public static boolean existDir(String dir) {
		boolean flag = true;
		try {
			execute(new FTPCommand() {
				public boolean doAction(FTPClient client) {
					try {
						return client.changeWorkingDirectory(dir);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						return false;
					}
				}

			});
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}

	/**
	 * execute command list which are created by using createXXXX method if one
	 * command meet error, following command will NOT be executed Invoker can
	 * check command successes flag to know which command failed
	 * 
	 * @param cmds
	 * @throws Exception
	 */
	public static void execute(FTPCommand... cmds) throws Exception {
		FTPClient client = null;
		try {
			client = openConnection();
			String mode = PropertiesUtil.getValue("ftp.server.mode", "localactive");
			String workpath = PropertiesUtil.getValue("ftp.server.workpath", "./");
			for (FTPCommand cmd : cmds) {
				client.changeWorkingDirectory(workpath);
				if (mode.equals("localactive"))
					client.enterLocalActiveMode();
				else if (mode.equals("localpassive"))
					client.enterLocalPassiveMode();
				else
					client.enterRemotePassiveMode();

				cmd.isOK = cmd.doAction(client);
				if (cmd.isOK == false) {
					if (cmd.error == null || cmd.error.isEmpty()) {
						String clsName = cmd.getClass().getName();
						cmd.error = "execute command:" + clsName.substring(clsName.lastIndexOf("$") + 1) + " failed -- "
								+ client.getReplyString();
					}
					throw new Exception(cmd.error);
				}
			}
		} finally {
			closeConnection(client);
		}
	}

	public static FTPClient openConnection() throws Exception {
		// get configuration parameter
		String ip = PropertiesUtil.getValue("ftp.server.ip", "");
		String userName = PropertiesUtil.getValue("ftp.server.username", "");
		String password = PropertiesUtil.getValue("ftp.server.password", "");
		String encoding = PropertiesUtil.getValue("ftp.server.encoding", "UTF-8");
		int port = PropertiesUtil.getValue("ftp.server.port", 21);
		String workPath = PropertiesUtil.getValue("ftp.server.workpath", "./");

		FTPClient client = new FTPClient();
		client.setDefaultPort(port);
		client.setConnectTimeout(30000);
		client.setDataTimeout(180000);
		client.setControlKeepAliveTimeout(60);
		client.setControlKeepAliveReplyTimeout(60);
		client.setControlEncoding(encoding);
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode("UTF-8");
		client.configure(ftpConfig);

		try {
			client.connect(ip);
		} catch (SocketException exp) {
			log.warn("connect timeout with FTP server:" + ip);
			throw new Exception(exp.getMessage());
		} catch (IOException exp) {
			log.warn("connect FTP server:" + ip + " meet error:" + exp.getMessage());
			throw new Exception(exp.getMessage());
		}

		int reply = client.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			log.warn("FTP server refused refused connection.");
			throw new Exception("login failed with FTP server:" + ip + " may user name and password is wrong");
		}
		client.login(userName, password);

		// set communication mode
		client.setFileType(FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.COMPRESSED_TRANSFER_MODE);

		client.changeWorkingDirectory(workPath);
		return client;
	}

	private static void closeConnection(FTPClient client) {
		try {
			if (client == null)
				return;

			client.logout();
			client.disconnect();
		} catch (Exception exp) {
			log.warn("close ftp connection meet error:" + exp.getMessage());
		}
	}

	private static void createPath(FTPClient client, String pathName) throws IOException, Exception {
		pathName = pathName.replace("\\", "/");
		if (pathName.startsWith("/"))
			client.changeWorkingDirectory("/");

		String[] paths = pathName.trim().split("/");
		for (String path : paths) {
			if (path.equals(".") || path.isEmpty())
				continue;

			if (client.changeWorkingDirectory(path))
				continue;
			else if (client.makeDirectory(path))
				client.changeWorkingDirectory(path);
			else
				throw new Exception("failed to create directory:" + path);
		}
	}

	public static abstract class FTPCommand {
		protected boolean isOK = false;
		protected String error = "";

		public abstract boolean doAction(FTPClient client);

		public boolean isSuccessed() {
			return isOK;
		}

		public String getError() {
			return error;
		}
	}

	/**
	 * if remote path is started with '/', current working path will move to
	 * root path first else if is started with './' or path name, current
	 * working path will be configured path all child paths will be created from
	 * root path to leaf path.
	 */
	static class MakeDirCommand extends FTPCommand {
		private String relativePath;

		public MakeDirCommand(String relativePath) {
			this.relativePath = relativePath;
		}

		public boolean doAction(FTPClient client) {
			try {
				createPath(client, relativePath);
				return true;
			} catch (Exception exp) {
				error = "failed to create dirctory " + relativePath + " because " + exp;
				log.warn(error, exp);
				return false;
			}
		}
	}

	/**
	 * Delete remote path, if path doesn't exist, still return true The children
	 * files and directory will be all deleted
	 */
	static class DeleteDirCommand extends FTPCommand {
		private String relativePath;

		public DeleteDirCommand(String relativePath) {
			this.relativePath = relativePath;
		}

		public boolean doAction(FTPClient client) {
			try {
				FTPFile[] files = client.listFiles(relativePath);
				if (files == null || files.length == 0)
					return client.removeDirectory(relativePath);

				for (FTPFile file : files) {
					if (file.isDirectory())
						deleteChildren(client, relativePath + "/" + file.getName());
					else
						client.dele(relativePath + "/" + file.getName());
				}
				return client.removeDirectory(relativePath);
			} catch (Exception exp) {
				error = "failed to delete dirctory " + relativePath + " in FTP server because " + exp.getMessage();
				log.warn(error, exp);
				return false;
			}
		}

		private boolean deleteChildren(FTPClient client, String pathName) {
			try {
				FTPFile[] files = client.listFiles(pathName);
				if (files == null || files.length == 0)
					return client.removeDirectory(pathName);

				for (FTPFile file : files) {
					if (file.isDirectory())
						deleteChildren(client, pathName + "/" + file.getName());
					else
						client.dele(pathName + "/" + file.getName());
				}
				return client.removeDirectory(pathName);
			} catch (Exception exp) {
				error = "failed to delete dirctory " + relativePath + "in FTP server because " + exp.getMessage();
				log.warn(error, exp);
				return false;
			}
		}
	}

	/**
	 * 
	 * delete remote file, if it doesn't exist, still return true
	 */
	static class DeleteFileCommand extends FTPCommand {
		private String relativeFilePath;

		public DeleteFileCommand(String relativeFilePath) {
			this.relativeFilePath = relativeFilePath;
		}

		@Override
		public boolean doAction(FTPClient client) {
			try {
				FTPFile[] files = client.listFiles(relativeFilePath);
				if (files == null || files.length == 0)
					return true;
				else
					return client.deleteFile(relativeFilePath);
			} catch (Exception exp) {
				error = "failed to delete file " + relativeFilePath + " in FTP server because " + exp.getMessage();
				log.warn(error, exp);
				return false;
			}
		}
	}

	static class PutFileCommand extends FTPCommand {
		private String[] localFiles;
		private String[] newNames;
		private String relativePath;

		public PutFileCommand(String[] localFiles, String[] newNames, String relativePath) {
			this.localFiles = localFiles;
			this.newNames = newNames;
			this.relativePath = relativePath;
		}

		@Override
		public boolean doAction(FTPClient client) {
			if (localFiles == null || localFiles.length == 0)
				return true;

			try {
				// make remote directory tree and change working directory to
				// that path
				if (client.changeWorkingDirectory(relativePath) == false)
					createPath(client, relativePath);

				for (int i = 0; i < localFiles.length; i++) {
					String localFile = localFiles[i];
					File newFile = new File(localFile.replace("\\", "/"));
					if (newFile.exists() && newFile.canRead()) {
						InputStream input = null;
						try {
							input = new BufferedInputStream(new FileInputStream(newFile));
							boolean isOK = false;
							client.setBufferSize(BUFF_SIZE);
							if (newNames == null || newNames.length - 1 < i)
								isOK = client.storeFile(newFile.getName(), input);
							else
								isOK = client.storeFile(newNames[i], input);
							input.close();
							if (isOK == false)
								return false;
						} catch (Exception exp) {
							error = "failed to upload file " + localFile + " to FTP server:" + exp.getMessage();
							log.warn(error, exp);
							return false;
						} finally {
							CommonUtil.closeIO(input);
						}
					} else {
						error = "don't found local file:" + localFile;
						log.warn(error);
						return false;
					}
				}

				return true;
			} catch (Exception exp) {
				log.error("unkown exception when upload file to FTP server:", exp);
				return false;
			}
		}
	}

	static class PutStreamCommand extends FTPCommand {
		private Map<String, InputStream> localFiles;
		private String relativeDir;

		public PutStreamCommand(Map<String, InputStream> localFiles, String relativeDir) {
			this.localFiles = localFiles;
			this.relativeDir = relativeDir;
		}

		public boolean doAction(FTPClient client) {
			try {
				// make remote directory tree and change working directory to
				// that path
				if (client.changeWorkingDirectory(relativeDir) == false)
					createPath(client, relativeDir);

				for (Map.Entry<String, InputStream> localFile : localFiles.entrySet()) {
					if (localFile.getValue() != null) {
						InputStream input = null;
						try {
							client.setBufferSize(BUFF_SIZE);
							input = new BufferedInputStream(localFile.getValue(), BUFF_SIZE / 2);
							client.storeFile(localFile.getKey(), input);
						} catch (Exception exp) {
							error = "failed to upload file " + localFile + " to FTP server:" + exp.getMessage();
							log.warn(error);
							return false;
						} finally {
							CommonUtil.closeIO(input);
						}
					} else {
						error = "don't found local file:" + localFile;
						log.warn(error);
						return false;
					}
				}

				localFiles.clear();
				return true;
			} catch (Exception exp) {
				log.error("unkown exception when upload file to FTP server:", exp);
				return false;
			}
		}
	}

	static class GetFileCommand extends FTPCommand {
		private String[] relativeFilePaths;
		private String localDir;

		public GetFileCommand(String[] relativeFilePaths, String localDir) {
			this.relativeFilePaths = relativeFilePaths;
			this.localDir = localDir;
		}

		public boolean doAction(FTPClient client) {
			for (String remoteFile : relativeFilePaths) {
				OutputStream out = null;
				try {
					client.setBufferSize(BUFF_SIZE);
					File tmp = new File(remoteFile);
					out = new BufferedOutputStream(
							new FileOutputStream(new File(localDir + File.separatorChar + tmp.getName())),
							BUFF_SIZE / 2);
					client.retrieveFile(remoteFile, out);
				} catch (Exception exp) {
					error = "failed to get file:" + remoteFile + " because " + exp.getMessage();
					log.warn(error, exp);
					return false;
				} finally {
					CommonUtil.closeIO(out);
				}
			}
			return true;
		}
	}

	static class GetOutputStreamCommand extends FTPCommand {
		private String remoteFile;
		private OutputStream out;

		public GetOutputStreamCommand(String remoteFile, OutputStream out) {
			this.remoteFile = remoteFile;
			this.out = out;
		}

		@Override
		public boolean doAction(FTPClient client) {
			try {
				client.setBufferSize(BUFF_SIZE);
				client.retrieveFile(remoteFile, out);
			} catch (Exception exp) {
				error = "failed to get file:" + remoteFile + " because " + exp.getMessage();
				log.warn(error, exp);
				return false;
			} finally {
				CommonUtil.closeIO(out);
			}
			return true;
		}
	}

	static class GetDirFilesCommand extends FTPCommand {
		private String[] relativePaths;
		private String localDir;

		public GetDirFilesCommand(String[] relativePaths, String localDir) {
			this.relativePaths = relativePaths;
			this.localDir = localDir;
		}

		@Override
		public boolean doAction(FTPClient client) {
			client.setBufferSize(BUFF_SIZE);
			for (String relativePath : relativePaths) {
				OutputStream out = null;
				try {
					client.changeWorkingDirectory(relativePath);
					FTPFile[] files = client.listFiles();
					for (FTPFile file : files) {
						out = new BufferedOutputStream(
								new FileOutputStream(new File(localDir + File.separatorChar + file.getName())),
								BUFF_SIZE / 2);
						client.retrieveFile(file.getName(), out);
						out.flush();
					}
				} catch (Exception exp) {
					error = "failed to get file:" + relativePath + " because " + exp.getMessage();
					log.warn(error, exp);
					return false;
				} finally {
					CommonUtil.closeIO(out);
				}
			}
			return true;
		}
	}

	static class CopyFileCommand extends FTPCommand {
		private String[] srcFiles;
		private String targetDir;

		public CopyFileCommand(String[] srcFiles, String targetDir) {
			this.srcFiles = srcFiles;
			this.targetDir = targetDir;
		}

		@Override
		public boolean doAction(FTPClient client) {
			for (String file : srcFiles) {
				InputStream in = null;
				OutputStream out = null;
				try {
					client.setBufferSize(BUFF_SIZE);
					File tempFile = File.createTempFile("tc" + this.hashCode(), "log." + System.nanoTime());
					out = new BufferedOutputStream(new FileOutputStream(tempFile));
					client.retrieveFile(file, out);
					out.flush();
					out.close();
					String name = file.substring(file.lastIndexOf("/"));
					in = new BufferedInputStream(new FileInputStream(tempFile));
					boolean isOK = client.storeFile(targetDir + name, in);
					if (isOK == false)
						throw new Exception("can't upload file");
					else
						tempFile.delete();
				} catch (Exception exp) {
					error = "failed to copy file:" + file + " because " + exp.getMessage();
					log.warn(error, exp);
					return false;
				} finally {
					CommonUtil.closeIO(in);
					CommonUtil.closeIO(out);
				}
			}
			return true;
		}
	}

	/**
	 * if remote path is started with '/', current working path will move to
	 * root path first else if is started with './' or path name, current
	 * working path will be configured path all child paths will be created from
	 * root path to leaf path.
	 */
	public static class GetInputStringCommand extends FTPCommand {
		private String relativePath;
		private InputStream in;

		public GetInputStringCommand(String relativePath, InputStream in) {
			this.relativePath = relativePath;
			this.in = in;
		}

		@Override
		public boolean doAction(FTPClient client) {
			try {
				in = client.retrieveFileStream(relativePath);
				return true;
			} catch (Exception exp) {
				error = "failed to get InputStream " + relativePath + " because " + exp;
				log.warn(error, exp);
				return false;
			}
		}

		public InputStream getIn() {
			return in;
		}

	}

}
