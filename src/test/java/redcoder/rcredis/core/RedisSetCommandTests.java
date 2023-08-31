package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.KeyCommand;
import redcoder.rcredis.core.command.SetCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SetCommandTests extends AbstractRedisTestsSupport {

    private static SetCommand command;
    private static KeyCommand keyCommand;

    @BeforeAll
    public static void beforeAll() {
        RedisConnection connection = getConnection();
        command = new RedisSetCommandImpl(connection);
        keyCommand = new RedisKeyCommandImpl(connection);
    }

    @Test
    void sadd() {
        byte[] key = "users".getBytes();
        keyCommand.del(key);
        assertThat(command.sadd(key, "u1".getBytes())).isEqualTo(1);
        assertThat(command.sadd(key, "u2".getBytes())).isEqualTo(1);
        assertThat(command.sadd(key, "u3".getBytes())).isEqualTo(1);
        assertThat(command.sadd(key, "u3".getBytes())).isEqualTo(0);
    }

    @Test
    void rem() {
        byte[] key = "users".getBytes();
        setup(key);
        assertThat(command.srem(key, "u1".getBytes())).isEqualTo(1);
        assertThat(command.srem(key, "u2".getBytes())).isEqualTo(1);
        assertThat(command.srem(key, "u3".getBytes())).isEqualTo(1);
        assertThat(command.srem(key, "u3".getBytes())).isEqualTo(0);
    }

    @Test
    void spop() {
        byte[] key = "users".getBytes();
        setup(key);
        assertThat(command.spop(key)).isNotNull();
        List<Object> list = command.spop(key, 2);
        assertThat(list).size().isEqualTo(2);
        list.forEach(o -> System.out.println(new String(((byte[]) o))));
    }

    @Test
    void smembers() {
        byte[] key = "users".getBytes();
        setup(key);
        List<Object> list = command.smembers(key);
        list.forEach(o -> System.out.println(new String(((byte[]) o))));
    }

    void setup(byte[] key) {
        keyCommand.del(key);
        command.sadd(key, "u1".getBytes());
        command.sadd(key, "u2".getBytes());
        command.sadd(key, "u3".getBytes());
    }
}
