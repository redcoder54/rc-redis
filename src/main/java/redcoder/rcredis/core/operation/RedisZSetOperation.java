package redcoder.rcredis.core.operation;

import java.util.List;
import java.util.Map;

public interface RedisZSetOperation {

    long zadd(String key, double score, String member);

    long zadd(String key, Map<String, Double> memberScores);

    long zcard(String key);

    long zcount(String key, double min, double max);

    List<String> zrange(String key, long start, long stop);

    List<Tuple<String, Double>> zrangeWithScores(String key, long start, long stop);

    List<String> zrangeByScore(String key, double min, double max);

    List<Tuple<String, Double>> zrangeByScoreWithScores(String key, double min, double max);

    List<String> zrevRange(String key, long start, long stop);

    List<Tuple<String, Double>> zrevRangeWithScores(String key, long start, long stop);

    List<String> zrevRangeByScore(String key, double min, double max);

    List<Tuple<String, Double>> zrevRangeByScoreWithScores(String key, double min, double max);

    long zrem(String key, String... members);

    Double zscore(String key, String members);
}
