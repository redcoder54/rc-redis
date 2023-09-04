package redcoder.rcredis.core;

public class AbstractRedisTestsSupport {

    protected static RedisConnection getConnection() {
        return new RedisConnectionImpl("localhost", 6379);
    }
}
