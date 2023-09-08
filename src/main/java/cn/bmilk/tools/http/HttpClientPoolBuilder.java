package cn.bmilk.tools.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientPoolBuilder {

    private HttpPoolProperties httpPoolProperties;

    public HttpClientPoolBuilder(HttpPoolProperties httpPoolProperties) {
        this.httpPoolProperties = httpPoolProperties;
    }

    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", PlainConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());
        httpClientConnectionManager.setDefaultMaxPerRoute(httpPoolProperties.getMaxPerRoute());
        httpClientConnectionManager.setValidateAfterInactivity(httpPoolProperties.getValidateAfterInactivity());
        return httpClientConnectionManager;
    }

    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectionRequestTimeout(httpPoolProperties.getConnectRequestTimeout())
                .setConnectTimeout(httpPoolProperties.getConnectTimeout())
                .setSocketTimeout(httpPoolProperties.getSocketTimeout());
    }

    public RequestConfig getRequestConfig(RequestConfig.Builder requestConfigBuilder) {
        return requestConfigBuilder.build();
    }

    public HttpClientBuilder getHttpClientBuilder(PoolingHttpClientConnectionManager httpClientConnectionManager,
                                                  RequestConfig requestConfig) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);


        if (httpPoolProperties.isEnableRetry()) {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpPoolProperties.getRetryTimes(), true));
        } else {
            httpClientBuilder.disableAutomaticRetries();
        }
        return httpClientBuilder;
    }

    public CloseableHttpClient getHttpClient(HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    public static CloseableHttpClient builder() {
        return builder(new HttpPoolProperties());
    }

    public static CloseableHttpClient builder(HttpPoolProperties httpPoolProperties) {
        HttpClientPoolBuilder httpClientPoolBuilder = new HttpClientPoolBuilder(httpPoolProperties);
        PoolingHttpClientConnectionManager httpClientConnectionManager = httpClientPoolBuilder.getHttpClientConnectionManager();
        RequestConfig.Builder builder = httpClientPoolBuilder.getBuilder();
        RequestConfig requestConfig = httpClientPoolBuilder.getRequestConfig(builder);
        HttpClientBuilder httpClientBuilder = httpClientPoolBuilder.getHttpClientBuilder(httpClientConnectionManager, requestConfig);
        return httpClientPoolBuilder.getHttpClient(httpClientBuilder);
    }

}
