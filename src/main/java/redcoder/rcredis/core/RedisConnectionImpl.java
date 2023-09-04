package redcoder.rcredis.core;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class RedisConnectionImpl implements RedisConnection {

    private static final byte PLUS_BYTE = '+';
    private static final byte MINUS_BYTE = '-';
    private static final byte COLON_BYTE = ':';
    private static final byte DOLLAR_BYTE = '$';
    private static final byte ASTERISK_BYTE = '*';

    private String host;
    private int port = 6379;
    private String password;
    private Socket socket;
    private boolean connected;
    private RedisInputStream in;
    private RedisOutputStream out;

    public RedisConnectionImpl(String host) {
        this.host = host;
    }

    public RedisConnectionImpl(String host, int port) {
        this(host, port, null);
    }

    public RedisConnectionImpl(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Override
    public void sendCommand(RedisCommand command, byte[]... args) throws IOException {
        if (!connected) {
            connect();
        }
        out.write(ASTERISK_BYTE);
        out.writeIntCRLF(args.length + 1);
        // write command
        byte[] c_bytes = command.name().getBytes();
        out.write(DOLLAR_BYTE);
        out.writeIntCRLF(c_bytes.length);
        out.writeByteCRLF(c_bytes);
        // write args
        for (byte[] arg : args) {
            out.write(DOLLAR_BYTE);
            out.writeIntCRLF(arg.length);
            out.writeByteCRLF(arg);
        }
        out.flush();
    }

    private void connect() throws IOException {
        socket = new Socket(host, port);
        in = new RedisInputStream(socket.getInputStream());
        out = new RedisOutputStream(socket.getOutputStream());
        connected = true;

        auth();
    }

    private void auth() throws IOException {
        if (password != null && !password.isEmpty()) {
            // auth
            sendCommand(RedisCommand.AUTH, password.getBytes(StandardCharsets.UTF_8));
            DataType type = getType();
            if (type == DataType.SIMPLE_ERRORS) {
                throw new RedisConnectionException("Auth failed: " + new String(getSimpleErrorsReply()));
            }
            getSimpleStringsReply();
        }
    }

    @Override
    public DataType getType() throws IOException {
        byte b = in.readByte();
        switch (b) {
            case PLUS_BYTE:
                // simple string reply
                return DataType.SIMPLE_STRINGS;
            case DOLLAR_BYTE:
                // bulk string reply
                return DataType.BULK_STRINGS;
            case COLON_BYTE:
                // integer reply
                return DataType.INTEGERS;
            case ASTERISK_BYTE:
                // byte array reply
                return DataType.ARRAYS;
            case MINUS_BYTE:
                // error reply
                return DataType.SIMPLE_ERRORS;
            default:
                throw new RedisException("Unsupported data type: " + b);
        }
    }

    @Override
    public byte[] getSimpleStringsReply() throws IOException {
        return in.readLineBytes();
    }

    @Override
    public byte[] getSimpleErrorsReply() throws IOException {
        return in.readLineBytes();
    }

    @Override
    public long getIntegersReply() throws IOException {
        return in.readLong();
    }

    @Override
    public byte[] getBulkStringsReply() throws IOException {
        int len = in.readInt();
        if (len == -1) {
            // null reply
            return null;
        }
        if (len == 0) {
            // empty string
            return "".getBytes();
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        return bytes;
    }

    @Override
    public int getArraysLength() {
        return in.readInt();
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RedisException("Can't close socket", e);
        }
    }
}
