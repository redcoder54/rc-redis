package redcoder.rcredis.core.command;

import java.util.concurrent.TimeUnit;

public interface RedisStringCommand {

    byte[] PX = {'P', 'X'};

    void set(byte[] key, byte[] value);

    void set(byte[] key, byte[] value, long timeout, TimeUnit unit);

    long incr(byte[] key);

    long decr(byte[] key);

    byte[] get(byte[] key);
}
