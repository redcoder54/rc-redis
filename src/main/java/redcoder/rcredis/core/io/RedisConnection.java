package redcoder.rcredis.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RedisConnection {

    RedisInputStream getInputStream();

    RedisOutputStream getOutputStream();

    void close();
}
