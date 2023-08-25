package redcoder.rcredis.core;

import redcoder.rcredis.core.command.StringCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSerializer;
import redcoder.rcredis.core.operation.RedisStringOperation;

import java.util.concurrent.TimeUnit;

class RedisStringOperationImpl<K, V> implements RedisStringOperation<K, V> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    private StringCommand stringCommand;

    public RedisStringOperationImpl(RedisSerializer<K> keySerializer,
                                    RedisSerializer<V> valueSerializer,
                                    RedisConnection connection) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.stringCommand = new RedisStringCommandImpl(connection);
    }

    @Override
    public void set(K key, V value) {
        stringCommand.set(keySerializer.serialize(key), valueSerializer.serialize(value));
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {
        stringCommand.set(keySerializer.serialize(key), valueSerializer.serialize(value), timeout, unit);
    }

    @Override
    public long incr(K key) {
        return stringCommand.incr(keySerializer.serialize(key));
    }

    @Override
    public long decr(K key) {
        return stringCommand.decr(keySerializer.serialize(key));
    }

    @Override
    public V get(K key) {
        byte[] bytes = stringCommand.get(keySerializer.serialize(key));
        return valueSerializer.deserialize(bytes);
    }
}
