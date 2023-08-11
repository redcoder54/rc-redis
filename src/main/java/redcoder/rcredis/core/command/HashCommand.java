package redcoder.rcredis.core.command;

import java.util.List;
import java.util.Map;

public interface HashCommand {

    long hset(byte[] key, byte[] field, byte[] value);

    void hmset(byte[] key, Map<byte[], byte[]> hash);

    byte[] hget(byte[] key, byte[] field);

    List<byte[]> hmget(byte[] key, byte[]... fields);
}
