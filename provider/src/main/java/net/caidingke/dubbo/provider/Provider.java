package net.caidingke.dubbo.provider;

import net.caidingke.dubbo.provider.service.ServiceApiImpl;
import net.caidingke.dubbo.service.ServiceApi;
import org.apache.dubbo.config.*;

/**
 * 提供服务
 *
 * @author bowen
 */
public class Provider {

    public static void main(String[] args) {

        System.setProperty("java.net.preferIPv4Stack", "true");
        ServiceApi serviceApi = new ServiceApiImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("provider");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://localhost:2181");
        registry.setTimeout(500);
        //registry.setUsername("aaa");
        //registry.setPassword("bbb");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20881);
        protocol.setThreads(200);

        ConfigCenterConfig configCenterConfig = new ConfigCenterConfig();
        configCenterConfig.setAddress("zookeeper://localhost:2181");

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<ServiceApi> service = new ServiceConfig<ServiceApi>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(ServiceApi.class);
        service.setRef(serviceApi);
        service.setConfigCenter(configCenterConfig);
        service.setVersion("1.0.0");
        //负载均衡策略
        service.setLoadbalance("random");
        //重试
        service.setRetries(2);

        // 暴露及注册服务
        service.export();
        for (;;){}
    }
}
