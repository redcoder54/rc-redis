package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisSetCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSetOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

class RedisSetOperationImpl implements RedisSetOperation {
    
    private RedisSetCommand setCommand;
    private StringRedisSerializer serializer;

    public RedisSetOperationImpl(RedisConnection connection) {
        this.serializer = new StringRedisSerializer();
        this.setCommand = new RedisSetCommandImpl(connection);
    }

    @Override
    public int sadd(String key, String... members) {
        byte[][] array = new byte[members.length][];
        for (int i = 0; i < members.length; i++) {
            array[i] = serializer.serialize(members[i]);
        }
        return setCommand.sadd(serializer.serialize(key), array);
    }

    @Override
    public int srem(String key, String... members) {
        byte[][] array = new byte[members.length][];
        for (int i = 0; i < members.length; i++) {
            array[i] = serializer.serialize(members[i]);
        }
        return setCommand.srem(serializer.serialize(key), array);
    }

    @Override
    public String spop(String key) {
        byte[] bytes = setCommand.spop(serializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return serializer.deserialize(bytes);
    }

    @Override
    public List<String> spop(String key, int count) {
        List<Object> list = setCommand.spop(serializer.serialize(key), count);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<String> smembers(String key) {
        List<Object> list = setCommand.smembers(serializer.serialize(key));
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }
}
