package com.summer.project.http.helper;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.util.Base64;

/**
 * Created by xiaxinyu3 on 2019.3.20
 */
public class HttpUtil {

    public static Header buildBasicAuthHeader(String userName, String password) {
        String auth = userName + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
        return new BasicHeader("Authorization", encodedAuth);
    }

    private static RequestConfig defaultRequestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(2000).build();
    }

    public static CloseableHttpResponse post(String url) throws Exception {
        return post(url, null, null);
    }

    public static CloseableHttpResponse post(String url, Header[] headers) throws Exception {
        return post(url, headers, null);
    }

    public static CloseableHttpResponse post(String url, Header[] headers, RequestConfig requestConfig) throws Exception {
        return post(null, url, headers, requestConfig);
    }

    public static CloseableHttpResponse post(CloseableHttpClient client, String url, Header[] headers, RequestConfig requestConfig) throws Exception {
        CloseableHttpResponse response = null;

        CloseableHttpClient realClient = (null == client) ? HttpClients.createDefault() : client;
        HttpPost post = new HttpPost(url);
        post.setConfig((null == requestConfig) ? defaultRequestConfig() : requestConfig);
        post.setHeaders(headers);
        try {
            response = realClient.execute(post);
        } catch (Exception e) {
            throw new Exception("Doing request [{}] has error.", e);
        } finally {
            post.releaseConnection();
        }
        return response;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://10.0.49.90/jenkins/job/devcloud-devops-ci_devops-ci-backend_email-ext-test/reload";
        Header header = buildBasicAuthHeader("root", "115268ac0aa429fcf601010b37c4f6a62d");
        CloseableHttpResponse response = post(url, new Header[]{header});
        System.out.println("Result : " +  (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()));
    }
}