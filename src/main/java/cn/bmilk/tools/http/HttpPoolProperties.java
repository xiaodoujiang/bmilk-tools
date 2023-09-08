package cn.bmilk.tools.http;

import lombok.Data;

@Data
public class HttpPoolProperties {
    private static final int DEFAULT_HTTP_MAX_CONNECTION = 200;
    private static final int DEFAULT_MAX_PERROUTE = 20;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 5000;
    private static final int DEFAULT_POOLCM_SOCKET_TIMEOUT = 3000;
    private static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 5000;
    private static final boolean DEFAULT_ENABLE_RETRY = false;
    private static final int DEFAULT_RETRY_TIMES = 3;
    private static final int DEFAULT_RETRY_INTERVAL = 2000;

    // 最大连接数
    private int maxTotal = DEFAULT_HTTP_MAX_CONNECTION;
    // 同一路由最大请求连接数
    private int maxPerRoute = DEFAULT_MAX_PERROUTE;
    // 链接超时时间
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    // 从连接池获取链接的超时时间
    private int connectRequestTimeout = DEFAULT_CONNECT_REQUEST_TIMEOUT;
    // 数据传输过程中 数据包之间间隔的最大时间
    private int socketTimeout = DEFAULT_POOLCM_SOCKET_TIMEOUT;
    // 空闲一定时间后检验连接是否有效
    private int validateAfterInactivity = DEFAULT_VALIDATE_AFTER_INACTIVITY;
    // 是否允许重试
    private boolean enableRetry = DEFAULT_ENABLE_RETRY;
    // 重试次数
    private int retryTimes = DEFAULT_RETRY_TIMES;
    // 重试间隔
    private int retryInterval = DEFAULT_RETRY_INTERVAL;
}
