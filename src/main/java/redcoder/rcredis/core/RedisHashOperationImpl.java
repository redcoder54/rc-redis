package redcoder.rcredis.core;

import redcoder.rcredis.core.command.HashCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisHashOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RedisHashOperationImpl implements RedisHashOperation {

    private StringRedisSerializer serializer;
    private HashCommand hashCommand;

    public RedisHashOperationImpl(RedisConnection connection) {
        this.serializer = new StringRedisSerializer();
        this.hashCommand = new RedisHashCommandImpl(connection);
    }

    @Override
    public long hset(String key, String field, String value) {
        return hashCommand.hset(serializer.serialize(key), serializer.serialize(field), serializer.serialize(value));
    }

    @Override
    public void hmset(String key, Map<String, String> hash) {
        Map<byte[], byte[]> map = new HashMap<>();
        for (Map.Entry<String, String> entry : hash.entrySet()) {
            map.put(serializer.serialize(entry.getKey()), serializer.serialize(entry.getValue()));
        }
        hashCommand.hmset(serializer.serialize(key), map);
    }

    @Override
    public String hget(String key, String field) {
        byte[] bytes = hashCommand.hget(serializer.serialize(key), serializer.serialize(field));
        return serializer.deserialize(bytes);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        List<byte[]> args = new ArrayList<>();
        for (String field : fields) {
            args.add(serializer.serialize(field));
        }
        List<byte[]> list = hashCommand.hmget(serializer.serialize(key), args.toArray(new byte[args.size()][]));
        List<String> result = new ArrayList<>();
        for (byte[] by : list) {
            result.add(serializer.deserialize(by));
        }
        return result;
    }
}
