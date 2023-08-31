package redcoder.rcredis.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisHashOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RedisHashOperationTests extends AbstractRedisTestsSupport {

    private static RedisHashOperation operation;

    @BeforeAll
    static void beforeAll() {
        RedisConnection connection = getConnection();
        operation = new RedisHashOperationImpl(connection);
    }

    @Test
    void hset_hget() {
        String key = UUID.randomUUID().toString();
        operation.hset(key, "k1", "v1");
        Assertions.assertThat(operation.hget(key, "k1")).isEqualTo("v1");
    }

    @Test
    void hmset_hmget() {
        String key = UUID.randomUUID().toString();

        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        operation.hmset(key, map);

        Assertions.assertThat(operation.hmget(key, "k1", "k2", "k3")).containsOnly("v1", "v2", "v3");
    }
}
