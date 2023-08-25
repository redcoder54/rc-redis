package redcoder.rcredis.core.command;

import java.util.List;
import java.util.Map;

public interface ZSetCommand {

    byte[] WITHSCORES = new byte[]{'W', 'I', 'T', 'H', 'S', 'C', 'O', 'R', 'E', 'S'};
    // byte[] BYSCORE = new byte[]{'B', 'Y', 'S', 'C', 'O', 'R', 'E'};
    // byte[] BYLEN = new byte[]{'B', 'Y', 'L', 'E', 'N'};
    // byte[] REV = new byte[]{'R', 'E', 'V'};

    long zadd(byte[] key, double score, byte[] member);

    long zadd(byte[] key, Map<byte[], Double> memberScores);

    long zcard(byte[] key);

    long zcount(byte[] key, double min, double max);

    List<Object> zrange(byte[] key, long start, long stop);

    List<Object> zrangeWithScores(byte[] key, long start, long stop);

    List<Object> zrangeByScore(byte[] key, double min, double max);

    List<Object> zrangeByScoreWithScores(byte[] key, double min, double max);

    List<Object> zrevRange(byte[] key, long start, long stop);

    List<Object> zrevRangeWithScores(byte[] key, long start, long stop);

    List<Object> zrevRangeByScore(byte[] key, double min, double max);

    List<Object> zrevRangeByScoreWithScores(byte[] key, double min, double max);

    long zrem(byte[] key, byte[]... members);

    byte[] zscore(byte[] key, byte[] members);
}
