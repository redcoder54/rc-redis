package redcoder.rcredis.core;

import redcoder.rcredis.core.command.HashCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisHashOperation;
import redcoder.rcredis.core.operation.RedisSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RedisHashOperationImpl<K, HK, HV> implements RedisHashOperation<K, HK, HV> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<HK> hkSerializer;
    private RedisSerializer<HV> hvSerializer;
    private HashCommand hashCommand;

    public RedisHashOperationImpl(RedisSerializer<K> keySerializer,
                                  RedisSerializer<HK> hkSerializer,
                                  RedisSerializer<HV> hvSerializer,
                                  RedisConnection connection) {
        this.keySerializer = keySerializer;
        this.hkSerializer = hkSerializer;
        this.hvSerializer = hvSerializer;
        this.hashCommand = new RedisHashCommandImpl(connection);
    }

    @Override
    public long hset(K key, HK field, HV value) {
        return hashCommand.hset(keySerializer.serialize(key), hkSerializer.serialize(field), hvSerializer.serialize(value));
    }

    @Override
    public void hmset(K key, Map<HK, HV> hash) {
        Map<byte[], byte[]> map = new HashMap<>();
        for (Map.Entry<HK, HV> entry : hash.entrySet()) {
            map.put(hkSerializer.serialize(entry.getKey()), hvSerializer.serialize(entry.getValue()));
        }
        hashCommand.hmset(keySerializer.serialize(key), map);
    }

    @Override
    public HV hget(K key, HK field) {
        byte[] bytes = hashCommand.hget(keySerializer.serialize(key), hkSerializer.serialize(field));
        return hvSerializer.deserialize(bytes);
    }

    @Override
    public List<HV> hmget(K key, HK... fields) {
        List<byte[]> args = new ArrayList<>();
        for (HK field : fields) {
            args.add(hkSerializer.serialize(field));
        }
        List<byte[]> list = hashCommand.hmget(keySerializer.serialize(key), args.toArray(new byte[args.size()][]));
        List<HV> result = new ArrayList<>();
        for (byte[] by : list) {
            result.add(hvSerializer.deserialize(by));
        }
        return result;
    }
}
