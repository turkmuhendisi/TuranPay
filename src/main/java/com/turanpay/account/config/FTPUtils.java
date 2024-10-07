package com.turanpay.account.config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FTPUtils {

    private static final String FTP_SERVER = "localhost";
    private static final String FTP_USERNAME = "admin";
    private static final String FTP_PASSWORD = "admin123!.";

    public static void uploadFileToFtp(File file, String ftpDirectory, String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SERVER);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(ftpDirectory);

            FileInputStream inputStream = new FileInputStream(file);
            ftpClient.storeFile(fileName + ".jpg", inputStream);
            inputStream.close();

            ftpClient.logout();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
