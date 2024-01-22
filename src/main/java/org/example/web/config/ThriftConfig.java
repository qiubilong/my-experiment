package org.example.web.config;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThriftConfig {

    @Bean(name="IUserPurseService",destroyMethod = "stop")
    public TServer thriftServer(IUserPurseService.Iface service) throws Exception {
        IUserPurseService.Processor<IUserPurseService.Iface> processor = new IUserPurseService.Processor<>(service);
        TServerSocket serverTransport = new TServerSocket(9090);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport).minWorkerThreads(32).processor(processor);
        TThreadPoolServer server = new TThreadPoolServer(args);
        new Thread(()->{
            server.serve();
        }).start();
        return server;
    }
}
