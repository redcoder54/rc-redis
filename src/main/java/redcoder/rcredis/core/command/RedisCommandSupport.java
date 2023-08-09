package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.RedisCommandException;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisInputStream;
import redcoder.rcredis.core.io.RedisOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static redcoder.rcredis.core.ProtocolConstant.*;

public abstract class RedisCommandSupport {

    protected RedisConnection connection;

    public RedisCommandSupport(RedisConnection connection) {
        this.connection = connection;
    }

    protected Object sendCommand(RedisCommand command, String... args) {
        RedisInputStream in = connection.getInputStream();
        RedisOutputStream out = connection.getOutputStream();
        try {
            out.write(ASTERISK_BYTE);
            out.write(args.length + 1 + '0');
            out.writeCRLF();
            out.writeBulkString(command.name());
            for (String arg : args) {
                out.writeBulkString(arg);
            }
            out.flush();

            byte b = in.readByte();
            switch (b) {
                case PLUS_BYTE:
                    return in.readLine();
                case DOLLAR_BYTE:
                    int len = in.readInt();
                    byte[] bytes = new byte[len];
                    in.readBytes(bytes);
                    return new String(bytes, StandardCharsets.UTF_8);
                case COLON_BYTE:
                    // integer reply
                    return in.readLong();
                case ASTERISK_BYTE:
                    throw new UnsupportedOperationException();
                case MINUS_BYTE:
                    // error reply
                    String err = in.readLine();
                    throw new RedisCommandException(String.format("Failed to execute command %s, redis server's reply: %s", command.name(), err));
                default:
                    throw new RedisCommandException("unknown reply: " + b + in.readLine());
            }

        } catch (IOException e) {
            throw new RedisCommandException(e);
        }
    }
}
