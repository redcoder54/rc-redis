package redcoder.rcredis.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedisPoolTests {

    @Test
    void getOne() {
        RedisPool pool = RedisPoolBuilder.builder().build();
        Redis redis = pool.getResource();
        try {
            redis.set("name", "tom");
            Assertions.assertThat(redis.get("name")).isEqualTo("tom");
        }finally {
            pool.returnResource(redis);
        }
    }

    @Test
    void getFailure() {
        RedisPool pool = RedisPoolBuilder.builder()
                .maxTotal(1)
                .build();
        pool.getResource();
        Assertions.assertThatThrownBy(pool::getResource).isInstanceOf(RedisException.class);
    }
}
