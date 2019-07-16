package com.github.arugal.curator.example.discovery;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author: zhangwei
 * @date: 2019-07-14/13:25
 */
public class ServiceDiscoveryTest {

    private CuratorFramework client;

    private ServiceDiscovery<RemoteInstance> discovery;

    private ServiceCache<RemoteInstance> cache;

    private RemoteInstance remoteInstance = RemoteInstance.builder().host("127.0.0.1").port(1000).build();

    private final String SERVICE_NAME = "service";

    private final String BASE_PATH = "example";

    @Before
    public void startUp() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(System.getProperty("zk.address", "127.0.0.1:2181"), retryPolicy);
        discovery = ServiceDiscoveryBuilder.builder(RemoteInstance.class)
            .client(client)
            .basePath(BASE_PATH)
            .watchInstances(true)
            .serializer(new RemoteInstanceSerializer())
            .build();

        client.start();
        client.blockUntilConnected();
        discovery.start();

        cache = discovery.serviceCacheBuilder()
            .name(SERVICE_NAME)
            .build();

        cache.start();
    }

    @Test
    public void registerNode() throws Exception {
        ServiceInstance<RemoteInstance> thisInstance = ServiceInstance.<RemoteInstance>builder()
            .name(SERVICE_NAME)
            .id(UUID.randomUUID().toString())
            .address(remoteInstance.getHost())
            .port(remoteInstance.getPort())
            .payload(remoteInstance)
            .build();

        discovery.registerService(thisInstance);
    }

    @Test
    public void queryNodes() {
        List<ServiceInstance<RemoteInstance>> serviceInstances = cache.getInstances();
        assertEquals(1, serviceInstances.size());
        RemoteInstance otherInstance = serviceInstances.get(0).getPayload();
        assertEquals(otherInstance.getHost(), remoteInstance.getHost());
        assertEquals(otherInstance.getPort(), remoteInstance.getPort());
    }
}
