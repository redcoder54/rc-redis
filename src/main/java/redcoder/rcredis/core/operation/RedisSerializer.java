package redcoder.rcredis.core.operation;

public interface RedisSerializer<T> {

    byte[] serialize(T key);

    T deserialize(byte[] bytes);
}
