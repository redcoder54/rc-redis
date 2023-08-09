package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.StringCommand;
import redcoder.rcredis.core.command.StringCommandImpl;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;
import redcoder.rcredis.core.io.RedisConnectionFactoryImpl;

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
        command.set("name", "john");
    }

    @Test
    void set_with_timeout() {
        command.set("age", "18", 1, TimeUnit.MINUTES);
        assertThat(command.get("age")).isEqualTo("18");
    }

    @Test
    void get() {
        String value = command.get("name");
        assertThat(value).isEqualTo("john");
    }

    @Test
    void incr() {
        command.set("count", "0");
        assertThat(command.incr("count")).isEqualTo(1);
        assertThat(command.incr("count")).isEqualTo(2);
    }

    @Test
    void decr() {
        command.set("count", "2");
        assertThat(command.decr("count")).isEqualTo(1);
        assertThat(command.decr("count")).isEqualTo(0);
    }
}
