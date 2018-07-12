package org.flow.boot.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.util.JavaRequest.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JavaHttpUtil {

	private static final Logger log = LoggerFactory.getLogger(JavaHttpUtil.class);
	private static final Integer CONNECT_TIMEOUT = 30_00000;
	private static final Integer READ_TIMEOUT = 30_00000;

	private static TrustManager defaultTrust = new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	};

	private static SSLSocketFactory sslFactory;

	private static HostnameVerifier hostVerifier = new HostnameVerifier() {
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	};

	/**
	 * 发送请求
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static String send(String url, JavaRequest request) {
		String result = null;
		request = request == null ? new JavaRequest() : request;
		if (request.getRequestMethod() == null) {
			request.setRequestMethod(RequestMethod.GET);
		}
		switch (request.getRequestMethod()) {
		case GET:
			result = sendGet(url, request);
			break;
		case POST:
			result = sendPost(url, request);
			break;
		case PUT:
			result = sendPut(url, request);
			break;
		default:
			break;
		}

		return result;
	}

	public static String sendGet(String url, JavaRequest request) {
		request = Objects.isNull(request) ? new JavaRequest() : request;
		HttpURLConnection conn = null;
		try {
			if (StringUtils.isNotEmpty(request.getParamStr())) {
				url = url + "?" + request.getParamStr();
			}
			conn = getConnection(url, request);
			String responseBody = getResponseBody(conn);
			int code = conn.getResponseCode();
			if (code >= 400)
				log.error("access " + conn.getURL() + " meet error:" + conn.getResponseMessage());

			if (code == 400) {
				throw new RuntimeException(responseBody);
			} else if (code == 401) {
				throw new RuntimeException(responseBody);
			}
			return responseBody;
		} catch (KeyManagementException | NoSuchAlgorithmException | MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (Objects.nonNull(conn))
				conn.disconnect();
		}
	}

	public static String sendPost(String url, JavaRequest request) {
		request = Objects.isNull(request) ? new JavaRequest() : request;
		PrintWriter pw = null;
		HttpURLConnection conn = null;
		try {
			conn = getConnection(url, request);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			pw = new PrintWriter(conn.getOutputStream());
			String content = StringUtils.isEmpty(request.getParamStr()) ? request.getContent() : request.getParamStr();
			pw.write(content);
			pw.flush();
			String responseBody = getResponseBody(conn);
			int code = conn.getResponseCode();
			if (code >= 400)
				log.error("http code:[" + code + "], access " + conn.getURL() + " meet error:"
						+ conn.getResponseMessage());

			if (code == 400) {
				throw new RuntimeException(responseBody);
			} else if (code == 401) {
				throw new RuntimeException(responseBody);
			}
			return responseBody;
		} catch (KeyManagementException | NoSuchAlgorithmException | MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (Objects.nonNull(conn))
				conn.disconnect();

			if (Objects.nonNull(pw))
				pw.close();
		}
	}

	public static String sendPut(String url, JavaRequest request) {
		request = Objects.isNull(request) ? new JavaRequest() : request;
		PrintWriter pw = null;
		HttpURLConnection conn = null;
		try {
			conn = getConnection(url, request);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			pw = new PrintWriter(conn.getOutputStream());
			String content = StringUtils.isEmpty(request.getParamStr()) ? request.getContent() : request.getParamStr();
			pw.write(content);
			pw.flush();
			String responseBody = getResponseBody(conn);
			int code = conn.getResponseCode();
			if (code >= 400)
				log.error("http code:[" + code + "], access " + conn.getURL() + " meet error:"
						+ conn.getResponseMessage());

			if (code == 400) {
				throw new RuntimeException(responseBody);
			} else if (code == 401) {
				throw new RuntimeException(responseBody);
			}
			return responseBody;
		} catch (KeyManagementException | NoSuchAlgorithmException | MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (Objects.nonNull(conn))
				conn.disconnect();

			if (Objects.nonNull(pw))
				pw.close();
		}
	}

	private static HttpURLConnection getConnection(String url, JavaRequest request)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		URL target = new URL(url);
		HttpURLConnection conn;
		if (Objects.nonNull(request.getProxy()))
			conn = (HttpURLConnection) target.openConnection(request.getProxy());
		else
			conn = (HttpURLConnection) target.openConnection();

		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection sslConn = (HttpsURLConnection) conn;
			synchronized (defaultTrust) {
				if (Objects.isNull(sslFactory)) {
					SSLContext context = SSLContext.getInstance("TLS");
					context.init(null, new TrustManager[] { defaultTrust }, new java.security.SecureRandom());
					sslFactory = context.getSocketFactory();
				}
			}
			sslConn.setSSLSocketFactory(sslFactory);
			sslConn.setHostnameVerifier(hostVerifier);
		}
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(
				Objects.isNull(request.getConnectTimeout()) ? CONNECT_TIMEOUT : request.getConnectTimeout());
		conn.setReadTimeout(Objects.isNull(request.getReadTimeout()) ? READ_TIMEOUT : request.getReadTimeout());
		conn.setRequestMethod(request.getRequestMethod().name());
		for (Map.Entry<String, String> entry : request.getHeaderMap().entrySet()) {
			conn.setRequestProperty(entry.getKey(), entry.getValue());
		}
		return conn;
	}

	private static String getResponseBody(HttpURLConnection conn) throws IOException {
		StringBuilder body = new StringBuilder();
		// check content length or chunk transfer
		if (conn.getContentLengthLong() <= 0 && (conn.getHeaderField("transfer-encoding") == null
				|| conn.getHeaderField("Transfer-Encoding") == null))
			return "";

		InputStream in = null;
		if (conn.getResponseCode() < 400) {
			if (conn.getContentEncoding() != null && conn.getContentEncoding().equalsIgnoreCase("gzip"))
				in = new GZIPInputStream(conn.getInputStream(), 1024);
			else
				in = new BufferedInputStream(conn.getInputStream());

			readData(in, body);
			in.close();
		} else {
			try {
				in = new BufferedInputStream(conn.getErrorStream());
			} catch (Exception exp) {
				in = new BufferedInputStream(conn.getInputStream());
			}

			// io maybe closed at server side, we can ignore them
			try {
				readData(in, body);
				in.close();
			} catch (Exception exp) {
				log.warn("failed to read error stream:{}", exp.getMessage());
			}
		}
		return body.toString();
	}

	private static void readData(InputStream in, StringBuilder body) throws IOException {
		int len = 0;
		int pos = 0;
		byte[] data = new byte[512];
		byte[] buff = new byte[512];
		while ((len = in.read(buff)) > 0) {
			if (data.length - pos < len) {
				byte[] tmp = new byte[data.length + 2 * len];
				System.arraycopy(data, 0, tmp, 0, pos);
				data = tmp;
			}

			System.arraycopy(buff, 0, data, pos, len);
			pos += len;
		}

		body.setLength(0);
		body.append(new String(data, 0, pos, "UTF-8"));
	}

}
