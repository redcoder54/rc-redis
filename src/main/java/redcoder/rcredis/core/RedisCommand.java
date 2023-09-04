package redcoder.rcredis.core;

public enum RedisCommand {
    AUTH,
    EXPIRE,
    TTL,
    PING,
    DEL,

    // string
    GET,
    MGET,
    SET,
    MSET,
    SETNX,
    INCR,
    DECR,

    // set
    SADD,
    SREM,
    SPOP,
    SMEMBERS,

    // list
    LLEN,
    LPUSH,
    LPUSHX,
    LRANGE,
    LPOP,
    RPUSH,
    RPUSHX,
    RRANGE,
    RPOP,

    // zseet
    ZADD,
    ZCARD,
    ZCOUNT,
    ZRANGE,
    ZRANGEBYSCORE,
    ZREVRANGE,
    ZREVRANGEBYSCORE,
    ZREM,
    ZSCORE,

    // hash
    HSET,
    HMSET,
    HGET,
    HMGET,
}
