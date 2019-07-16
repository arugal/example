package com.github.arugal.curator.example.discovery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;

/**
 * @author: zhangwei
 * @date: 2019-07-14/13:36
 */
public class RemoteInstanceSerializer implements InstanceSerializer<RemoteInstance> {


    private final Gson gson = new Gson();

    @Override
    public byte[] serialize(ServiceInstance<RemoteInstance> serviceInstance) throws Exception {
        return gson.toJson(serviceInstance).getBytes();
    }

    @Override
    public ServiceInstance<RemoteInstance> deserialize(byte[] bytes) throws Exception {
        return gson.fromJson(new String(bytes), new TypeToken<ServiceInstance<RemoteInstance>>() {
        }.getType());
    }
}
