package hn.cch.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    //字符集
    private static String charset = "utf-8";

    // public static HttpRequest toRequest(){
    //
    // }
    // public static byte[] fromResponse(){
    //
    // }

    public static String sentGet(String http){

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            // logger.info("http : " + http);

            HttpGet httpGet = new HttpGet(http);

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String get = EntityUtils.toString(httpResponse.getEntity(), charset);

            // logger.info("get : " + get);
            return get;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("send get error : " + e.getMessage());
            return "";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("send get close : " + e.getMessage());
            }
        }

    }

    public static String sentPost(String http, String data){

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            // logger.info("http : " + http);
            // logger.info("data : " + data);

            HttpPost httpPost = new HttpPost(http);
            httpPost.setEntity(new StringEntity(data, charset));

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String post = EntityUtils.toString(httpResponse.getEntity(), charset);

            // logger.info("post : " + post);
            return post;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("send post error : " + e.getMessage());
            return "";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("send post close : " + e.getMessage());
            }
        }
    }

    public static byte[] sentPost(String http, byte[] data){

        // logger.info("http : " + http);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpPost httpPost = new HttpPost(http);
            httpPost.setEntity(new ByteArrayEntity(data));


            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            byte[] post = EntityUtils.toByteArray(httpResponse.getEntity());

            return post;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("send post error : " + e.getMessage());
            return new byte[0];
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("send post close : " + e.getMessage());
            }
        }
    }


}
