package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisZSetOperation;
import redcoder.rcredis.core.operation.Tuple;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisZSetOperationTests extends AbstractRedisTestsSupport {

    private static RedisZSetOperation operation;

    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        operation = new RedisZSetOperationImpl(connection);
    }

    @Test
    void zadd() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, 1, "m1")).isEqualTo(1);
        assertThat(operation.zadd(key, 2, "m2")).isEqualTo(1);
        assertThat(operation.zadd(key, 3.1, "m3")).isEqualTo(1);
        assertThat(operation.zadd(key, 0.1, "m4")).isEqualTo(1);

        assertThat(operation.zrange(key, 0, -1)).contains("m1", "m2", "m3", "m4");
        assertThat(operation.zrange(key, 0, 0)).containsExactly("m4");
        assertThat(operation.zrange(key, 1, 1)).containsExactly("m1");
        assertThat(operation.zrange(key, 2, 2)).containsExactly("m2");
        assertThat(operation.zrange(key, 3, 3)).containsExactly("m3");
    }

    @Test
    void zadd_multi() {
        String key = UUID.randomUUID().toString();

        Map<String, Double> memberScores = new java.util.HashMap<>();
        memberScores.put("m1", 1d);
        memberScores.put("m2", 2d);
        memberScores.put("m3", 3.1);
        memberScores.put("m4", 0.1);
        assertThat(operation.zadd(key, memberScores)).isEqualTo(4);

        assertThat(operation.zrange(key, 0, -1)).contains("m1", "m2", "m3", "m4");
        assertThat(operation.zrange(key, 0, 0)).containsExactly("m4");
        assertThat(operation.zrange(key, 1, 1)).containsExactly("m1");
        assertThat(operation.zrange(key, 2, 2)).containsExactly("m2");
        assertThat(operation.zrange(key, 3, 3)).containsExactly("m3");
    }

    @Test
    void zcard() {
        String key = UUID.randomUUID().toString();

        Map<String, Double> memberScores = new java.util.HashMap<>();
        memberScores.put("m1", 1d);
        memberScores.put("m2", 2d);
        memberScores.put("m3", 3.1);
        memberScores.put("m4", 0.1);
        assertThat(operation.zadd(key, memberScores)).isEqualTo(4);

        assertThat(operation.zcard(key)).isEqualTo(4);
    }

    @Test
    void zcount() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zcount(key, 0, 0)).isEqualTo(0);
        assertThat(operation.zcount(key, 0, 0.5)).isEqualTo(1);
        assertThat(operation.zcount(key, 0, 1)).isEqualTo(2);
        assertThat(operation.zcount(key, 0, 2)).isEqualTo(3);
        assertThat(operation.zcount(key, 0, 3)).isEqualTo(3);
        assertThat(operation.zcount(key, 0, 4)).isEqualTo(4);
    }

    @Test
    void zrangeWithScores() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        // m4-0.1, m1-1
        List<Tuple<String, Double>> m1_m4 = operation.zrangeWithScores(key, 0, 1);
        assertThat(m1_m4).size().isEqualTo(2);
        assertThat(m1_m4).element(0).extracting(Tuple::getLeft).isEqualTo("m4");
        assertThat(m1_m4).element(0).extracting(Tuple::getRight).isEqualTo(0.1);
        assertThat(m1_m4).element(1).extracting(Tuple::getLeft).isEqualTo("m1");
        assertThat(m1_m4).element(1).extracting(Tuple::getRight).isEqualTo(1.0);

        // m2-2, m3-3.1
        List<Tuple<String, Double>> m2_m3 = operation.zrangeWithScores(key, 2, 4);
        assertThat(m2_m3).size().isEqualTo(2);
        assertThat(m2_m3).element(0).extracting(Tuple::getLeft).isEqualTo("m2");
        assertThat(m2_m3).element(0).extracting(Tuple::getRight).isEqualTo(2.0);
        assertThat(m2_m3).element(1).extracting(Tuple::getLeft).isEqualTo("m3");
        assertThat(m2_m3).element(1).extracting(Tuple::getRight).isEqualTo(3.1);
    }

    @Test
    void zrangeByScore() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zrangeByScore(key, 0, 0)).isEmpty();
        assertThat(operation.zrangeByScore(key, 0, 0.1)).containsExactly("m4");
        assertThat(operation.zrangeByScore(key, 0, 0.2)).containsExactly("m4");
        assertThat(operation.zrangeByScore(key, 0, 1)).containsExactly("m4", "m1");
        assertThat(operation.zrangeByScore(key, 0, 2)).containsExactly("m4", "m1", "m2");
        assertThat(operation.zrangeByScore(key, 0, 3)).containsExactly("m4", "m1", "m2");
        assertThat(operation.zrangeByScore(key, 0, 4)).containsExactly("m4", "m1", "m2", "m3");
    }

    @Test
    void zrangeByScoreWithScores() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        // m4-0.1, m1-1
        List<Tuple<String, Double>> m1_m4 = operation.zrangeByScoreWithScores(key, 0, 1.1);
        assertThat(m1_m4).size().isEqualTo(2);
        assertThat(m1_m4).element(0).extracting(Tuple::getLeft).isEqualTo("m4");
        assertThat(m1_m4).element(0).extracting(Tuple::getRight).isEqualTo(0.1);
        assertThat(m1_m4).element(1).extracting(Tuple::getLeft).isEqualTo("m1");
        assertThat(m1_m4).element(1).extracting(Tuple::getRight).isEqualTo(1.0);

        // m2-2, m3-3.1
        List<Tuple<String, Double>> m2_m3 = operation.zrangeByScoreWithScores(key, 1.5, 4);
        assertThat(m2_m3).size().isEqualTo(2);
        assertThat(m2_m3).element(0).extracting(Tuple::getLeft).isEqualTo("m2");
        assertThat(m2_m3).element(0).extracting(Tuple::getRight).isEqualTo(2.0);
        assertThat(m2_m3).element(1).extracting(Tuple::getLeft).isEqualTo("m3");
        assertThat(m2_m3).element(1).extracting(Tuple::getRight).isEqualTo(3.1);
    }

    @Test
    void zrevRange() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zrevRange(key, 0, 0)).containsExactly("m3");
        assertThat(operation.zrevRange(key, 0, 1)).containsExactly("m3", "m2");
        assertThat(operation.zrevRange(key, 0, 2)).containsExactly("m3", "m2", "m1");
        assertThat(operation.zrevRange(key, 0, 3)).containsExactly("m3", "m2", "m1", "m4");
        assertThat(operation.zrevRange(key, 0, -1)).containsExactly("m3", "m2", "m1", "m4");
    }

    @Test
    void zrevRangeWithScores() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        // m4-0.1, m1-1
        List<Tuple<String, Double>> m1_m4 = operation.zrevRangeWithScores(key, 2, 3);
        assertThat(m1_m4).size().isEqualTo(2);
        assertThat(m1_m4).element(0).extracting(Tuple::getLeft).isEqualTo("m1");
        assertThat(m1_m4).element(0).extracting(Tuple::getRight).isEqualTo(1.0);
        assertThat(m1_m4).element(1).extracting(Tuple::getLeft).isEqualTo("m4");
        assertThat(m1_m4).element(1).extracting(Tuple::getRight).isEqualTo(0.1);

        // m2-2, m3-3.1
        List<Tuple<String, Double>> m2_m3 = operation.zrevRangeWithScores(key, 0, 1);
        assertThat(m2_m3).size().isEqualTo(2);
        assertThat(m2_m3).element(0).extracting(Tuple::getLeft).isEqualTo("m3");
        assertThat(m2_m3).element(0).extracting(Tuple::getRight).isEqualTo(3.1);
        assertThat(m2_m3).element(1).extracting(Tuple::getLeft).isEqualTo("m2");
        assertThat(m2_m3).element(1).extracting(Tuple::getRight).isEqualTo(2.0);
    }

    @Test
    void zrevRangeByScore() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zrevRangeByScore(key, 0, 0)).isEmpty();
        assertThat(operation.zrevRangeByScore(key, 0, 0.5)).containsExactly("m4");
        assertThat(operation.zrevRangeByScore(key, 0, 1)).containsExactly("m1", "m4");
        assertThat(operation.zrevRangeByScore(key, 0, 2)).containsExactly("m2", "m1", "m4");
        assertThat(operation.zrevRangeByScore(key, 0, 3)).containsExactly("m2", "m1", "m4");
        assertThat(operation.zrevRangeByScore(key, 0, 4)).containsExactly("m3", "m2", "m1", "m4");
    }

    @Test
    void zrevRangeByScoreWithScores() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        // m4-0.1, m1-1
        List<Tuple<String, Double>> m1_m4 = operation.zrevRangeByScoreWithScores(key, 0, 1);
        assertThat(m1_m4).size().isEqualTo(2);
        assertThat(m1_m4).element(0).extracting(Tuple::getLeft).isEqualTo("m1");
        assertThat(m1_m4).element(0).extracting(Tuple::getRight).isEqualTo(1.0);
        assertThat(m1_m4).element(1).extracting(Tuple::getLeft).isEqualTo("m4");
        assertThat(m1_m4).element(1).extracting(Tuple::getRight).isEqualTo(0.1);

        // m2-2, m3-3.1
        List<Tuple<String, Double>> m2_m3 = operation.zrevRangeByScoreWithScores(key, 2, 4);
        assertThat(m2_m3).size().isEqualTo(2);
        assertThat(m2_m3).element(0).extracting(Tuple::getLeft).isEqualTo("m3");
        assertThat(m2_m3).element(0).extracting(Tuple::getRight).isEqualTo(3.1);
        assertThat(m2_m3).element(1).extracting(Tuple::getLeft).isEqualTo("m2");
        assertThat(m2_m3).element(1).extracting(Tuple::getRight).isEqualTo(2.0);
    }

    @Test
    void zrem() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zrem(key, "m1")).isEqualTo(1);
        assertThat(operation.zrem(key, "m1", "m2")).isEqualTo(1);
        assertThat(operation.zrem(key, "m3", "m4")).isEqualTo(2);
        assertThat(operation.zrem(key, "m3", "m4")).isEqualTo(0);
    }

    @Test
    void zscore() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.zadd(key, mockData())).isEqualTo(4);

        assertThat(operation.zscore(key, "m1")).isEqualTo(1.0);
        assertThat(operation.zscore(key, "m2")).isEqualTo(2.0);
        assertThat(operation.zscore(key, "m3")).isEqualTo(3.1);
        assertThat(operation.zscore(key, "m4")).isEqualTo(0.1);
    }

    private Map<String, Double> mockData() {
        Map<String, Double> memberScores = new java.util.HashMap<>();
        memberScores.put("m1", 1.0);
        memberScores.put("m2", 2.0);
        memberScores.put("m3", 3.1);
        memberScores.put("m4", 0.1);
        return memberScores;
    }
}
