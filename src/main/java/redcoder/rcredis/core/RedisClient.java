package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnectionFactory;
import redcoder.rcredis.core.operation.*;

public class RedisClient<K, V> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    @SuppressWarnings("rawtypes")
    private RedisSerializer hashKeySerializer;
    @SuppressWarnings("rawtypes")
    private RedisSerializer hashValueSerializer;
    private RedisConnectionFactory connectionFactory;

    public RedisClient(RedisConfiguration conf) {
        init(conf);
    }

    public RedisClient(RedisConfiguration conf,
                       RedisSerializer<K> keySerializer,
                       RedisSerializer<V> valueSerializer,
                       RedisSerializer hashKeySerializer,
                       RedisSerializer hashValueSerializer) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.hashKeySerializer = hashKeySerializer;
        this.hashValueSerializer = hashValueSerializer;
        init(conf);
    }

    private void init(RedisConfiguration conf) {
        RedisSerializer serializer = new StringRedisSerializer();
        this.keySerializer = serializer;
        this.valueSerializer = serializer;
        this.hashKeySerializer = serializer;
        this.hashValueSerializer = serializer;
        this.connectionFactory = new RedisConnectionFactoryImpl(conf);
    }

    public RedisStringOperation<K, V> stringCommand() {
        return new RedisStringOperationImpl<>(keySerializer, valueSerializer, connectionFactory.create());
    }

    public RedisListOperation<K,V> listCommand() {
        return new RedisListOperationImpl<>(keySerializer, valueSerializer, connectionFactory.create());
    }

    public RedisSetOperation<K,V> setCommand() {
        return new RedisSetOperationImpl<>(keySerializer, valueSerializer, connectionFactory.create());
    }

    public RedisZSetOperation<K,V> zSetCommand() {
        return new RedisZSetOperationImpl<>(keySerializer, valueSerializer, connectionFactory.create());
    }

    public <HK,HV> RedisHashOperation<K,HK,HV> hashCommand() {
        return new RedisHashOperationImpl<>(keySerializer, hashKeySerializer, hashValueSerializer, connectionFactory.create());
    }
}
