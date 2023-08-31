package redcoder.rcredis.core.operation;

import java.util.concurrent.TimeUnit;

public interface RedisKeyOperation {

    int expire(String key, long timeout, TimeUnit unit);

    int del(String key);
}
