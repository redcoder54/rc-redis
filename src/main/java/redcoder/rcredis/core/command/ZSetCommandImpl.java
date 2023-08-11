package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;
import java.util.Map;

public class ZSetCommandImpl extends RedisCommandSupport implements ZSetCommand {

    public ZSetCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public long zadd(byte[] key, double score, byte[] member) {
        return (long) executeCommand(RedisCommand.ZADD, key, convertToBytes(score), member);
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
        return (long) executeCommand(RedisCommand.ZADD, args);
    }

    @Override
    public long zcard(byte[] key) {
        return (long) executeCommand(RedisCommand.ZCARD, key);
    }

    @Override
    public long zcount(byte[] key, long min, long max) {
        return (long) executeCommand(RedisCommand.ZCOUNT, key, convertToBytes(min), convertToBytes(max));
    }

    @Override
    public List<Object> zrange(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(RedisCommand.ZRANGE, key, convertToBytes(start), convertToBytes(stop));
    }

    @Override
    public List<Object> zrangeWithScores(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(RedisCommand.ZRANGE, key, convertToBytes(start), convertToBytes(stop), WITHSCORES);
    }

    @Override
    public List<Object> zrangeByScore(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(RedisCommand.ZRANGEBYSCORE, key, convertToBytes(min), convertToBytes(max));
    }

    @Override
    public List<Object> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(RedisCommand.ZRANGEBYSCORE, key, convertToBytes(min), convertToBytes(max), WITHSCORES);
    }

    @Override
    public List<Object> zrevRange(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(RedisCommand.ZREVRANGE, key, convertToBytes(start), convertToBytes(stop));
    }

    @Override
    public List<Object> zrevRangeWithScores(byte[] key, long start, long stop) {
        return (List<Object>) executeCommand(RedisCommand.ZREVRANGE, key, convertToBytes(start), convertToBytes(stop), WITHSCORES);
    }

    @Override
    public List<Object> zrevRangeByScore(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(RedisCommand.ZREVRANGEBYSCORE, key, convertToBytes(max), convertToBytes(min));
    }

    @Override
    public List<Object> zrevRangeByScoreWithScores(byte[] key, double min, double max) {
        return (List<Object>) executeCommand(RedisCommand.ZREVRANGEBYSCORE, key, convertToBytes(max), convertToBytes(min), WITHSCORES);
    }

    @Override
    public long zrem(byte[] key, byte[]... members) {
        return (long) executeCommand(RedisCommand.ZREM, mergeByteArray(key, members));
    }

    @Override
    public byte[] zscore(byte[] key, byte[] members) {
        return (byte[]) executeCommand(RedisCommand.ZSCORE, key, members);
    }
}
