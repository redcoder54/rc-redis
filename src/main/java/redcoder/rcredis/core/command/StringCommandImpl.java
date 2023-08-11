package redcoder.rcredis.core.command;

import redcoder.rcredis.core.io.RedisConnection;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static redcoder.rcredis.core.RedisCommand.*;

class StringCommandImpl extends RedisCommandSupport implements StringCommand {

    public StringCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public void set(byte[] key, byte[] value) {
        executeCommand(SET, key, value);
    }

    @Override
    public void set(byte[] key, byte[] value, long timeout, TimeUnit unit) {
        executeCommand(SET, key, value, PX, String.valueOf(unit.toMillis(timeout)).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] get(byte[] key) {
        Object resp = executeCommand(GET, key);
        return (byte[]) resp;
    }

    @Override
    public long incr(byte[] key) {
        Object obj = executeCommand(INCR, key);
        return (long) obj;
    }

    @Override
    public long decr(byte[] key) {
        Object obj = executeCommand(DECR, key);
        return (long) obj;
    }
}
