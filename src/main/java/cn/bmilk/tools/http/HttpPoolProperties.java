package cn.bmilk.tools.http;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnExpression("${http.pool.enable:false}==true")
public class HttpPoolProperties {
    // 最大连接数
    @Value("${http.pool.max.total}")
    private int maxTotal;
    // 同一路由最大请求连接数
    @Value("${http.pool.max.per.route}")
    private int maxPerRoute;
    // 链接超时时间
    @Value("${http.connect.timeout}")
    private int connectTimeout;
    // 从连接池获取链接的超时时间
    @Value("${http.connect.request.timeout:2000}")
    private int connectRequestTimeout;
    // 数据传输过程中 数据包之间间隔的最大时间
    @Value("${http.connect.socket.timeout:2000}")
    private int socketTimeout;

    @Value("${http.validate.after.inactivity:5000}")
    private int validateAfterInactivity;
    // 是否允许重试
    @Value("${http.enable.retry:true}")
    private String enableRetry;
    // 重试次数
    @Value("${http.retry.times:3}")
    private int retryTimes;
    // 重试间隔
    @Value("${http.retry.interval:2000}")
    private int retryInterval;
}
