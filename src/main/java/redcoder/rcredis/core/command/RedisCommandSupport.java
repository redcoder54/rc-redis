package redcoder.rcredis.core.command;

import redcoder.rcredis.core.RedisCommand;
import redcoder.rcredis.core.RedisCommandException;
import redcoder.rcredis.core.io.RedisConnection;
import redcoder.rcredis.core.io.RedisInputStream;
import redcoder.rcredis.core.io.RedisOutputStream;

import java.io.IOException;

import static redcoder.rcredis.core.ProtocolConstant.*;

public abstract class RedisCommandSupport {

    protected RedisConnection connection;

    public RedisCommandSupport(RedisConnection connection) {
        this.connection = connection;
    }

    protected Object sendCommand(RedisCommand command, byte[]... args) {
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
            byte b = in.readByte();
            switch (b) {
                case PLUS_BYTE:
                    // simple string reply
                    return in.readLine();
                case DOLLAR_BYTE:
                    // bulk string reply
                    int len = in.readInt();
                    byte[] bytes = new byte[len];
                    in.readBytes(bytes);
                    return bytes;
                case COLON_BYTE:
                    // integer reply
                    return in.readLong();
                case ASTERISK_BYTE:
                    // byte array reply
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
