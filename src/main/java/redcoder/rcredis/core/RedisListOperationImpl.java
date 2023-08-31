package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisListCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisListOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

class RedisListOperationImpl implements RedisListOperation {
    
    private RedisListCommand listCommand;
    private StringRedisSerializer serializer = new StringRedisSerializer();

    public RedisListOperationImpl(RedisConnection connection) {
        this.listCommand = new RedisListCommandImpl(connection);
    }

    @Override
    public long lpush(String key, String... elements) {
        byte[][] array = new byte[elements.length][];
        for (int i = 0; i < elements.length; i++) {
            array[i] = serializer.serialize(elements[i]);
        }
        return listCommand.lpush(serializer.serialize(key), array);
    }

    @Override
    public long lpushx(String key, String element) {
        return listCommand.lpushx(serializer.serialize(key), serializer.serialize(element));
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        List<Object> list = listCommand.lrange(serializer.serialize(key), start, end);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public String lpop(String key) {
        byte[] bytes = listCommand.lpop(serializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return serializer.deserialize(bytes);
    }

    @Override
    public long llen(String key) {
        return listCommand.llen(serializer.serialize(key));
    }

    @Override
    public long rpush(String key, String... elements) {
        byte[][] array = new byte[elements.length][];
        for (int i = 0; i < elements.length; i++) {
            array[i] = serializer.serialize(elements[i]);
        }
        return listCommand.rpush(serializer.serialize(key), array);
    }

    @Override
    public long rpushx(String key, String element) {
        return listCommand.rpushx(serializer.serialize(key), serializer.serialize(element));
    }

    @Override
    public String rpop(String key) {
        byte[] bytes = listCommand.rpop(serializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return serializer.deserialize(bytes);
    }
}
