package redcoder.rcredis.core.io;

import redcoder.rcredis.core.RedisConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RedisConnectionImpl implements RedisConnection {

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
