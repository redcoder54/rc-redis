package redcoder.rcredis.core;

import redcoder.rcredis.core.io.RedisConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


abstract class RedisCommandSupport {

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
        try {
            connection.sendCommand(command, args);
            return processReply();
        } catch (IOException e) {
            throw new RedisException("execute command error", e);
        }
    }

    private Object processReply() throws IOException {
        RedisConnection.DataType type = connection.getType();
        switch (type) {
            case SIMPLE_ERRORS:
                return connection.getSimpleErrorsReply();
            case SIMPLE_STRINGS:
                return connection.getSimpleStringsReply();
            case INTEGERS:
                return connection.getIntegersReply();
            case BULK_STRINGS:
                return connection.getBulkStringsReply();
            case ARRAYS:
                return processArraysReply();
            default:
                throw new RedisException("Unsupported DataType: " + type);
        }
    }


    private Object processArraysReply() throws IOException {
        int alen = connection.getArraysLength();
        if (alen == 0) {
            // empty array
            return Collections.emptyList();
        }
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < alen; i++) {
            result.add(processReply());
        }
        return result;
    }
}
