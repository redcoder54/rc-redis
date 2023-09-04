package redcoder.rcredis.core;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

class RedisPoolBuilderImpl implements RedisPoolBuilder {

    private String host = "localhost";
    private int port = 6379;
    private String password;
    private int maxTotal = 8;
    private int maxIdle = 0;
    private int minIdle = 0;
    private long maxWaitTImeMillis = 10_000;
    private boolean testOnCreate = false;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean testWhileIdle = false;

    @Override
    public RedisPoolBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public RedisPoolBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RedisPoolBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public RedisPoolBuilder maxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    @Override
    public RedisPoolBuilder maxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    @Override
    public RedisPoolBuilder minIdle(int minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    @Override
    public RedisPoolBuilder maxWaitTimeMillis(long maxWaitTImeMillis) {
        this.maxWaitTImeMillis = maxWaitTImeMillis;
        return this;
    }

    @Override
    public RedisPoolBuilder testOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
        return this;
    }

    @Override
    public RedisPoolBuilder testOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        return this;
    }

    @Override
    public RedisPoolBuilder testOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
        return this;
    }

    @Override
    public RedisPoolBuilder testWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
        return this;
    }

    @Override
    public RedisPool build() {
        PooledObjectFactory<Redis> factory = new RedisPooledObjectFactory(host, port, password);

        GenericObjectPoolConfig<Redis> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWait(Duration.ofMillis(maxWaitTImeMillis));
        config.setTestOnCreate(testOnCreate);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setTestWhileIdle(testWhileIdle);

        return new RedisPoolImpl(factory, config);
    }
}
