package redcoder.rcredis.core;

public class RedisCommandException extends RuntimeException {

    public RedisCommandException(String message) {
        super(message);
    }

    public RedisCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisCommandException(Throwable cause) {
        super(cause);
    }
}
