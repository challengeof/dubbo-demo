package net.caidingke.dubbo.provider.service;

import com.alibaba.dubbo.rpc.RpcContext;
import net.caidingke.dubbo.service.ServiceApi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bowen
 */
public class ServiceApiImpl implements ServiceApi {

    @Override
    public void findF(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        for (;;) {

        }
        //return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();

    }
}
