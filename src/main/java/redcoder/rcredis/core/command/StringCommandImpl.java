package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.RedisCommandException;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisInputStream;
import redcoder.rcredis.core.io.RedisOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static redcoder.rcredis.core.ProtocolConstant.DOLLAR_BYTE;
import static redcoder.rcredis.core.ProtocolConstant.OK;

public class StringCommandImpl extends RedisCommandSupport implements StringCommand {

    public StringCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public void set(String key, String value) {
        sendCommand(RedisCommand.SET, key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        sendCommand(RedisCommand.SET, key, value, "PX", "" + unit.toMillis(timeout));
    }

    @Override
    public String get(String key) {
        Object resp = sendCommand(RedisCommand.GET, key);
        return resp.toString();
    }

    @Override
    public long incr(String key) {
        Object obj = sendCommand(RedisCommand.INCR, key);
        return (long) obj;
    }

    @Override
    public long decr(String key) {
        Object obj = sendCommand(RedisCommand.DECR, key);
        return (long) obj;
    }
}
