# A simple redis client

This is a simple redis client that implements some of the commands of redis.

This redis client also supports connection pool, if you need to use pool, 
please add apache-commons-pool2 dependency in your project pom.xml:

```
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.11.1</version>
</dependency>
```

## How to use

1. The usage of Redis:

```java
// without password
Redis redis = RedisBuilder.builder()
        .host("localhost")
        .port(6379)
        .build();
try {
    redis.set("name", "tom");
} finally {
    redis.close();
}

// with password
Redis redis = RedisBuilder.builder()
        .host("localhost")
        .port(6379)
        .password("123456")
        .build();
try {
    redis.set("name", "tom");
} finally {
    redis.close();
}
```

2. The usage of PooledRedis
```java
RedisPool pool = RedisPoolBuilder.builder()
        .host("localhost")
        .port(6379)
        .password("123456")
        .maxTotal(8)
        .maxIdle(4)
        .minIdle(1)
        .testOnBorrow(true)
        .build();
PooledRedis pooledRedis = new PooledRedis(pool);
try {
    pooledRedis.set("name", "jerry");
} finally {
    pooledRedis.close();
}
```