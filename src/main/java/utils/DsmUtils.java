package utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DsmUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DsmUtils.class);

    private static final Map<Integer, String> codeErrors = new HashMap<>();
    static {
        codeErrors.put(101, "No parameter of API, method or version");
        codeErrors.put(102, "The requested API does not exist");
        codeErrors.put(103, "The requested method does not exist");
        codeErrors.put(104, "The requested version does not support the functionality");
        codeErrors.put(105, "The logged in session does not have permission");
        codeErrors.put(106, "Session timeout");
        codeErrors.put(107, "Session interrupted by duplicate login");
        codeErrors.put(400, "Invalid parameter of file operation");
        codeErrors.put(401, "Unknown error of file operation");
        codeErrors.put(402, "System is too busy");
        codeErrors.put(403, "Invalid user does this file operation");
        codeErrors.put(404, "Invalid group does this file operation");
        codeErrors.put(405, "Invalid user and group does this file operation");
        codeErrors.put(406, "Can’t get user/group information from the account server");
        codeErrors.put(407, "Operation not permitted");
        codeErrors.put(408, "No such file or directory");
        codeErrors.put(409, "Non-supported file system");
        codeErrors.put(410, "Failed to connect internet-based file system (ex: CIFS)");
        codeErrors.put(411, "Read-only file system");
        codeErrors.put(412, "Filename too long in the non-encrypted file system");
        codeErrors.put(413, "Filename too long in the encrypted file system");
        codeErrors.put(414, "File already exists");
        codeErrors.put(415, "Disk quota exceeded");
        codeErrors.put(416, "No space left on device");
        codeErrors.put(417, "Input/output error");
        codeErrors.put(418, "Illegal name or path");
        codeErrors.put(419, "Illegal file name");
        codeErrors.put(420, "Illegal file name on FAT file system");
        codeErrors.put(421, "Device or resource busy");
        codeErrors.put(500, "No such task of the file operation");
        codeErrors.put(1800, "There is no Content-Length information in the HTTP header or the received\n" +
                "size does not match the value of Content-Length information in the HTTP\n" +
                "header.");
        codeErrors.put(1801, "Wait too long, no date can be received from client (Default maximum wait time is 3600 seconds)");
        codeErrors.put(1802, "No filename information in the last part of file content.\n");
        codeErrors.put(1803, "Upload connection is cancelled.");
        codeErrors.put(1804, "Failed to upload too big file to FAT file system.");
        codeErrors.put(1805, "Can’t overwrite or skip the existed file, if no overwrite parameter is given.");
    }

    private DsmUtils(){}

    public static String manageErrorMessage(Integer code) {
        return Optional.ofNullable(codeErrors.get(code)).orElse("Unknown error");
    }

    public static String makePostRequest(String url, String filePath, Map<String, String> params) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httppost = new HttpPost(url);

            FileBody fileBody = new FileBody(new File(filePath), ContentType.DEFAULT_BINARY, new File(filePath).getName());

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                    params.forEach((key, value) -> multipartEntityBuilder.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN)));
                    multipartEntityBuilder.addPart("file", fileBody);
            HttpEntity reqEntity =  multipartEntityBuilder
                                        .setLaxMode()
                                        .build();

            httppost.setEntity(reqEntity);

            LOGGER.debug("executing request " + httppost.getRequestLine());
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                LOGGER.debug("----------------------------------------");
                LOGGER.debug(response.getStatusLine().toString());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    LOGGER.debug("Response content length: " + resEntity.getContentLength());
                    result = EntityUtils.toString(resEntity);
                    LOGGER.debug(result);
                }
                EntityUtils.consume(resEntity);
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

}
