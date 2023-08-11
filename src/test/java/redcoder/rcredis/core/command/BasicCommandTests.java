package redcoder.rcredis.core.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;
import redcoder.rcredis.core.io.RedisConnectionFactoryImpl;

import java.util.concurrent.TimeUnit;

public class BasicCommandTests {

    private static BasicCommand command;
    private static StringCommand stringCommand;

    @BeforeAll
    public static void beforeAll() {
        RedisConnectionFactory factory = new RedisConnectionFactoryImpl();
        RedisConnection connection = factory.create("localhost", 7370);
        command = new BasicCommandImpl(connection);
        stringCommand = new StringCommandImpl(connection);
    }

    @Test
    void expire() {
        byte[] key = "RedisCommandTests".getBytes();
        Assertions.assertThat(command.expire(key, 1, TimeUnit.MINUTES)).isEqualTo(0);

        stringCommand.set(key, "1".getBytes());
        Assertions.assertThat(command.expire(key, 1, TimeUnit.MINUTES)).isEqualTo(1);
    }

    @Test
    void del() {
        byte[] key = "RedisCommandTests".getBytes();
        command.del(key);
        Assertions.assertThat(command.del(key)).isEqualTo(0);

        stringCommand.set(key, "1".getBytes());
        Assertions.assertThat(command.del(key)).isEqualTo(1);
    }
}
