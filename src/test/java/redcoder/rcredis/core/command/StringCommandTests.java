package redcoder.rcredis.core.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.StringCommand;
import redcoder.rcredis.core.command.StringCommandImpl;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;
import redcoder.rcredis.core.io.RedisConnectionFactoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCommandTests {

    private static StringCommand command;

    @BeforeAll
    public static void beforeAll() {
        RedisConnectionFactory factory = new RedisConnectionFactoryImpl();
        RedisConnection connection = factory.create("localhost", 7370);
        command = new StringCommandImpl(connection);
    }

    @Test
    void set() {
        command.set("name".getBytes(), "john".getBytes());
    }

    @Test
    void set_with_timeout() {
        command.set("age".getBytes(), "18".getBytes(), 1, TimeUnit.MINUTES);
        assertThat(new String(command.get("age".getBytes()))).isEqualTo("18");
    }

    @Test
    void get() {
        byte[] value = command.get("name".getBytes());
        assertThat(new String(value)).isEqualTo("john");

        // empty string
        command.set("empty".getBytes(), "".getBytes());
        assertThat(new String(command.get("empty".getBytes()))).isEqualTo("");

        // nil
        assertThat(command.get("nooooooooooooooooooo".getBytes(StandardCharsets.UTF_8))).isNull();
    }

    @Test
    void incr() {
        command.set("count".getBytes(), "0".getBytes());
        assertThat(command.incr("count".getBytes())).isEqualTo(1);
        assertThat(command.incr("count".getBytes())).isEqualTo(2);
    }

    @Test
    void decr() {
        command.set("count".getBytes(), "2".getBytes());
        assertThat(command.decr("count".getBytes())).isEqualTo(1);
        assertThat(command.decr("count".getBytes())).isEqualTo(0);
    }
}
