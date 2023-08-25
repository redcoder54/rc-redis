package redcoder.rcredis.core;

import redcoder.rcredis.core.command.StringCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisStringOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

class RedisStringOperationImpl implements RedisStringOperation {
    
    private StringCommand stringCommand;
    private StringRedisSerializer serializer;

    public RedisStringOperationImpl(RedisConnection connection) {
        this.serializer = new StringRedisSerializer();
        this.stringCommand = new RedisStringCommandImpl(connection);
    }

    @Override
    public void set(String key, String value) {
        stringCommand.set(serializer.serialize(key), serializer.serialize(value));
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        stringCommand.set(serializer.serialize(key), serializer.serialize(value), timeout, unit);
    }

    @Override
    public long incr(String key) {
        return stringCommand.incr(serializer.serialize(key));
    }

    @Override
    public long decr(String key) {
        return stringCommand.decr(serializer.serialize(key));
    }

    @Override
    public String get(String key) {
        byte[] bytes = stringCommand.get(serializer.serialize(key));
        return serializer.deserialize(bytes);
    }
}
