package redcoder.rcredis.core;

import redcoder.rcredis.core.command.ListCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisListOperation;
import redcoder.rcredis.core.operation.RedisSerializer;

import java.util.ArrayList;
import java.util.List;

class RedisListOperationImpl<K, V> implements RedisListOperation<K, V> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    private ListCommand listCommand;

    public RedisListOperationImpl(RedisSerializer<K> keySerializer,
                                  RedisSerializer<V> valueSerializer,
                                  RedisConnection connection) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.listCommand = new RedisListCommandImpl(connection);
    }

    @Override
    public long lpush(K key, V... elements) {
        byte[][] array = new byte[elements.length][];
        for (int i = 0; i < elements.length; i++) {
            array[i] = valueSerializer.serialize(elements[i]);
        }
        return listCommand.lpush(keySerializer.serialize(key), array);
    }

    @Override
    public long lpushx(K key, V element) {
        return listCommand.lpushx(keySerializer.serialize(key), valueSerializer.serialize(element));
    }

    @Override
    public List<V> lrange(K key, long start, long end) {
        List<Object> list = listCommand.lrange(keySerializer.serialize(key), start, end);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public V lpop(K key) {
        byte[] bytes = listCommand.lpop(keySerializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return valueSerializer.deserialize(bytes);
    }

    @Override
    public long llen(K key) {
        return listCommand.llen(keySerializer.serialize(key));
    }

    @Override
    public long rpush(K key, V... elements) {
        byte[][] array = new byte[elements.length][];
        for (int i = 0; i < elements.length; i++) {
            array[i] = valueSerializer.serialize(elements[i]);
        }
        return listCommand.rpush(keySerializer.serialize(key), array);
    }

    @Override
    public long rpushx(K key, V element) {
        return listCommand.rpushx(keySerializer.serialize(key), valueSerializer.serialize(element));
    }

    @Override
    public V rpop(K key) {
        byte[] bytes = listCommand.rpop(keySerializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return valueSerializer.deserialize(bytes);
    }
}
