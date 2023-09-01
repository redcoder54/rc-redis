package redcoder.rcredis.core.io;

import redcoder.rcredis.core.RedisCommand;

import java.io.Closeable;
import java.io.IOException;

public interface RedisConnection extends Closeable {

    /**
     * Send command with args to redis server.
     *
     * @param command redis command
     * @param args    redis command's args
     */
    void sendCommand(RedisCommand command, byte[]... args) throws IOException;

    /**
     * Get the type of redis server's reply, or the type of next element of the array.
     *
     * @return The type of redis server's reply, or the type of next element of the array.
     */
    DataType getType() throws IOException;

    /**
     * Get simple string reply that redis server returns
     *
     * @return simple string reply
     */
    byte[] getSimpleStringsReply() throws IOException;

    /**
     * Get simple error reply that redis server returns
     *
     * @return simple error reply
     */
    byte[] getSimpleErrorsReply() throws IOException;

    /**
     * Get integers reply that redis server returns
     *
     * @return integers reply
     */
    long getIntegersReply() throws IOException;

    /**
     * Get bulk strings reply that redis server returns.
     * If redis server returns null bulk strings, NULL will be returned.
     *
     * @return A byte arrays representing the reply or NULL
     */
    byte[] getBulkStringsReply() throws IOException;

    /**
     * Get the length of the arrays of redis reply
     *
     * @return the length of the arrays of redis reply
     */
    int getArraysLength();

    enum DataType {
        SIMPLE_STRINGS, SIMPLE_ERRORS, INTEGERS, BULK_STRINGS, ARRAYS
    }
}
