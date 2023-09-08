package cn.bmilk.tools.http;

import javax.ws.rs.client.Client;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        Client client = JerseyClientFactory.builder(new JerseyProperties());
        BaseJerseyFacade facade = BaseJerseyFacade.builder(client);
        Map<String, String> map = new HashMap<>();
        map.put("username", "nacos");
        map.put("password", "nacos");

        LoginResponse post = facade.post("http://192.168.56.14:8848/nacos/v1/auth/login", map, LoginResponse.class);
        System.out.println(1);
        Map<String, String> getMap = new HashMap<>();
        getMap.put("namespaceId", "public");
        getMap.put("group", "test-app");
        getMap.put("dataId", "test-app");
        getMap.put("accessToken", post.getAccessToken());
        CommonResponse response = facade.get("http://192.168.56.14:8848/nacos/v2/cs/config", getMap, CommonResponse.class);
        System.out.println(response);

    }


    static class CommonResponse{
        private int code;

        private String message;

        private String data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    static class LoginResponse{
        private String accessToken;
        private int tokenTtl;
        private boolean globalAdmin;
        private String username;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getTokenTtl() {
            return tokenTtl;
        }

        public void setTokenTtl(int tokenTtl) {
            this.tokenTtl = tokenTtl;
        }

        public boolean isGlobalAdmin() {
            return globalAdmin;
        }

        public void setGlobalAdmin(boolean globalAdmin) {
            this.globalAdmin = globalAdmin;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
