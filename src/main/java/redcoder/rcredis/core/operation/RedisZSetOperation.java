package redcoder.rcredis.core.operation;

import java.util.List;
import java.util.Map;

public interface RedisZSetOperation<K, V> {

    long zadd(K key, double score, V member);

    long zadd(K key, Map<K, Double> memberScores);

    long zcard(K key);

    long zcount(K key, double min, double max);

    List<V> zrange(K key, long start, long stop);

    List<Tuple<V, Double>> zrangeWithScores(K key, long start, long stop);

    List<V> zrangeByScore(K key, double min, double max);

    List<Tuple<V, Double>> zrangeByScoreWithScores(K key, double min, double max);

    List<V> zrevRange(K key, long start, long stop);

    List<Tuple<V, Double>> zrevRangeWithScores(K key, long start, long stop);

    List<V> zrevRangeByScore(K key, double min, double max);

    List<Tuple<V, Double>> zrevRangeByScoreWithScores(K key, double min, double max);

    long zrem(K key, V... members);

    Double zscore(K key, V members);
}
