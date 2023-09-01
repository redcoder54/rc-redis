package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple redis client that supports common redis command.
 */
public class Redis implements RedisStringOperation, RedisListOperation, RedisSetOperation, RedisZSetOperation,
        RedisHashOperation, Closeable {

    private RedisConnection connection;
    private RedisStringOperation stringOperation;
    private RedisListOperation listOperation;
    private RedisSetOperation setOperation;
    private RedisZSetOperation zSetOperation;
    private RedisHashOperation hashOperation;

    public Redis(RedisConnection connection) {
        this.connection = connection;
        this.stringOperation = new RedisStringOperationImpl(connection);
        this.listOperation = new RedisListOperationImpl(connection);
        this.setOperation = new RedisSetOperationImpl(connection);
        this.zSetOperation = new RedisZSetOperationImpl(connection);
        this.hashOperation = new RedisHashOperationImpl(connection);
    }

    // -------- string command
    @Override
    public void set(String key, String value) {
        stringOperation.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        stringOperation.set(key, value, timeout, unit);
    }

    @Override
    public long incr(String key) {
        return stringOperation.incr(key);
    }

    @Override
    public long decr(String key) {
        return stringOperation.decr(key);
    }

    @Override
    public String get(String key) {
        return stringOperation.get(key);
    }

    // ---------- list command

    @Override
    public long lpush(String key, String... elements) {
        return listOperation.lpush(key, elements);
    }

    @Override
    public long lpushx(String key, String element) {
        return listOperation.lpushx(key, element);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return listOperation.lrange(key, start, end);
    }

    @Override
    public String lpop(String key) {
        return listOperation.lpop(key);
    }

    @Override
    public long llen(String key) {
        return listOperation.llen(key);
    }

    @Override
    public long rpush(String key, String... elements) {
        return listOperation.rpush(key, elements);
    }

    @Override
    public long rpushx(String key, String element) {
        return listOperation.rpushx(key, element);
    }

    @Override
    public String rpop(String key) {
        return listOperation.rpop(key);
    }

    // ------------ set command

    @Override
    public int sadd(String key, String... members) {
        return setOperation.sadd(key, members);
    }

    @Override
    public int srem(String key, String... members) {
        return setOperation.srem(key, members);
    }

    @Override
    public String spop(String key) {
        return setOperation.spop(key);
    }

    @Override
    public List<String> spop(String key, int count) {
        return setOperation.spop(key, count);
    }

    @Override
    public List<String> smembers(String key) {
        return setOperation.smembers(key);
    }

    // ------------- zset command

    @Override
    public long zadd(String key, double score, String member) {
        return zSetOperation.zadd(key, score, member);
    }

    @Override
    public long zadd(String key, Map<String, Double> memberScores) {
        return zadd(key, memberScores);
    }

    @Override
    public long zcard(String key) {
        return zSetOperation.zcard(key);
    }

    @Override
    public long zcount(String key, double min, double max) {
        return zSetOperation.zcount(key, min, max);
    }

    @Override
    public List<String> zrange(String key, long start, long stop) {
        return zSetOperation.zrange(key, start, stop);
    }

    @Override
    public List<Tuple<String, Double>> zrangeWithScores(String key, long start, long stop) {
        return zSetOperation.zrangeWithScores(key, start, stop);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        return zSetOperation.zrangeByScore(key, min, max);
    }

    @Override
    public List<Tuple<String, Double>> zrangeByScoreWithScores(String key, double min, double max) {
        return zSetOperation.zrangeByScoreWithScores(key, min, max);
    }

    @Override
    public List<String> zrevRange(String key, long start, long stop) {
        return zSetOperation.zrevRange(key, start, stop);
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeWithScores(String key, long start, long stop) {
        return zSetOperation.zrevRangeWithScores(key, start, stop);
    }

    @Override
    public List<String> zrevRangeByScore(String key, double min, double max) {
        return zSetOperation.zrevRangeByScore(key, min, max);
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeByScoreWithScores(String key, double min, double max) {
        return zSetOperation.zrevRangeByScoreWithScores(key, min, max);
    }

    @Override
    public long zrem(String key, String... members) {
        return zSetOperation.zrem(key, members);
    }

    @Override
    public Double zscore(String key, String members) {
        return zSetOperation.zscore(key, members);
    }

    // ------------- hash command

    @Override
    public long hset(String key, String field, String value) {
        return hashOperation.hset(key, field, value);
    }

    @Override
    public void hmset(String key, Map<String, String> hash) {
        hashOperation.hmset(key, hash);
    }

    @Override
    public String hget(String key, String field) {
        return hashOperation.hget(key, field);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return hashOperation.hmget(key, fields);
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }
}
