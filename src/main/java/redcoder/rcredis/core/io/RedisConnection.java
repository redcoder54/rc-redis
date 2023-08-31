package redcoder.rcredis.core.io;

import java.io.Closeable;

public interface RedisConnection extends Closeable {

    RedisInputStream getInputStream();

    RedisOutputStream getOutputStream();

}
