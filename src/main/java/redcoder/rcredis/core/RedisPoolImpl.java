package redcoder.rcredis.core;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

class RedisPoolImpl implements RedisPool {

    private GenericObjectPool<Redis> pool;

    public RedisPoolImpl(PooledObjectFactory<Redis> factory, GenericObjectPoolConfig<Redis> config) {
        this.pool = new GenericObjectPool<>(factory, config);
    }

    @Override
    public Redis getResource() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RedisException("Could not get Redis Instance", e);
        }
    }

    @Override
    public void returnResource(Redis redis) {
        pool.returnObject(redis);
    }

    @Override
    public void close() {
        pool.close();
    }
}
