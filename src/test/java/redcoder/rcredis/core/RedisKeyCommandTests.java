package redcoder.rcredis.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.command.RedisKeyCommand;
import redcoder.rcredis.core.command.RedisStringCommand;

import java.util.concurrent.TimeUnit;

public class RedisKeyCommandTests extends AbstractRedisTestsSupport {

    private static RedisKeyCommand command;
    private static RedisStringCommand stringCommand;

    @BeforeAll
    public static void beforeAll() {
        RedisConnection connection = getConnection();
        command = new RedisKeyCommandImpl(connection);
        stringCommand = new RedisStringCommandImpl(connection);
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
