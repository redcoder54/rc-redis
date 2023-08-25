package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisConnectionFactory;

import java.io.IOException;
import java.net.Socket;

class RedisConnectionFactoryImpl implements RedisConnectionFactory {

    private RedisConfiguration conf;

    public RedisConnectionFactoryImpl(RedisConfiguration conf) {
        this.conf = conf;
    }

    @Override
    public RedisConnection create() {
        try {
            Socket socket = new Socket(conf.getHost(), conf.getPort());
            return new RedisConnectionImpl(socket);
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }

}
