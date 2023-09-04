package redcoder.rcredis.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedisTests {

    @Test
    void set_get() {
        try (Redis redis = RedisBuilder.builder().password("9876543210").build()) {
            redis.set("name", "tom");
            Assertions.assertThat(redis.get("name")).isEqualTo("tom");
        }

        RedisPool pool = RedisPoolBuilder.builder()
                .host("localhost")
                .port(6379)
                .password("123456")
                .maxTotal(8)
                .maxIdle(4)
                .minIdle(1)
                .testOnBorrow(true)
                .build();
        PooledRedis pooledRedis = new PooledRedis(pool);
        try {
            pooledRedis.set("name", "jerry");
        } finally {
            pooledRedis.close();
        }
    }
}
