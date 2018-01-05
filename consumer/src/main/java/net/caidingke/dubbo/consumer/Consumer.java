package net.caidingke.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.collect.ImmutableList;
import net.caidingke.dubbo.service.ServiceApi;

/**
 * 消费者
 *
 * @author bowen
 */
public class Consumer {

    public static void main(String[] args) {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("consumer");


        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://localhost:2181");
        //registry.setUsername("aaa");
        //registry.setPassword("bbb");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://localhost:2182");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<ServiceApi> reference = new ReferenceConfig<ServiceApi>();
        reference.setApplication(application);
        //reference.setRegistry(registry);
        reference.setRegistries(ImmutableList.of(registry,registry1));
        reference.setInterface(ServiceApi.class);
        reference.setVersion("1.0.0");

        // 和本地bean一样使用xxxService
        ServiceApi serviceApi = reference.get();

        while (true) {
            try {
                Thread.sleep(1);
                serviceApi.findF("bowen"); // call remote method
                //System.out.println(f); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


        }
    }
}
