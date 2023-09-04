package redcoder.rcredis.core;

class RedisBuilderImpl implements RedisBuilder {

    private String host = "localhost";
    private int port = 6379;
    private String password;

    @Override
    public RedisBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public RedisBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RedisBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public Redis build() {
        RedisConnection connection = new RedisConnectionImpl(host, port, password);
        return new Redis(connection);
    }
}
