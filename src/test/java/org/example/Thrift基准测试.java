package org.example;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.web.dao.entity.UserPurse;
import org.example.web.thrift.ThriftConnectionPoolFactory;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.example.web.thrift.userpurse.api.UserPurseBo;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxuegui
 * @since 2024/1/12
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations=2,time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations=3,time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Thrift基准测试 {
    public static void main(String[] args) throws Exception{
        Options opt = new OptionsBuilder().include(Thrift基准测试.class.getSimpleName())
                .threads(8)
                .jvmArgs( "-Xms2G", "-Xmx2G")
                .build();
        new Runner(opt).run();
    }

   @Benchmark
    public void getUserPurseInfoFromCacheRedis() throws Exception{

        TTransport transport = poolFactory.getConnection();
        TProtocol protocol = new TBinaryProtocol(transport);
        IUserPurseService.Client userPurseService = new IUserPurseService.Client(protocol);

        UserPurseBo user = userPurseService.getUserPurseInfoCache(200000010L);

        poolFactory.releaseConnection(transport);


    }
    //@Benchmark
    public void incrUserPurseGoldNum() throws Exception{

        TTransport transport = poolFactory.getConnection();
        TProtocol protocol = new TBinaryProtocol(transport);
        IUserPurseService.Client userPurseService = new IUserPurseService.Client(protocol);

        long total = userPurseService.incrUserPurseGoldNum(200000010, 1L);

        poolFactory.releaseConnection(transport);


    }

    public void getUserPurseInfoFromCacheLocal()throws Exception{

        TTransport transport = poolFactory.getConnection();
        TProtocol protocol = new TBinaryProtocol(transport);
        IUserPurseService.Client userPurseService = new IUserPurseService.Client(protocol);

        UserPurseBo user = userPurseService.getUserPurseInfoLocal(200000010L);

        poolFactory.releaseConnection(transport);

    }

    private  IUserPurseService.Client userPurseService;
    private ThriftConnectionPoolFactory poolFactory;

    @Setup
    public void init() {
        try  {

            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(16);
            config.setMaxWait(Duration.ofSeconds(5));
            poolFactory = new ThriftConnectionPoolFactory(config, "localhost", 9090);

            TTransport transport = poolFactory.getConnection();
            TProtocol protocol = new TBinaryProtocol(transport);
            userPurseService = new IUserPurseService.Client(protocol);

            UserPurseBo user = userPurseService.getUserPurseInfo(200000010L);
            System.out.println(user);

            poolFactory.releaseConnection(transport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @TearDown
    public void shutdown(){
        poolFactory.getPool().close();
    }
}
