package redcoder.rcredis.core.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static redcoder.rcredis.core.ProtocolConstant.*;

public class RedisOutputStream extends FilterOutputStream {

    public RedisOutputStream(OutputStream out) {
        super(out);
    }

    public void writeBulkString(String str) throws IOException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        out.write(DOLLAR_BYTE);
        out.write(bytes.length + '0');
        writeCRLF();
        out.write(bytes);
        writeCRLF();
    }

    public void writeCRLF() throws IOException {
        out.write('\r');
        out.write('\n');
    }
}
