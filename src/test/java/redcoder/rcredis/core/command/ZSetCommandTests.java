package redcoder.rcredis.core.command;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;
import redcoder.rcredis.core.io.RedisConnectionFactoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.*;

public class ZSetCommandTests {

    private static RedisCommand command;
    private static ZSetCommand zSetCommand;

    @BeforeAll
    static void beforeAll() {
        RedisConnectionFactory factory = new RedisConnectionFactoryImpl();
        RedisConnection connection = factory.create("localhost", 7370);
        command = new RedisCommandImpl(connection);
        zSetCommand = new ZSetCommandImpl(connection);
    }

    @Test
    void zadd() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);
        assertThat(zSetCommand.zadd(key, 1, "z1".getBytes())).isEqualTo(1);
        assertThat(zSetCommand.zadd(key, 3, "z2".getBytes())).isEqualTo(1);
        assertThat(zSetCommand.zadd(key, 3, "z2".getBytes())).isEqualTo(0);
        assertThat(zSetCommand.zadd(key, 5, "z3".getBytes())).isEqualTo(1);
    }

    @Test
    void zadd_multi() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(0);
    }

    @Test
    void zcard() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);
        assertThat(zSetCommand.zcard(key)).isEqualTo(0);

        assertThat(zSetCommand.zadd(key, 1, "z1".getBytes())).isEqualTo(1);
        assertThat(zSetCommand.zcard(key)).isEqualTo(1);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(2);

        assertThat(zSetCommand.zcard(key)).isEqualTo(3);
    }

    @Test
    void zcount() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        assertThat(zSetCommand.zcount(key, 2, 4)).isEqualTo(0);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        assertThat(zSetCommand.zcount(key, 2, 4)).isEqualTo(1);
    }

    @Test
    void zrange() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        assertThat(zSetCommand.zrange(key, 0, -1)).isEmpty();

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrange(key, 0, -1);
        assertThat(list).size().isEqualTo(3);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
    }

    @Test
    void zrangeWithScores() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        assertThat(zSetCommand.zrangeWithScores(key, 0, -1)).isEmpty();

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrangeWithScores(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).isEqualTo("1".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).isEqualTo("3".getBytes());
        assertThat(list.get(4)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(5)).asInstanceOf(BYTE_ARRAY).isEqualTo("5".getBytes());
    }

    @Test
    void zrangeByScore() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrangeByScore(key, 0, 10);
        assertThat(list).size().isEqualTo(3);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
    }

    @Test
    void zrangeByScoreWithScores() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrangeByScoreWithScores(key, 0, 10);
        assertThat(list).size().isEqualTo(6);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).isEqualTo("1".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).isEqualTo("3".getBytes());
        assertThat(list.get(4)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(5)).asInstanceOf(BYTE_ARRAY).isEqualTo("5".getBytes());
    }

    @Test
    void zrevRange() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrevRange(key, 0, 0);
        assertThat(list).size().isEqualTo(1);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());

        list = zSetCommand.zrevRange(key, 0, 1);
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());

        list = zSetCommand.zrevRange(key, 0, 2);
        assertThat(list).size().isEqualTo(3);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());

        list = zSetCommand.zrevRange(key, 0, 3);
        assertThat(list).size().isEqualTo(3);

        list = zSetCommand.zrevRange(key, 0, -1);
        assertThat(list).size().isEqualTo(3);
    }

    @Test
    void zrevRangeWithScores() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrevRangeWithScores(key, 0, 0);
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());

        list = zSetCommand.zrevRangeWithScores(key, 0, 1);
        assertThat(list).size().isEqualTo(4);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).containsOnly("3".getBytes());

        list = zSetCommand.zrevRangeWithScores(key, 0, 2);
        assertThat(list).size().isEqualTo(6);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).containsOnly("3".getBytes());
        assertThat(list.get(4)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(5)).asInstanceOf(BYTE_ARRAY).containsOnly("1".getBytes());

        list = zSetCommand.zrevRangeWithScores(key, 0, 3);
        assertThat(list).size().isEqualTo(6);

        list = zSetCommand.zrevRangeWithScores(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
    }

    @Test
    void zrevRangeByScore() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrevRangeByScore(key, 4, 5);
        assertThat(list).size().isEqualTo(1);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());

        list = zSetCommand.zrevRangeByScore(key, 3, 5);
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());

        list = zSetCommand.zrevRangeByScore(key, 1, 5);
        assertThat(list).size().isEqualTo(3);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
    }

    @Test
    void zrevRangeByScoreWithScores() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        List<Object> list = zSetCommand.zrevRangeByScoreWithScores(key, 4, 5);
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());

        list = zSetCommand.zrevRangeByScoreWithScores(key, 3, 5);
        assertThat(list).size().isEqualTo(4);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).containsOnly("3".getBytes());

        list = zSetCommand.zrevRangeByScoreWithScores(key, 1, 5);
        assertThat(list).size().isEqualTo(6);
        assertThat(list.get(0)).asInstanceOf(BYTE_ARRAY).containsOnly("z3".getBytes());
        assertThat(list.get(1)).asInstanceOf(BYTE_ARRAY).containsOnly("5".getBytes());
        assertThat(list.get(2)).asInstanceOf(BYTE_ARRAY).containsOnly("z2".getBytes());
        assertThat(list.get(3)).asInstanceOf(BYTE_ARRAY).containsOnly("3".getBytes());
        assertThat(list.get(4)).asInstanceOf(BYTE_ARRAY).containsOnly("z1".getBytes());
        assertThat(list.get(5)).asInstanceOf(BYTE_ARRAY).containsOnly("1".getBytes());
    }

    @Test
    void zrem() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        assertThat(zSetCommand.zrem(key, "z1".getBytes())).isEqualTo(1);
        assertThat(zSetCommand.zrem(key, "z1".getBytes(), "z2".getBytes(), "z3".getBytes())).isEqualTo(2);
        assertThat(zSetCommand.zrem(key, "z1".getBytes(), "z2".getBytes(), "z3".getBytes())).isEqualTo(0);
    }

    @Test
    void zscore() {
        byte[] key = "ZSetCommandTests".getBytes();
        command.del(key);

        assertThat(zSetCommand.zscore(key, "z1".getBytes())).isNull();
        assertThat(zSetCommand.zscore(key, "z2".getBytes())).isNull();
        assertThat(zSetCommand.zscore(key, "z3".getBytes())).isNull();

        // add 3 member
        Map<byte[], Double> memberScores = mockData();
        assertThat(zSetCommand.zadd(key, memberScores)).isEqualTo(3);

        assertThat(zSetCommand.zscore(key, "z1".getBytes())).containsOnly("1".getBytes());
        assertThat(zSetCommand.zscore(key, "z2".getBytes())).containsOnly("3".getBytes());
        assertThat(zSetCommand.zscore(key, "z3".getBytes())).containsOnly("5".getBytes());
    }


    private Map<byte[], Double> mockData() {
        Map<byte[], Double> memberScores = new HashMap<>();
        memberScores.put("z1".getBytes(), 1.0);
        memberScores.put("z2".getBytes(), 3.0);
        memberScores.put("z3".getBytes(), 5.0);
        return memberScores;
    }
}
