package redcoder.rcredis.core;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class RedisPooledObjectFactory extends BasePooledObjectFactory<Redis> {

    private String host;
    private int port;
    private String password;

    public RedisPooledObjectFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RedisPooledObjectFactory(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Override
    public Redis create() throws Exception {
        RedisConnection connection = new RedisConnectionImpl(host, port);
        return new Redis(connection);
    }

    @Override
    public PooledObject<Redis> wrap(Redis redis) {
        return new DefaultPooledObject<>(redis);
    }
}
