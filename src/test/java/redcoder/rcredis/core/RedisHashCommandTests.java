package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.RedisHashCommand;
import redcoder.rcredis.core.command.RedisKeyCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BYTE_ARRAY;

public class RedisHashCommandTests extends AbstractRedisTestsSupport {

    private static RedisKeyCommand command;
    private static RedisHashCommand hashCommand;

    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        command = new RedisKeyCommandImpl(connection);
        hashCommand = new RedisHashCommandImpl(connection);
    }

    @Test
    void hset() {
        byte[] key = "HashCommandTests".getBytes();
        command.del(key);

        assertThat(hashCommand.hset(key, "k1".getBytes(), "v1".getBytes())).isEqualTo(1);
        assertThat(hashCommand.hset(key, "k2".getBytes(), "v2".getBytes())).isEqualTo(1);
        assertThat(hashCommand.hset(key, "k3".getBytes(), "v3".getBytes())).isEqualTo(1);
        assertThat(hashCommand.hset(key, "k2".getBytes(), "v2".getBytes())).isEqualTo(0);
    }

    @Test
    void hmset() {
        byte[] key = "HashCommandTests".getBytes();
        command.del(key);
        hashCommand.hmset(key, mockData());
    }

    @Test
    void hget() {
        byte[] key = "HashCommandTests".getBytes();
        command.del(key);
        hashCommand.hmset(key, mockData());

        assertThat(hashCommand.hget(key, "k1".getBytes())).containsOnly("v1".getBytes());
        assertThat(hashCommand.hget(key, "k2".getBytes())).containsOnly("v2".getBytes());
        assertThat(hashCommand.hget(key, "k3".getBytes())).containsOnly("v3".getBytes());
    }

    @Test
    void hmget() {
        byte[] key = "HashCommandTests".getBytes();
        command.del(key);
        hashCommand.hmset(key, mockData());

        List<byte[]> list = hashCommand.hmget(key, "k1".getBytes(), "k2".getBytes(), "k3".getBytes());
        assertThat(list).size().isEqualTo(3);
        assertThat(list).element(0).asInstanceOf(BYTE_ARRAY).containsOnly("v1".getBytes());
        assertThat(list).element(1).asInstanceOf(BYTE_ARRAY).containsOnly("v2".getBytes());
        assertThat(list).element(2).asInstanceOf(BYTE_ARRAY).containsOnly("v3".getBytes());
    }

    private Map<byte[], byte[]> mockData() {
        Map<byte[], byte[]> memberScores = new HashMap<>();
        memberScores.put("k1".getBytes(), "v1".getBytes());
        memberScores.put("k2".getBytes(), "v2".getBytes());
        memberScores.put("k3".getBytes(), "v3".getBytes());
        return memberScores;
    }
}
