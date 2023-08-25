package redcoder.rcredis.core.operation;

import java.util.List;
import java.util.Map;

public interface RedisHashOperation {

    long hset(String key, String field, String value);

    void hmset(String key, Map<String, String> hash);

    String hget(String key, String field);

    List<String> hmget(String key, String... fields);
}
