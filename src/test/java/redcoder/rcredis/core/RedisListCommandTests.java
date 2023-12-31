package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.RedisKeyCommand;
import redcoder.rcredis.core.command.RedisListCommand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisListCommandTests extends AbstractRedisTestsSupport {

    private static RedisKeyCommand command;
    private static RedisListCommand listCommand;

    @BeforeAll
    public static void beforeAll() {
        RedisConnection connection = getConnection();
        command = new RedisKeyCommandImpl(connection);
        listCommand = new RedisListCommandImpl(connection);
    }

    @Test
    void lpush() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.lpush(key, "a1".getBytes())).isEqualTo(1);
        assertThat(listCommand.lpush(key, "a3".getBytes(), "a3".getBytes())).isEqualTo(3);
    }

    @Test
    void lpushx() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.lpushx(key, "a1".getBytes())).isEqualTo(0);
        assertThat(listCommand.lpushx(key, "a2".getBytes())).isEqualTo(0);
        assertThat(listCommand.lpushx(key, "a3".getBytes())).isEqualTo(0);

        assertThat(listCommand.lpush(key, "a1".getBytes())).isEqualTo(1);
        assertThat(listCommand.lpushx(key, "a2".getBytes())).isEqualTo(2);
        assertThat(listCommand.lpushx(key, "a3".getBytes())).isEqualTo(3);
    }

    @Test
    void lrange() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.lpush(key, "a1".getBytes(), "a2".getBytes(), "a3".getBytes())).isEqualTo(3);
        List<Object> list = listCommand.lrange(key, 0, -1);
        assertThat(list).size().isEqualTo(3);
        assertThat(list).extracting(o -> new String((byte[]) o)).element(0).isEqualTo("a3");
        assertThat(list).extracting(o -> new String((byte[]) o)).element(1).isEqualTo("a2");
        assertThat(list).extracting(o -> new String((byte[]) o)).element(2).isEqualTo("a1");
    }

    @Test
    void lpop() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.lpop(key)).isNull();
        assertThat(listCommand.lpush(key, "a1".getBytes(), "a2".getBytes(), "a3".getBytes())).isEqualTo(3);
        assertThat(listCommand.lpop(key)).asString().isEqualTo("a3");
        assertThat(listCommand.lpop(key)).asString().isEqualTo("a2");
        assertThat(listCommand.lpop(key)).asString().isEqualTo("a1");
    }

    @Test
    void llen() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.llen(key)).isEqualTo(0);
        assertThat(listCommand.lpush(key, "a1".getBytes(), "a2".getBytes(), "a3".getBytes())).isEqualTo(3);
        assertThat(listCommand.llen(key)).isEqualTo(3);
        assertThat(listCommand.lpop(key)).asString().isEqualTo("a3");
        assertThat(listCommand.llen(key)).isEqualTo(2);
    }

    @Test
    void rpush() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.rpush(key, "a1".getBytes())).isEqualTo(1);
        assertThat(listCommand.rpush(key, "a3".getBytes(), "a3".getBytes())).isEqualTo(3);
    }

    @Test
    void rpushx() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.rpushx(key, "a1".getBytes())).isEqualTo(0);
        assertThat(listCommand.rpushx(key, "a2".getBytes())).isEqualTo(0);
        assertThat(listCommand.rpushx(key, "a3".getBytes())).isEqualTo(0);

        assertThat(listCommand.rpush(key, "a1".getBytes())).isEqualTo(1);
        assertThat(listCommand.rpushx(key, "a2".getBytes())).isEqualTo(2);
        assertThat(listCommand.rpushx(key, "a3".getBytes())).isEqualTo(3);
    }

    @Test
    void rpop() {
        byte[] key = "ListCommandTests".getBytes();
        command.del(key);
        assertThat(listCommand.rpop(key)).isNull();
        assertThat(listCommand.rpush(key, "a1".getBytes(), "a2".getBytes(), "a3".getBytes())).isEqualTo(3);
        assertThat(listCommand.rpop(key)).asString().isEqualTo("a3");
        assertThat(listCommand.rpop(key)).asString().isEqualTo("a2");
        assertThat(listCommand.rpop(key)).asString().isEqualTo("a1");
    }
}
