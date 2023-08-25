package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisInputStream;
import redcoder.rcredis.core.io.RedisOutputStream;

import java.io.IOException;
import java.net.Socket;

class RedisConnectionImpl implements RedisConnection {

    private final Socket socket;

    public RedisConnectionImpl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public RedisInputStream getInputStream() {
        try {
            return new RedisInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }

    @Override
    public RedisOutputStream getOutputStream() {
        try {
            return new RedisOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RedisConnectionException(e);
        }
    }
}
