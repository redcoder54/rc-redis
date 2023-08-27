package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisListCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;

import static redcoder.rcredis.core.RedisCommand.*;

class RedisListCommandImpl extends RedisCommandSupport implements RedisListCommand {

    public RedisListCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public long lpush(byte[] key, byte[]... elements) {
        return (long) executeCommand(LPUSH, mergeByteArray(key, elements));
    }

    @Override
    public long lpushx(byte[] key, byte[] element) {
        return (long) executeCommand(LPUSHX, key, element);
    }

    @Override
    public List<Object> lrange(byte[] key, long start, long end) {
        return (List<Object>) executeCommand(LRANGE, key, convertToBytes(start), convertToBytes(end));
    }

    @Override
    public byte[] lpop(byte[] key) {
        return (byte[]) executeCommand(LPOP, key);
    }

    @Override
    public long llen(byte[] key) {
        return (long) executeCommand(LLEN, key);
    }

    @Override
    public long rpush(byte[] key, byte[]... elements) {
        return (long) executeCommand(RPUSH, mergeByteArray(key, elements));
    }

    @Override
    public long rpushx(byte[] key, byte[] element) {
        return (long) executeCommand(RPUSHX, key, element);
    }

    @Override
    public byte[] rpop(byte[] key) {
        return (byte[]) executeCommand(RPOP, key);
    }
}
