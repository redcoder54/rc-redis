package redcoder.rcredis.core;

import redcoder.rcredis.core.command.RedisZSetCommand;
import redcoder.rcredis.core.operation.RedisZSetOperation;
import redcoder.rcredis.core.operation.StringRedisSerializer;
import redcoder.rcredis.core.operation.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RedisZSetOperationImpl implements RedisZSetOperation {
    
    private RedisZSetCommand zSetCommand;
    private StringRedisSerializer serializer;

    public RedisZSetOperationImpl(RedisConnection connection) {
        this.serializer = new StringRedisSerializer();
        this.zSetCommand = new RedisZSetCommandImpl(connection);
    }

    @Override
    public long zadd(String key, double score, String member) {
        return zSetCommand.zadd(serializer.serialize(key), score, serializer.serialize(member));
    }

    @Override
    public long zadd(String key, Map<String, Double> memberScores) {
        Map<byte[], Double> map = new HashMap<>();
        for (Map.Entry<String, Double> entry : memberScores.entrySet()) {
            map.put(serializer.serialize(entry.getKey()), entry.getValue());
        }
        return zSetCommand.zadd(serializer.serialize(key), map);
    }

    @Override
    public long zcard(String key) {
        return zSetCommand.zcard(serializer.serialize(key));
    }

    @Override
    public long zcount(String key, double min, double max) {
        return zSetCommand.zcount(serializer.serialize(key), min, max);
    }

    @Override
    public List<String> zrange(String key, long start, long stop) {
        List<Object> list = zSetCommand.zrange(serializer.serialize(key), start, stop);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<String, Double>> zrangeWithScores(String key, long start, long stop) {
        List<Object> list = zSetCommand.zrangeWithScores(serializer.serialize(key), start, stop);
        return convert(list);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        List<Object> list = zSetCommand.zrangeByScore(serializer.serialize(key), min, max);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<String, Double>> zrangeByScoreWithScores(String key, double min, double max) {
        List<Object> list = zSetCommand.zrangeByScoreWithScores(serializer.serialize(key), min, max);
        return convert(list);
    }

    @Override
    public List<String> zrevRange(String key, long start, long stop) {
        List<Object> list = zSetCommand.zrevRange(serializer.serialize(key), start, stop);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeWithScores(String key, long start, long stop) {
        List<Object> list = zSetCommand.zrevRangeWithScores(serializer.serialize(key), start, stop);
        return convert(list);
    }

    @Override
    public List<String> zrevRangeByScore(String key, double min, double max) {
        List<Object> list = zSetCommand.zrevRangeByScore(serializer.serialize(key), min, max);
        List<String> result = new ArrayList<>();
        for (Object o : list) {
            result.add(serializer.deserialize((byte[]) o));
        }
        return result;
    }

    @Override
    public List<Tuple<String, Double>> zrevRangeByScoreWithScores(String key, double min, double max) {
        List<Object> list = zSetCommand.zrevRangeByScoreWithScores(serializer.serialize(key), min, max);
        return convert(list);
    }

    @Override
    public long zrem(String key, String... members) {
        byte[][] array = new byte[members.length][];
        ;
        for (int i = 0; i < members.length; i++) {
            array[i] = serializer.serialize(members[i]);
        }
        return zSetCommand.zrem(serializer.serialize(key), array);
    }

    @Override
    public Double zscore(String key, String member) {
        byte[] bytes = zSetCommand.zscore(serializer.serialize(key), serializer.serialize(member));
        if (bytes == null) {
            return null;
        }
        return Double.parseDouble(new String(bytes));
    }

    private List<Tuple<String, Double>> convert(List<Object> list) {
        if (list.size() % 2 != 0) {
            throw new IllegalStateException("The number of values of zset command with scores is wrong.");
        }
        List<Tuple<String, Double>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 2) {
            String value = serializer.deserialize((byte[]) list.get(i));
            String score = new String((byte[]) list.get(i + 1));
            result.add(new Tuple<>(value, Double.parseDouble(score)));
        }
        return result;
    }
}
