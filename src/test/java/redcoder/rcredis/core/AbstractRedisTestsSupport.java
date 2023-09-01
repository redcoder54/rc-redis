package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;

public class AbstractRedisTestsSupport {

    protected static RedisConnection getConnection() {
        return new RedisConnectionImpl("localhost", 7370);
    }
}
