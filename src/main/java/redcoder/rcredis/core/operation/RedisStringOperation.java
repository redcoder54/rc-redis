package redcoder.rcredis.core.operation;

import java.util.concurrent.TimeUnit;

public interface RedisStringOperation<K, V> {

    void set(K key, V value);

    void set(K key, V value, long timeout, TimeUnit unit);

    long incr(K key);

    long decr(K key);

    V get(K key);
}
