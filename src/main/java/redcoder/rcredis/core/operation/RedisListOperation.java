package redcoder.rcredis.core.operation;

import java.util.List;

public interface RedisListOperation {

    long lpush(String key, String... elements);

    long lpushx(String key, String element);

    List<String> lrange(String key, long start, long end);

    String lpop(String key);

    long llen(String key);

    long rpush(String key, String... elements);

    long rpushx(String key, String element);

    String rpop(String key);

}
