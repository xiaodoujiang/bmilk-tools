package cn.bmilk.tools.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

public class JerseyClientFactory {

    public static Client builder(JerseyProperties jerseyProperties) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, jerseyProperties.getConnectTimeout());
        clientConfig.property(ClientProperties.READ_TIMEOUT, jerseyProperties.getReaderTimeout());

        PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
        pcm.setDefaultSocketConfig(
                SocketConfig.custom().
                        setSoTimeout(jerseyProperties.getSocketTimeout()).
                        build());
        pcm.setMaxTotal(jerseyProperties.getMaxTotal());
        pcm.setDefaultMaxPerRoute(jerseyProperties.getMaxPerRoute());
        pcm.setValidateAfterInactivity(jerseyProperties.getValidateAfterInactivity());

        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, pcm);

        clientConfig.connectorProvider(new ApacheConnectorProvider());
        clientConfig.register(JacksonFeature.class);


        return ClientBuilder.newClient(clientConfig);
    }

}
