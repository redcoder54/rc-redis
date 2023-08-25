package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSetOperation;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisSetOperationTests extends RedisTestSupport {

    private static RedisSetOperation operation;

    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        operation = new RedisSetOperationImpl(connection);
    }

    @Test
    void sadd() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.sadd(key, "v1", "v2", "v3")).isEqualTo(3);
        assertThat(operation.sadd(key, "v1", "v2", "v3")).isEqualTo(0);
        assertThat(operation.sadd(key, "v4", "v6", "v5")).isEqualTo(3);

        List<String> list = operation.smembers(key);
        assertThat(list).contains("v1", "v2", "v3", "v4", "v5", "v6");
    }

    @Test
    void srem() {
        String key = UUID.randomUUID().toString();
        operation.sadd(key, "v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.srem(key, "v1", "v2", "v3")).isEqualTo(3);
        assertThat(operation.srem(key, "v1", "v2", "v3")).isEqualTo(0);
        assertThat(operation.srem(key, "v4", "v6", "v5")).isEqualTo(3);
    }

    @Test
    void spop() {
        String key = UUID.randomUUID().toString();
        operation.sadd(key, "v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isIn("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key)).isNull();
    }

    @Test
    void spop_multi() {
        String key = UUID.randomUUID().toString();
        operation.sadd(key, "v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key, 3)).isSubsetOf("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key, 3)).isSubsetOf("v1", "v2", "v3", "v4", "v5", "v6");
        assertThat(operation.spop(key, 3)).isEmpty();
    }
}
