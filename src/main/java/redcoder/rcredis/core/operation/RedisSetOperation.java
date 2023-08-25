package redcoder.rcredis.core.operation;

import java.util.List;

public interface RedisSetOperation<K, V> {

    int sadd(K key, V... members);

    int srem(K key, V... members);

    V spop(K key);

    List<V> spop(K key, int count);

    List<V> smembers(K key);
}
