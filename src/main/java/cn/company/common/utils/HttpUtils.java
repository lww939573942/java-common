package cn.company.common.utils;

import jodd.net.URLDecoder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求工具
 *
 * @author donaldhan
 */
public final class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * Http get请求
     *
     * @param url 请求地址
     * @param header 头部信息
     * @param parameterDataMap 请求的参数
     * @param socketTimeout
     * @param connectTimeout
     */
    public static String apiGetRequest(String url, Map<String, String> header, Map<String, String> parameterDataMap, int socketTimeout, int connectTimeout) throws IOException {
        if (!ValidateUtils.isEmpty(parameterDataMap)) {
            try {
                URIBuilder uriBuilder = new URIBuilder(url);
                if (!ValidateUtils.isEmpty(parameterDataMap)) {
                    parameterDataMap.entrySet().forEach(e -> {
                        uriBuilder.addParameter(e.getKey(), e.getValue());
                    });
                }
                url = uriBuilder.build().toString();
            } catch (URISyntaxException e) {
                logger.error("url错误");
                throw new RuntimeException("url错误");
            }
        }
        logger.info("请求的URL：{}", url);

        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpGet.setConfig(requestConfig);

        return httpExecute(httpGet, header);
    }

    /**
     * Http post form请求
     *
     * @param url 请求地址
     * @param header 头部信息
     * @param parameterDataMap 请求的参数
     * @param socketTimeout
     * @param connectTimeout
     */
    public static String apiPostRequestForm(String url, Map<String, String> header, Map<String, String> parameterDataMap, int socketTimeout, int connectTimeout) throws IOException {
        logger.info("请求的URL为：{}", url);

        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);

        if (ValidateUtils.isNotEmpty(parameterDataMap)) {
            List<NameValuePair> formParams = new ArrayList<>();
            parameterDataMap.entrySet().forEach(e -> {
                formParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            });
            HttpEntity entity = new UrlEncodedFormEntity(formParams, Charset.forName("utf-8"));
            httpPost.setEntity(entity);

            logger.info("请求的参数为：{}",  URLDecoder.decode(EntityUtils.toString(entity)));
        }

        return httpExecute(httpPost, header);
    }

    /**
     * Http post form请求
     *
     * @param url 请求地址
     * @param header 头部信息
     * @param bodyStr body内容
     * @param contentType 内容类型
     * @param socketTimeout
     * @param connectTimeout
     */
    public static String apiPostRequestBody(String url, Map<String, String> header, String bodyStr, String contentType, int socketTimeout, int connectTimeout) throws IOException {
        logger.info("请求的URL为：{}", url);
        logger.info("请求的body内容为：{}", bodyStr);

        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(bodyStr,"UTF-8");
        entity.setContentType(contentType);
        httpPost.setEntity(entity);

        return httpExecute(httpPost, header);
    }

    private static String httpExecute(HttpUriRequest request, Map<String, String> headerMap) throws IOException {
        logger.info("请求方式为：{}", request.getMethod());
        if (ValidateUtils.isNotEmpty(headerMap)) {
            headerMap.entrySet().forEach(e -> {
                request.setHeader(e.getKey(), e.getValue());
            });
        }

        for (Header header : request.getAllHeaders()) {
            logger.info("请求的header为：{}", header);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        for (Header header1 : response.getAllHeaders()) {
            logger.info("返回的header为：{}", header1);
        }
        logger.info("返回的状态码为：{}", response.getStatusLine());
        String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        logger.info("返回的数据为：{}", result);
        response.close();
        httpClient.close();

        return result;
    }

    /**
     * 获取Body内容
     *
     * @param request
     *
     * @return bodyStr
     */
    public static String getRequestBodyContent(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = request.getReader();
        StringBuffer dataStrBuf = new StringBuffer();
        String dataTemp;

        while ((dataTemp = bufferedReader.readLine()) != null) {
            dataStrBuf.append(dataTemp);
        }
        bufferedReader.close();

        String content = dataStrBuf.toString();
        if (ValidateUtils.isNotEmpty(content)) {
            logger.info("请求的body内容为:{}", content);
            return dataStrBuf.toString();
        } else {
            logger.info("请求的body内容为空!");
            return null;
        }
    }

    /**
     * http下载文件
     *
     * @param url
     * @param headerMap
     * @param socketTimeout
     * @param connectTimeout
     *
     * @return InputStream
     */
    public static InputStream downloadFile(String url, Map<String, String> headerMap, int socketTimeout, int connectTimeout) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpGet.setConfig(requestConfig);


        if (ValidateUtils.isNotEmpty(headerMap)) {
            headerMap.entrySet().forEach(e -> {
                httpGet.setHeader(e.getKey(), e.getValue());
            });
        }

        for (Header header : httpGet.getAllHeaders()) {
            logger.info("请求的header为：{}", header);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        httpClient.close();

        return response.getEntity().getContent();
    }
}