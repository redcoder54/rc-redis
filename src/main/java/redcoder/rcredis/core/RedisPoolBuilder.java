package redcoder.rcredis.core;

public interface RedisPoolBuilder {

    /**
     * Set redis server host th be connected.
     * <p>
     * Default value: localhost
     * </p>
     */
    RedisPoolBuilder host(String host);

    /**
     * Set redis server port th be connected.
     * <p>
     * Default value: 6379
     * </p>
     */
    RedisPoolBuilder port(int port);

    /**
     * Set the max num of redis instance that redis pool can hold.
     * <p>
     * Default value: 8
     * </p>
     */
    RedisPoolBuilder maxTotal(int maxTotal);

    /**
     * Set the max idle num of redis instance that redis pool can hold.
     * <p>
     * Default value: 0
     * </p>
     */
    RedisPoolBuilder maxIdle(int maxIdle);

    /**
     * Set the min idle num of redis instance that redis pool can hold.
     * <p>
     * Default value: 0
     * </p>
     */
    RedisPoolBuilder minIdle(int minIdle);

    /**
     * Set the max wait milliseconds to get a redis instance from pool.
     * <p>
     * Default value: 10,000 (10s)
     * </p>
     */
    RedisPoolBuilder maxWaitTimeMillis(long maxWaitTImeMillis);

    /**
     * Whether to validate instance when it is created.
     * <p>
     * Default value: false
     * </p>
     */
    RedisPoolBuilder testOnCreate(boolean testOnCreate);

    /**
     * Whether to validate  instance when it is borrowed.
     * <p>
     * Default value: false
     * </p>
     */
    RedisPoolBuilder testOnBorrow(boolean testOnBorrow);

    /**
     * Whether to validate  instance when it is returned.
     * <p>
     * Default value: false
     * </p>
     */
    RedisPoolBuilder testOnReturn(boolean testOnReturn);

    /**
     * Whether to validate  instance when it is idle.
     * <p>
     * Default value: false
     * </p>
     */
    RedisPoolBuilder testWhileIdle(boolean testWhileIdle);

    /**
     * Build a redis pool instance with specified configuration.
     */
    RedisPool build();

    public static RedisPoolBuilder builder() {
        return new RedisPoolBuilderImpl();
    }

}
