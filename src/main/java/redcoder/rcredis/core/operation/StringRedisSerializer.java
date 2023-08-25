package redcoder.rcredis.core.operation;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringRedisSerializer implements RedisSerializer<String> {

    private Charset charset;

    public StringRedisSerializer() {
        this.charset = StandardCharsets.UTF_8;
    }

    public StringRedisSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] serialize(String key) {
        return key.getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes) {
        return new String(bytes, charset);
    }
}
