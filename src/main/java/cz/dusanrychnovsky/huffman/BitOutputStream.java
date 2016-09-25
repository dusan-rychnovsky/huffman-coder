package cz.dusanrychnovsky.huffman;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class BitOutputStream implements Closeable {

  private final OutputStream out;

  public BitOutputStream(OutputStream out) {
    this.out = out;
  }

  private final ByteArrayOutputStream data = new ByteArrayOutputStream();
  private int size = 0;

  private byte buffer = 0;

  public void append(BitString string) {
    for (byte bit : string) {
      append(bit);
    }
  }

  public void append(byte bit) {
    int pos = size % 8;
    if (bit == 1) {
      set(pos);
    }
    if (pos == 7) {
      data.write(buffer);
      buffer = 0;
    }
    size++;
  }

  private void set(int pos) {
    buffer |= (1 << pos);
  }

  // ==========================================================================
  // SAVE TO OUTPUT-STREAM
  // ==========================================================================

  public void saveTo(OutputStream out) throws IOException {
    out.write(toBytes(size));
    out.write(data.toByteArray());
    if (size % 8 != 0) {
      out.write(buffer);
    }
  }

  private static byte[] toBytes(int value) {
    return ByteBuffer.allocate(4).putInt(value).array();
  }

  @Override
  public void close() throws IOException {
    // TODO
  }

  public long size() {
    return 0;
  }
}
