package redcoder.rcredis.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisListOperation;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisListOperationTests extends AbstractRedisTestsSupport {

    private static RedisListOperation operation;

    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        operation = new RedisListOperationImpl(connection);
    }

    @Test
    void lpush() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.lpush(key, "v1", "v2", "v3")).isEqualTo(3);
        assertThat(operation.lpush(key, "v1")).isEqualTo(4);
        assertThat(operation.lpush(key, "v4", "v5")).isEqualTo(6);

        List<String> list = operation.lrange(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
        assertThat(list).element(0).isEqualTo("v5");
        assertThat(list).element(1).isEqualTo("v4");
        assertThat(list).element(2).isEqualTo("v1");
        assertThat(list).element(3).isEqualTo("v3");
        assertThat(list).element(4).isEqualTo("v2");
        assertThat(list).element(5).isEqualTo("v1");
    }

    @Test
    void lpushx() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.lpushx(key, "v1")).isEqualTo(0);
        assertThat(operation.lpush(key, "v1")).isEqualTo(1);
        assertThat(operation.lpushx(key, "v2")).isEqualTo(2);
        assertThat(operation.lpushx(key, "v3")).isEqualTo(3);
        assertThat(operation.lpushx(key, "v1")).isEqualTo(4);
        assertThat(operation.lpushx(key, "v4")).isEqualTo(5);
        assertThat(operation.lpushx(key, "v5")).isEqualTo(6);

        List<String> list = operation.lrange(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
        assertThat(list).element(0).isEqualTo("v5");
        assertThat(list).element(1).isEqualTo("v4");
        assertThat(list).element(2).isEqualTo("v1");
        assertThat(list).element(3).isEqualTo("v3");
        assertThat(list).element(4).isEqualTo("v2");
        assertThat(list).element(5).isEqualTo("v1");
    }

    @Test
    void lpop() {
        String key = UUID.randomUUID().toString();
        operation.lpush(key, "v1", "v2", "v3", "v1", "v4", "v5");
        assertThat(operation.lpop(key)).isEqualTo("v5");
        assertThat(operation.lpop(key)).isEqualTo("v4");
        assertThat(operation.lpop(key)).isEqualTo("v1");
        assertThat(operation.lpop(key)).isEqualTo("v3");
        assertThat(operation.lpop(key)).isEqualTo("v2");
        assertThat(operation.lpop(key)).isEqualTo("v1");
        assertThat(operation.lpop(key)).isNull();
    }

    @Test
    void llen() {
        String key = UUID.randomUUID().toString();
        operation.lpush(key, "v1", "v2", "v3", "v1", "v4", "v5");
        assertThat(operation.llen(key)).isEqualTo(6);
    }

    @Test
    void rpush() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.rpush(key, "v1", "v2", "v3")).isEqualTo(3);
        assertThat(operation.rpush(key, "v1", "v4", "v5")).isEqualTo(6);

        List<String> list = operation.lrange(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
        assertThat(list).element(0).isEqualTo("v1");
        assertThat(list).element(1).isEqualTo("v2");
        assertThat(list).element(2).isEqualTo("v3");
        assertThat(list).element(3).isEqualTo("v1");
        assertThat(list).element(4).isEqualTo("v4");
        assertThat(list).element(5).isEqualTo("v5");
    }

    @Test
    void rpushx() {
        String key = UUID.randomUUID().toString();
        assertThat(operation.rpushx(key, "v1")).isEqualTo(0);
        assertThat(operation.rpush(key, "v1", "v2", "v3")).isEqualTo(3);
        assertThat(operation.rpushx(key, "v1")).isEqualTo(4);
        assertThat(operation.rpushx(key, "v4")).isEqualTo(5);
        assertThat(operation.rpushx(key, "v5")).isEqualTo(6);

        List<String> list = operation.lrange(key, 0, -1);
        assertThat(list).size().isEqualTo(6);
        assertThat(list).element(0).isEqualTo("v1");
        assertThat(list).element(1).isEqualTo("v2");
        assertThat(list).element(2).isEqualTo("v3");
        assertThat(list).element(3).isEqualTo("v1");
        assertThat(list).element(4).isEqualTo("v4");
        assertThat(list).element(5).isEqualTo("v5");
    }

    @Test
    void rpop() {
        String key = UUID.randomUUID().toString();
        operation.lpush(key, "v1", "v2", "v3", "v1", "v4", "v5");
        assertThat(operation.rpop(key)).isEqualTo("v1");
        assertThat(operation.rpop(key)).isEqualTo("v2");
        assertThat(operation.rpop(key)).isEqualTo("v3");
        assertThat(operation.rpop(key)).isEqualTo("v1");
        assertThat(operation.rpop(key)).isEqualTo("v4");
        assertThat(operation.rpop(key)).isEqualTo("v5");
        assertThat(operation.rpop(key)).isNull();
    }
}
