package redcoder.rcredis.core;

import java.io.Closeable;

/**
 * Redis pool.
 * <p>
 * Example of use:
 * <pre>
 *     Redis redis = redisPool.getResource();
 *     try {
 *         // do something
 *     } finally {
 *         redisPool.returnResource();
 *     }
 * </pre>
 * </p>
 */
public interface RedisPool extends Closeable {

    /**
     * Get a redis instance from the pool
     *
     * @return A redis instance
     */
    Redis getResource();

    /**
     * Return the borrowed redis instance
     *
     * @param redis A borrowed redis instance to be returned
     */
    void returnResource(Redis redis);

}
