package redcoder.rcredis.core;

import redcoder.rcredis.core.command.KeyCommand;
import redcoder.rcredis.core.io.RedisConnection;

import java.util.concurrent.TimeUnit;

import static redcoder.rcredis.core.RedisCommand.DEL;
import static redcoder.rcredis.core.RedisCommand.EXPIRE;

 class RedisKeyCommandImpl extends RedisCommandSupport implements KeyCommand {

    public RedisKeyCommandImpl(RedisConnection connection) {
        super(connection);
    }

    @Override
    public int expire(byte[] key, long timeout, TimeUnit unit) {
        long i = (long) executeCommand(EXPIRE, key, String.valueOf(unit.toSeconds(timeout)).getBytes());
        return Long.valueOf(i).intValue();
    }

    @Override
    public int del(byte[] key) {
        long i = (long) executeCommand(DEL, key);
        return Long.valueOf(i).intValue();
    }
}
