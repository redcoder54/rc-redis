package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSerializer;
import redcoder.rcredis.core.operation.RedisStringOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisStringOperationTests extends RedisTestSupport {

    private static RedisStringOperation<String,String> operation;
    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        RedisSerializer<String> serializer = new StringRedisSerializer();
        operation = new RedisStringOperationImpl<>(serializer, serializer, connection);
    }

    @Test
    void set_get() {
        operation.set("name", "john");
        assertThat(operation.get("name")).isEqualTo("john");
    }

    @Test
    void incr_decr() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.incr(key)).isEqualTo(1);
        assertThat(operation.incr(key)).isEqualTo(2);
        assertThat(operation.incr(key)).isEqualTo(3);
        assertThat(operation.incr(key)).isEqualTo(4);

        assertThat(operation.decr(key)).isEqualTo(3);
        assertThat(operation.decr(key)).isEqualTo(2);
        assertThat(operation.decr(key)).isEqualTo(1);
        assertThat(operation.decr(key)).isEqualTo(0);
        assertThat(operation.decr(key)).isEqualTo(-1);
    }
}
