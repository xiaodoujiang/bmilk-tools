package cn.bmilk.tools.http;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.Data;

import java.util.Map;

@Data
public class BaseJerseyFacade {

    private Client client;
    private WebTarget baseTarget;

    public static BaseJerseyFacade builder(Client client, String host, String path){
        BaseJerseyFacade baseJerseyFacade = new BaseJerseyFacade();
        baseJerseyFacade.client = client;
        baseJerseyFacade.baseTarget = client.target(host+path);
        return baseJerseyFacade;
    }
    public static BaseJerseyFacade builder(Client client){
        BaseJerseyFacade baseJerseyFacade = new BaseJerseyFacade();
        baseJerseyFacade.client = client;
        return baseJerseyFacade;
    }

    public <T> T get(String path, Map<String, String> param, Class<T> responseType){
        return get(path, null, param, responseType);
    }
    public <T> T get(String path, Map<String, String> param, GenericType<T> responseType){
        return get(path, null, param, responseType);
    }
    public <T> T post(String path, Object entity, Class<T> responseType) {
        return post(path, null, entity, responseType);
    }

    public <T> T post(String path, Object entity, GenericType<T> responseType) {
        return post(path, null, entity, responseType);
    }
    public <T> T post(String path, Map<String, String> form, Class<T> responseType) {
        return post(path,null, form, responseType);
    }
    public <T> T post(String path, Map<String, String> form, GenericType<T> responseType) {
        return post(path, null, form, responseType);
    }

        // 自定义方法用于发送 GET 请求并获取已反序列化的结果
    public <T> T get(String path,  Map<String, String> headerMap, Map<String, String> param, Class<T> responseType) {
        Response response = getRequest(path, headerMap, param);
        return getTargetObject(response, responseType);
    }
    public <T> T get(String path,  Map<String, String> headerMap, Map<String, String> param, GenericType<T> responseType) {
        Response response = getRequest(path, headerMap, param);
        return getTargetObject(response, responseType);
    }

    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, Map<String, String> headerMap, Object entity, Class<T> responseType) {
        Response response = postRequest(path, headerMap,  entity);
        return getTargetObject(response, responseType);

    }
    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, Map<String, String> headerMap, Object entity, GenericType<T> responseType) {
        Response response = postRequest(path, headerMap,  entity);
        return getTargetObject(response, responseType);
    }

    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, Map<String, String> headerMap, Map<String, String> form, Class<T> responseType) {
        return post(path, MediaType.APPLICATION_FORM_URLENCODED_TYPE, headerMap, form, responseType);
    }

    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, Map<String, String> headerMap, Map<String, String> form, GenericType<T> responseType) {
        return post(path, MediaType.APPLICATION_FORM_URLENCODED_TYPE, headerMap, form, responseType);
    }

    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, MediaType mediaType, Map<String, String> headerMap, Map<String, String> form, Class<T> responseType) {
        Response response = postRequest(path, mediaType, headerMap,  form);
        return getTargetObject(response, responseType);
    }
    // 自定义方法用于发送 POST 请求并获取已反序列化的结果
    public <T> T post(String path, MediaType mediaType, Map<String, String> headerMap, Map<String, String> form, GenericType<T> responseType) {
        Response response = postRequest(path, mediaType, headerMap,  form);
        return getTargetObject(response, responseType);
    }

    private Response getRequest(String path,  Map<String, String> headerMap, Map<String, String> param){
        WebTarget webTarget = null == baseTarget ? client.target(path):  baseTarget.path(path);

        if(null != param){
            for (String key : param.keySet()){
                webTarget = webTarget.queryParam(key, param.get(key));
            }
        }
        Invocation.Builder request = webTarget.request();
        if(null != headerMap){
            for (String key : headerMap.keySet()){
                request = request.header(key, headerMap.get(key));
            }
        }

        return request.get();
    }

    private Response postRequest(String path, Map<String, String> headerMap, Object entity){
        WebTarget webTarget = null == baseTarget ? client.target(path):  baseTarget.path(path);
        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        if (null != headerMap){
            for (String key : headerMap.keySet()){
                request = request.header(key, headerMap.get(key));
            }
        }
        return request.post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    private Response postRequest(String path, MediaType mediaType, Map<String, String> headerMap, Map<String, String> from){
        WebTarget webTarget = null == baseTarget ? client.target(path):  baseTarget.path(path);
         Invocation.Builder request = webTarget.request();
        if (null != headerMap){
            for (String key : headerMap.keySet()){
                request = request.header(key, headerMap.get(key));
            }
        }
        Form form = new Form();
        if(null != from){
            for (String key : from.keySet()){
                form.param(key, from.get(key));

            }
        }
        return request.post(Entity.entity(form, mediaType));
    }

    private <T> T getTargetObject(Response response, Class<T> responseType){
        if(200 == response.getStatus()){
            return response.readEntity(responseType);
        }else {
            String msg = String.format("http request failure, response:%s", response.toString());
            if(response.hasEntity()){
                String responseEntity = response.readEntity(String.class);
                msg = String.format(msg + ", responseEntity:%s", responseEntity);
            }
            throw new RuntimeException(msg);
        }
    }

    private <T> T getTargetObject(Response response, GenericType<T> responseType){
        if(200 == response.getStatus()){
            return response.readEntity(responseType);
        }else {
            String msg = String.format("http request failure, response:%s", response.toString());
            if(response.hasEntity()){
                String responseEntity = response.readEntity(String.class);
                msg = String.format(msg + ", responseEntity:%s", responseEntity);
            }
            throw new RuntimeException(msg);
        }
    }

}
