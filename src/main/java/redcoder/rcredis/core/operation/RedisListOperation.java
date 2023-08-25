package redcoder.rcredis.core.operation;

import java.util.List;

public interface RedisListOperation<K, V> {

    long lpush(K key, V... elements);

    long lpushx(K key, V element);

    List<V> lrange(K key, long start, long end);

    V lpop(K key);

    long llen(K key);

    long rpush(K key, V... elements);

    long rpushx(K key, V element);

    V rpop(K key);

}
