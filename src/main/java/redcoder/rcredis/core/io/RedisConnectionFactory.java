package redcoder.rcredis.core.io;

public interface RedisConnectionFactory {

    RedisConnection create(String host, int port);

}
