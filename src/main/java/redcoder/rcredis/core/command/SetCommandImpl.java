package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;

import static redcoder.rcredis.core.RedisCommand.*;

class SetCommandImpl extends RedisCommandSupport implements SetCommand {

    public SetCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public int sadd(byte[] key, byte[]... members) {
        long i = (long) executeCommand(SADD, mergeByteArray(key, members));
        return Long.valueOf(i).intValue();
    }

    @Override
    public int srem(byte[] key, byte[]... members) {
        long i = (long) executeCommand(SREM, mergeByteArray(key, members));
        return Long.valueOf(i).intValue();
    }

    @Override
    public byte[] spop(byte[] key) {
        return (byte[]) executeCommand(SPOP, key);
    }

    @Override
    public List<Object> spop(byte[] key, int count) {
        return (List<Object>) executeCommand(SPOP, key, String.valueOf(count).getBytes());
    }

    @Override
    public List<Object> smembers(byte[] key) {
        return (List<Object>) executeCommand(SMEMBERS, key);
    }
}
