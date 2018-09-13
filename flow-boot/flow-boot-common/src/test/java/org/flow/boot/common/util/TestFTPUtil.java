package org.flow.boot.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestFTPUtil {

    public static void main(String[] args) throws Exception {

        // // 创建文件夹
        // FTPUtil.execute(FTPUtil.createMakeDirCmd("songyz"));
        //
        // // 删除文件夹
        // FTPUtil.execute(FTPUtil.createDelDirCmd("songyz"));
        //
        // // 删除文件
        // FTPUtil.execute(FTPUtil.createDelFileCmd("test/远方.jpg"));

        // 上传本地文件

        Map<String, InputStream> localFiles = new HashMap<String, InputStream>();
        Stream.of(new File("D:\\flow200").listFiles())
                .forEach(f -> {
                    try {
                        localFiles.put(f.getName(), new FileInputStream(f));
                    }
                    catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

        FTPUtil.execute(FTPUtil.createPutStreamCmd(localFiles, "file/flow200"));
    }

}
