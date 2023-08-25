package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;

public class RedisTestSupport {

    protected static RedisConnection getConnection() {
        RedisConfiguration conf = new RedisConfiguration();
        conf.setHost("localhost");
        conf.setPort(7370);
        RedisConnectionFactory factory = new RedisConnectionFactoryImpl(conf);
        return factory.create();
    }
}
