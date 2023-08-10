package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;

class SetCommandImpl extends RedisCommandSupport implements SetCommand {

    public SetCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public int sadd(byte[] key, byte[]... members) {
        long i = (long) executeCommand(RedisCommand.SADD, mergeByteArray(key, members));
        return Long.valueOf(i).intValue();
    }

    @Override
    public int srem(byte[] key, byte[]... members) {
        long i = (long) executeCommand(RedisCommand.SREM, mergeByteArray(key, members));
        return Long.valueOf(i).intValue();
    }

    @Override
    public byte[] spop(byte[] key) {
        return (byte[]) executeCommand(RedisCommand.SPOP, key);
    }

    @Override
    public List<Object> spop(byte[] key, int count) {
        return (List<Object>) executeCommand(RedisCommand.SPOP, key, String.valueOf(count).getBytes());
    }

    @Override
    public List<Object> smembers(byte[] key) {
        return (List<Object>) executeCommand(RedisCommand.SMEMBERS, key);
    }
}
