package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisKeyCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisKeyOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

class RedisKeyOperationImpl implements RedisKeyOperation {

    private StringRedisSerializer serializer;
    private RedisKeyCommand command;

    public RedisKeyOperationImpl(RedisConnection connection) {
        this.command = new RedisKeyCommandImpl(connection);
        this.serializer = new StringRedisSerializer();
    }

    @Override
    public int expire(String key, long timeout, TimeUnit unit) {
        return command.expire(serializer.serialize(key), timeout, unit);
    }

    @Override
    public int del(String key) {
        return command.del(serializer.serialize(key));
    }
}
