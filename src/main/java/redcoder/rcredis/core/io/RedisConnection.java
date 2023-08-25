package redcoder.rcredis.core.io;

public interface RedisConnection {

    RedisInputStream getInputStream();

    RedisOutputStream getOutputStream();

    void close();
}
