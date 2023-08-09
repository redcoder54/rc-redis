package redcoder.rcredis.core.io;

import redcoder.rcredis.core.RedisConnectionException;

import java.io.IOException;
import java.net.Socket;

public class RedisConnectionFactoryImpl implements RedisConnectionFactory {
    @Override
    public RedisConnection create(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            return new RedisConnectionImpl(socket);
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }

}
