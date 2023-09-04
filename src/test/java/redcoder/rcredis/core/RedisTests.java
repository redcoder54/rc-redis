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
    }
}
