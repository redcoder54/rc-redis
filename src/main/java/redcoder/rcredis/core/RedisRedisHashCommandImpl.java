package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisHashCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;
import java.util.Map;

import static redcoder.rcredis.core.RedisCommand.*;

class RedisRedisHashCommandImpl extends RedisCommandSupport implements RedisHashCommand {

    public RedisRedisHashCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public long hset(byte[] key, byte[] field, byte[] value) {
        return (long) executeCommand(HSET, key, field, value);
    }

    @Override
    public void hmset(byte[] key, Map<byte[], byte[]> hash) {
        byte[][] args = new byte[hash.size() * 2 + 1][];
        args[0] = key;
        int i = 1;
        for (Map.Entry<byte[], byte[]> entry : hash.entrySet()) {
            args[i++] = entry.getKey();
            args[i++] = entry.getValue();
        }
        executeCommand(HMSET, args);
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        return (byte[]) executeCommand(HGET, key, field);
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        return (List<byte[]>) executeCommand(HMGET, mergeByteArray(key, fields));
    }
}
