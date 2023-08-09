package redcoder.rcredis.core.command;

import java.util.concurrent.TimeUnit;

public interface RedisCommand {

    void expire(String key, long timeout, TimeUnit unit);

}
