package redcoder.rcredis.core.command;

import java.util.concurrent.TimeUnit;

public interface RedisKeyCommand {

    int expire(byte[] key, long timeout, TimeUnit unit);

    int del(byte[] key);

}
