package cn.bmilk.tools.http;

import cn.bmilk.tools.common.BooleanEnum;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnExpression("${http.pool.enable:false}==true")
public class HttpClientPoolConfig {

    @Resource
    private HttpPoolProperties httpPoolProperties;

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
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

    @Bean(name = "requestConfigBuilder")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectionRequestTimeout(httpPoolProperties.getConnectRequestTimeout())
                .setConnectTimeout(httpPoolProperties.getConnectTimeout())
                .setSocketTimeout(httpPoolProperties.getSocketTimeout());
    }

    @Bean(name = "requestConfig")
    public RequestConfig getRequestConfig(@Qualifier("requestConfigBuilder") RequestConfig.Builder requestConfigBuilder){
        return requestConfigBuilder.build();
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager,
                                                  @Qualifier("requestConfig") RequestConfig requestConfig){
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);


        if(BooleanEnum.TRUE.getValue().equals(httpPoolProperties.getEnableRetry())){
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpPoolProperties.getRetryTimes(), true));
        }else {
            httpClientBuilder.disableAutomaticRetries();
        }
        return httpClientBuilder;
    }

    @Bean(name = "httpClient")
    public CloseableHttpClient getHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

}
