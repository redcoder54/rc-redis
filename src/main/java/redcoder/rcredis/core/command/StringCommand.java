package redcoder.rcredis.core.command;

import java.util.concurrent.TimeUnit;

public interface StringCommand {

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit unit);

    long incr(String key);

    long decr(String key);

    String get(String key);
}
