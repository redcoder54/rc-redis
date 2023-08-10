package redcoder.rcredis.core;

public enum RedisCommand {
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
}
