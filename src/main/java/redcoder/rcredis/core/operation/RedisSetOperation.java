package redcoder.rcredis.core.operation;

import java.util.List;

public interface RedisSetOperation {

    int sadd(String key, String... members);

    int srem(String key, String... members);

    String spop(String key);

    List<String> spop(String key, int count);

    List<String> smembers(String key);
}
