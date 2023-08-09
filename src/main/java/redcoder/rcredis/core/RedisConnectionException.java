package redcoder.rcredis.core;

public class RedisConnectionException extends RuntimeException {

    public RedisConnectionException(Throwable cause) {
        super(cause);
    }

    public RedisConnectionException(String message) {
        super(message);
    }
}
