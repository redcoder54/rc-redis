package redcoder.rcredis.core;

import redcoder.rcredis.core.command.ZSetCommand;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.operation.RedisSerializer;
import redcoder.rcredis.core.operation.RedisZSetOperation;
import redcoder.rcredis.core.operation.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RedisZSetOperationImpl<K, V> implements RedisZSetOperation<K, V> {

    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    private ZSetCommand zSetCommand;

    public RedisZSetOperationImpl(RedisSerializer<K> keySerializer,
                                  RedisSerializer<V> valueSerializer,
                                  RedisConnection connection) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.zSetCommand = new RedisZSetCommandImpl(connection);
    }

    @Override
    public long zadd(K key, double score, V member) {
        return zSetCommand.zadd(keySerializer.serialize(key), score, valueSerializer.serialize(member));
    }

    @Override
    public long zadd(K key, Map<K, Double> memberScores) {
        Map<byte[], Double> map = new HashMap<>();
        for (Map.Entry<K, Double> entry : memberScores.entrySet()) {
            map.put(keySerializer.serialize(entry.getKey()), entry.getValue());
        }
        return zSetCommand.zadd(keySerializer.serialize(key), map);
    }

    @Override
    public long zcard(K key) {
        return zSetCommand.zcard(keySerializer.serialize(key));
    }

    @Override
    public long zcount(K key, double min, double max) {
        return zSetCommand.zcount(keySerializer.serialize(key), min, max);
    }

    @Override
    public List<V> zrange(K key, long start, long stop) {
        List<Object> list = zSetCommand.zrange(keySerializer.serialize(key), start, stop);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<V, Double>> zrangeWithScores(K key, long start, long stop) {
        List<Object> list = zSetCommand.zrangeWithScores(keySerializer.serialize(key), start, stop);
        return convert(list);
    }

    @Override
    public List<V> zrangeByScore(K key, double min, double max) {
        List<Object> list = zSetCommand.zrangeByScore(keySerializer.serialize(key), min, max);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<V, Double>> zrangeByScoreWithScores(K key, double min, double max) {
        List<Object> list = zSetCommand.zrangeByScoreWithScores(keySerializer.serialize(key), min, max);
        return convert(list);
    }

    @Override
    public List<V> zrevRange(K key, long start, long stop) {
        List<Object> list = zSetCommand.zrevRange(keySerializer.serialize(key), start, stop);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<V, Double>> zrevRangeWithScores(K key, long start, long stop) {
        List<Object> list = zSetCommand.zrevRangeWithScores(keySerializer.serialize(key), start, stop);
        return convert(list);
    }

    @Override
    public List<V> zrevRangeByScore(K key, double min, double max) {
        List<Object> list = zSetCommand.zrevRangeByScore(keySerializer.serialize(key), min, max);
        List<V> result = new ArrayList<>();
        for (Object o : list) {
            result.add(valueSerializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<V, Double>> zrevRangeByScoreWithScores(K key, double min, double max) {
        List<Object> list = zSetCommand.zrevRangeByScoreWithScores(keySerializer.serialize(key), min, max);
        return convert(list);
    }

    @Override
    public long zrem(K key, V... members) {
        byte[][] array = new byte[members.length][];
        ;
        for (int i = 0; i < members.length; i++) {
            array[i] = valueSerializer.serialize(members[i]);
        }
        return zSetCommand.zrem(keySerializer.serialize(key), array);
    }

    @Override
    public Double zscore(K key, V members) {
        byte[] bytes = zSetCommand.zscore(keySerializer.serialize(key), valueSerializer.serialize(members));
        if (bytes == null) {
            return null;
        }
        return Double.parseDouble(new String(bytes));
    }

    private List<Tuple<V, Double>> convert(List<Object> list) {
        if (list.size() % 2 != 0) {
            throw new IllegalStateException("The number of values of zset command with scores is wrong.");
        }
        List<Tuple<V, Double>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 2) {
            V value = valueSerializer.deserialize((byte[]) list.get(i));
            String score = new String((byte[]) list.get(i + 1));
            result.add(new Tuple<>(value, Double.parseDouble(score)));
        }
        return result;
    }
}
