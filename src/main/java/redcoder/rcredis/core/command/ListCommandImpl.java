package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;

class ListCommandImpl extends RedisCommandSupport implements ListCommand {

    public ListCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public long lpush(byte[] key, byte[]... elements) {
        return (long) executeCommand(RedisCommand.LPUSH, mergeByteArray(key, elements));
    }

    @Override
    public long lpushx(byte[] key, byte[]... elements) {
        return (long) executeCommand(RedisCommand.LPUSHX, mergeByteArray(key, elements));
    }

    @Override
    public List<Object> lrange(byte[] key, long start, long end) {
        return (List<Object>) executeCommand(RedisCommand.LRANGE, key, convertToBytes(start), convertToBytes(end));
    }

    @Override
    public byte[] lpop(byte[] key) {
        return (byte[]) executeCommand(RedisCommand.LPOP, key);
    }

    @Override
    public long llen(byte[] key) {
        return (long) executeCommand(RedisCommand.LLEN, key);
    }

    @Override
    public long rpush(byte[] key, byte[]... elements) {
        return (long) executeCommand(RedisCommand.RPUSH, mergeByteArray(key, elements));
    }

    @Override
    public long rpushx(byte[] key, byte[]... elements) {
        return (long) executeCommand(RedisCommand.RPUSHX, mergeByteArray(key, elements));
    }

    @Override
    public List<Object> rrange(byte[] key, long start, long end) {
        return (List<Object>) executeCommand(RedisCommand.RRANGE, key, convertToBytes(start), convertToBytes(end));
    }

    @Override
    public byte[] rpop(byte[] key) {
        return (byte[]) executeCommand(RedisCommand.RPOP, key);
    }
}
