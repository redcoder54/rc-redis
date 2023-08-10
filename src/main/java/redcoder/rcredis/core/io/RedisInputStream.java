package redcoder.rcredis.core.io;

import redcoder.rcredis.core.RedisCommandException;
import redcoder.rcredis.core.RedisConnectionException;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RedisInputStream extends FilterInputStream {

    private byte[] buf;
    private int pos;
    private int limit;

    public RedisInputStream(InputStream in) {
        super(in);
        buf = new byte[2048];
    }

    public byte readByte() throws IOException {
        fillBufIfNecessary();
        return buf[pos++];
    }

    public byte[] readLineBytes() throws IOException {
        int cursor = pos;
        while (true) {
            fillBufIfNecessary();
            if (buf[cursor++] == '\r') {
                fillBufIfNecessary();
                if (buf[cursor++] != '\n') {
                    throw new RedisCommandException("Unexpected character!");
                }
                break;
            }
        }
        int len = cursor - pos - 2;
        byte[] dest = new byte[len];
        System.arraycopy(buf, pos, dest, 0, len);
        pos = cursor;
        return dest;
    }

    public String readLine() throws IOException {
        return new String(readLineBytes());
    }

    public int readInt() {
        return (int) readLong();
    }

    public long readLong() {
        boolean isNeg = false;
        long value = 0;
        while (true) {
            fillBufIfNecessary();
            byte b = buf[pos++];
            if (b == '-') {
                isNeg = true;
                continue;
            }
            if (b == '\r') {
                byte c = buf[pos++];
                if (c != '\n') {
                    throw new RedisCommandException("Unexpected character!");
                }
                break;
            }
            value = value * 10 + (b - '0');
        }
        return isNeg ? (-value) : value;
    }

    public void readBytes(byte[] bytes) {
        int i = 0;
        while (i < bytes.length) {
            fillBufIfNecessary();
            bytes[i++] = buf[pos++];
        }
        fillBufIfNecessary();
        byte b1 = buf[pos++];
        fillBufIfNecessary();
        byte b2 = buf[pos++];
        if (b1 == '\r' && b2 == '\n') {
            return;
        }
        throw new RedisConnectionException("Unexpected character!");
    }

    private void fillBufIfNecessary() {
        if (pos >= limit) {
            try {
                limit = in.read(buf);
                pos = 0;
                if (limit == -1) {
                    throw new RedisConnectionException("Unexpected end of stream.");
                }
            } catch (IOException e) {
                throw new RedisConnectionException(e);
            }
        }
    }
}
