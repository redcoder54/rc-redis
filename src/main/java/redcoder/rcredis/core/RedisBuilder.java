package redcoder.rcredis.core;

/**
 * A builder used to build a {@link Redis} instance.
 */
public interface RedisBuilder {

    /**
     * Set redis server host th be connected.
     * <p>
     * Default value: localhost
     * </p>
     */
    RedisBuilder host(String host);

    /**
     * Set redis server port th be connected.
     * <p>
     * Default value: 6379
     * </p>
     */
    RedisBuilder port(int port);

    /**
     * Set redis server's password.
     */
    RedisBuilder password(String password);

    Redis build();

    public static RedisBuilder builder() {
        return new RedisBuilderImpl();
    }
}
