package redcoder.rcredis.core.operation;

import java.util.List;
import java.util.Map;

public interface RedisHashOperation<K, HK, HV> {

    long hset(K key, HK field, HV value);

    void hmset(K key, Map<HK, HV> hash);

    HV hget(K key, HK field);

    List<HV> hmget(K key, HK... fields);
}
