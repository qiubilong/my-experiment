package org.example.web.thrift;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftConnectionPoolFactory {
    private GenericObjectPool<TTransport> pool;
  
    public ThriftConnectionPoolFactory(GenericObjectPoolConfig config, String ip, int port) {
    	ConnectionFactory objFactory = new ConnectionFactory(ip, port, 3000);  
        pool = new GenericObjectPool<TTransport>(objFactory, config);  
    }  
    
    //从池里获取一个Transport对象
    public TTransport getConnection() throws Exception {  
        return pool.borrowObject();  
    }  
    
    //把一个Transport对象归还到池里
    public void releaseConnection(TTransport transport) {  
        pool.returnObject(transport);   
    }

    public GenericObjectPool<TTransport> getPool() {
        return pool;
    }

    /*
     * 连接池管理的对象Transport的工厂类，
     * GenericObjectPool会使用此类的create方法来
     * 创建Transport对象并放进pool里进行管理等操作。
     */
    class ConnectionFactory extends BasePooledObjectFactory<TTransport> {
    	private String ip;  
        private int port;
        private int socketTimeout;
          
        public ConnectionFactory(String ip, int port, int socketTimeout) {  
        	this.ip = ip;
        	this.port = port;
        	this.socketTimeout = socketTimeout;
        }  
        
        //创建TTransport类型对象方法
        @Override
		public TTransport create() throws Exception {
            TTransport socket = new TSocket(ip, port);
            if ( !socket.isOpen() ) {
                socket.open();
	        }
			return socket;
		}
 
        //把TTransport对象打包成池管理的对象PooledObject<TTransport>
		@Override
		public PooledObject<TTransport> wrap(TTransport transport) {
			return new DefaultPooledObject<TTransport>(transport);
		}
  
    }       
}