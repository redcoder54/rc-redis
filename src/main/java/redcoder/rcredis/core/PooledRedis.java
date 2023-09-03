package redcoder.rcredis.core;

import redcoder.rcredis.core.operation.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A thread-safe redis client that uses connection pool.
 */
public class PooledRedis implements RedisStringOperation, RedisListOperation, RedisSetOperation, RedisZSetOperation,
        RedisHashOperation, Closeable {

    public interface RedisAction<T> {

        T doAction(Redis redis);

    }

    private RedisPool pool;

    public PooledRedis(RedisPool pool) {
        this.pool = pool;
    }

    public <T> T execute(RedisAction<T> action) {
        Redis redis = pool.getResource();
        try {
            return action.doAction(redis);
        } finally {
            pool.returnResource(redis);
        }
    }

    @Override
    public void set(String key, String value) {
        execute((RedisAction<Void>) redis -> {
            redis.set(key, value);
            return null;
        });
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        execute((RedisAction<Void>) redis -> {
            redis.set(key, value, timeout, unit);
            return null;
        });
    }

    @Override
    public long incr(String key) {
        return execute(redis -> redis.incr(key));
    }

    @Override
    public long decr(String key) {
        return execute(redis -> redis.decr(key));
    }

    @Override
    public String get(String key) {
        return execute(redis -> redis.get(key));
    }

    @Override
    public long lpush(String key, String... elements) {
        return execute(redis -> redis.lpush(key, elements));
    }

    @Override
    public long lpushx(String key, String element) {
        return execute(redis -> redis.lpushx(key, element));
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return execute(redis -> redis.lrange(key, start, end));
    }

    @Override
    public String lpop(String key) {
        return execute(redis -> redis.lpop(key));
    }

    @Override
    public long llen(String key) {
        return execute(redis -> redis.llen(key));
    }

    @Override
    public long rpush(String key, String... elements) {
        return execute(redis -> redis.rpush(key, elements));
    }

    @Override
    public long rpushx(String key, String element) {
        return execute(redis -> redis.rpushx(key, element));
    }

    @Override
    public String rpop(String key) {
        return execute(redis -> redis.rpop(key));
    }

    @Override
    public int sadd(String key, String... members) {
        return execute(redis -> redis.sadd(key, members));
    }

    @Override
    public int srem(String key, String... members) {
        return execute(redis -> redis.srem(key, members));
    }

    @Override
    public String spop(String key) {
        return execute(redis -> redis.spop(key));
    }

    @Override
    public List<String> spop(String key, int count) {
        return execute(redis -> redis.spop(key, count));
    }

    @Override
    public List<String> smembers(String key) {
        return execute(redis -> redis.smembers(key));
    }

    @Override
    public long zadd(String key, double score, String member) {
        return execute(redis -> redis.zadd(key, score, member));
    }

    @Override
    public long zadd(String key, Map<String, Double> memberScores) {
        return execute(redis -> redis.zadd(key, memberScores));
    }

    @Override
    public long zcard(String key) {
        return execute(redis -> redis.zcard(key));
    }

    @Override
    public long zcount(String key, double min, double max) {
        return execute(redis -> redis.zcount(key, min, max));
    }

    @Override
    public List<String> zrange(String key, long start, long stop) {
        return execute(redis -> redis.zrange(key, start, stop));
    }

    @Override
    public List<Tuple<String, Double>> zrangeWithScores(String key, long start, long stop) {
        return execute(redis -> redis.zrangeWithScores(key, start, stop));
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        return execute(redis -> redis.zrangeByScore(key, min, max));
    }

    @Override
    public List<Tuple<String, Double>> zrangeByScoreWithScores(String key, double min, double max) {
        return execute(redis -> redis.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    public List<String> zrevRange(String key, long start, long stop) {
        return execute(redis -> redis.zrevRange(key, start, stop));
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeWithScores(String key, long start, long stop) {
        return execute(redis -> redis.zrevRangeWithScores(key, start, stop));
    }

    @Override
    public List<String> zrevRangeByScore(String key, double min, double max) {
        return execute(redis -> redis.zrevRangeByScore(key, min, max));
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeByScoreWithScores(String key, double min, double max) {
        return execute(redis -> redis.zrevRangeByScoreWithScores(key, min, max));
    }

    @Override
    public long zrem(String key, String... members) {
        return execute(redis -> redis.zrem(key, members));
    }

    @Override
    public Double zscore(String key, String member) {
        return execute(redis -> redis.zscore(key, member));
    }

    @Override
    public long hset(String key, String field, String value) {
        return execute(redis -> redis.hset(key, field, value));
    }

    @Override
    public void hmset(String key, Map<String, String> hash) {
        execute((RedisAction<Void>) redis -> {
            redis.hmset(key, hash);
            return null;
        });
    }

    @Override
    public String hget(String key, String field) {
        return execute(redis -> redis.hget(key, field));
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return execute(redis -> redis.hmget(key, fields));
    }

    @Override
    public void close() throws IOException {
        pool.close();
    }
}
