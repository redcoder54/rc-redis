package redcoder.rcredis.core;

import redcoder.rcredis.core.command.SetCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSerializer;
import redcoder.rcredis.core.operation.RedisSetOperation;

import java.util.ArrayList;
import java.util.List;

class RedisSetOperationImpl<K, V> implements RedisSetOperation<K, V> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    private SetCommand setCommand;

    public RedisSetOperationImpl(RedisSerializer<K> keySerializer,
                                 RedisSerializer<V> valueSerializer,
                                 RedisConnection connection) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.setCommand = new RedisSetCommandImpl(connection);
    }

    @Override
    public int sadd(K key, V... members) {
        byte[][] array = new byte[members.length][];
        for (int i = 0; i < members.length; i++) {
            array[i] = valueSerializer.serialize(members[i]);
        }
        return setCommand.sadd(keySerializer.serialize(key), array);
    }

    @Override
    public int srem(K key, V... members) {
        byte[][] array = new byte[members.length][];
        for (int i = 0; i < members.length; i++) {
            array[i] = valueSerializer.serialize(members[i]);
        }
        return setCommand.srem(keySerializer.serialize(key), array);
    }

    @Override
    public V spop(K key) {
        byte[] bytes = setCommand.spop(keySerializer.serialize(key));
        if (bytes == null) {
            return null;
        }
        return valueSerializer.deserialize(bytes);
    }

    @Override
    public List<V> spop(K key, int count) {
        List<Object> list = setCommand.spop(keySerializer.serialize(key), count);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<V> smembers(K key) {
        List<Object> list = setCommand.smembers(keySerializer.serialize(key));
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }
}
