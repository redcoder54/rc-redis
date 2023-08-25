package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisInputStream;
import redcoder.rcredis.core.io.RedisOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


abstract class RedisCommandSupport {

    private static final byte PLUS_BYTE = '+';
    private static final byte MINUS_BYTE = '-';
    private static final byte COLON_BYTE = ':';
    private static final byte DOLLAR_BYTE = '$';
    private static final byte ASTERISK_BYTE = '*';

    protected RedisConnection connection;

    public RedisCommandSupport(RedisConnection connection) {
        this.connection = connection;
    }

    protected byte[] convertToBytes(long i) {
        return String.valueOf(i).getBytes();
    }

    protected byte[] convertToBytes(double i) {
        return String.valueOf(i).getBytes();
    }

    protected byte[][] mergeByteArray(byte[] bytes, byte[][] src) {
        byte[][] dest = new byte[src.length + 1][];
        dest[0] = bytes;
        System.arraycopy(src, 0, dest, 1, src.length);
        return dest;
    }

    protected Object executeCommand(RedisCommand command, byte[]... args) {
        RedisInputStream in = connection.getInputStream();
        RedisOutputStream out = connection.getOutputStream();
        try {
            out.write(ASTERISK_BYTE);
            out.writeIntCRLF(args.length + 1);
            // write command name
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

            // process reply
            return processReply(in);
        } catch (IOException e) {
            throw new RedisCommandException(e);
        }
    }

    private Object processReply(RedisInputStream in) throws IOException {
        byte b = in.readByte();
        switch (b) {
            case PLUS_BYTE:
                // simple string reply
                return in.readLine();
            case DOLLAR_BYTE:
                // bulk string reply
                return processBulkStringReply(in);
            case COLON_BYTE:
                // integer reply
                return in.readLong();
            case ASTERISK_BYTE:
                // byte array reply
                return processArrayReply(in);
            case MINUS_BYTE:
                // error reply
                String err = in.readLine();
                throw new RedisCommandException(String.format("Failed to execute command, the reply of redis server is: %s", err));
            default:
                throw new RedisCommandException("unknown reply: " + b + in.readLine());
        }
    }

    private Object processBulkStringReply(RedisInputStream in) {
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

    private Object processArrayReply(RedisInputStream in) throws IOException {
        int alen = in.readInt();
        if (alen == 0) {
            // empty array
            return Collections.emptyList();
        }
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < alen; i++) {
            result.add(processReply(in));
        }
        return result;
    }
}
