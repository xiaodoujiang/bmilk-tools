package cn.bmilk.tools.http;

import lombok.Data;

@Data
public class JerseyProperties {
    private static final int DEFAULT_HTTP_MAX_CONNECTION = 200;
    private static final int DEFAULT_MAX_PERROUTE = 20;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_READ_TIMEOUT = 5000;
    private static final int DEFAULT_POOLCM_SOCKET_TIMEOUT = 3000;
    private static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 5000;

    // 最大连接数
    private int maxTotal = DEFAULT_HTTP_MAX_CONNECTION;
    // 同一路由最大请求连接数
    private int maxPerRoute = DEFAULT_MAX_PERROUTE;
    // 链接超时时间
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    // 客户端在等待服务器响应时的最大时间
    private int readerTimeout = DEFAULT_READ_TIMEOUT;
    // 数据传输过程中 数据包之间间隔的最大时间
    private int socketTimeout = DEFAULT_POOLCM_SOCKET_TIMEOUT;
    // 空闲一定时间后检验连接是否有效
    private int validateAfterInactivity = DEFAULT_VALIDATE_AFTER_INACTIVITY;

}
