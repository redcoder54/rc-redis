package redcoder.rcredis.core.command;

import java.util.List;

public interface RedisSetCommand {

    int sadd(byte[] key, byte[]... members);

    int srem(byte[] key, byte[]... members);

    byte[] spop(byte[] key);

    List<Object> spop(byte[] key, int count);

    List<Object> smembers(byte[] key);
}
