package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisZSetCommand;

import java.util.List;
import java.util.Map;

import static redcoder.rcredis.core.RedisCommand.*;

class RedisZSetCommandImpl extends RedisCommandSupport implements RedisZSetCommand {

    public RedisZSetCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public long zadd(byte[] key, double score, byte[] member) {
        return (long) executeCommand(ZADD, key, convertToBytes(score), member);
    }

    @Override
    public long zadd(byte[] key, Map<byte[], Double> memberScores) {
        byte[][] args = new byte[memberScores.size() * 2 + 1][];
        args[0] = key;
        int i = 1;
        for (Map.Entry<byte[], Double> entry : memberScores.entrySet()) {
            args[i++] = convertToBytes(entry.getValue());
            args[i++] = entry.getKey();
        }
        return (long) executeCommand(ZADD, args);
    }

    @Override
    public long zcard(byte[] key) {
        return (long) executeCommand(ZCARD, key);
    }

    @Override
    public long zcount(byte[] key, double min, double max) {
        return (long) executeCommand(ZCOUNT, key, convertToBytes(min), convertToBytes(max));
    }

    @Override
    public List<Object> zrange(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(ZRANGE, key, convertToBytes(start), convertToBytes(stop));
    }

    @Override
    public List<Object> zrangeWithScores(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(ZRANGE, key, convertToBytes(start), convertToBytes(stop), WITHSCORES);
    }

    @Override
    public List<Object> zrangeByScore(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(ZRANGEBYSCORE, key, convertToBytes(min), convertToBytes(max));
    }

    @Override
    public List<Object> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(ZRANGEBYSCORE, key, convertToBytes(min), convertToBytes(max), WITHSCORES);
    }

    @Override
    public List<Object> zrevRange(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(ZREVRANGE, key, convertToBytes(start), convertToBytes(stop));
    }

    @Override
    public List<Object> zrevRangeWithScores(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(ZREVRANGE, key, convertToBytes(start), convertToBytes(stop), WITHSCORES);
    }

    @Override
    public List<Object> zrevRangeByScore(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(ZREVRANGEBYSCORE, key, convertToBytes(max), convertToBytes(min));
    }

    @Override
    public List<Object> zrevRangeByScoreWithScores(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(ZREVRANGEBYSCORE, key, convertToBytes(max), convertToBytes(min), WITHSCORES);
    }

    @Override
    public long zrem(byte[] key, byte[]... members) {
        return (long) executeCommand(ZREM, mergeByteArray(key, members));
    }

    @Override
    public byte[] zscore(byte[] key, byte[] member) {
        return (byte[]) executeCommand(ZSCORE, key, member);
    }
}
