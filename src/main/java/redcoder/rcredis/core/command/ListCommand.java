package redcoder.rcredis.core.command;

import java.util.List;

public interface ListCommand {

    long lpush(byte[] key, byte[]... elements);

    long lpushx(byte[] key, byte[]... elements);

    List<Object> lrange(byte[] key, long start, long end);

    byte[] lpop(byte[] key);

    long llen(byte[] key);

    long rpush(byte[] key, byte[]... elements);

    long rpushx(byte[] key, byte[]... elements);

    byte[] rpop(byte[] key);
}
