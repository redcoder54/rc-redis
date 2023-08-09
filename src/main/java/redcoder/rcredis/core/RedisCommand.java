package redcoder.rcredis.core;

public enum RedisCommand {
    EXPIRE,
    TTL,
    PING,

    // string
    GET,
    MGET,
    SET,
    MSET,
    SETNX,
    INCR,
    DECR,
}
