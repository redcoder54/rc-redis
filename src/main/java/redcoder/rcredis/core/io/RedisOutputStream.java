package redcoder.rcredis.core.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RedisOutputStream extends FilterOutputStream {

    public RedisOutputStream(OutputStream out) {
        super(out);
    }

    public void writeCRLF() throws IOException {
        out.write('\r');
        out.write('\n');
    }

    public void writeIntCRLF(int i) throws IOException {
        out.write(i + '0');
        writeCRLF();
    }

    public void writeByteCRLF(byte[] bytes) throws IOException {
        out.write(bytes);
        writeCRLF();
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }
}
