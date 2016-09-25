package cz.dusanrychnovsky.huffman;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements Closeable {

  private final OutputStream out;
  private int size = 0;
  private byte buffer = 0;

  public BitOutputStream(OutputStream out) {
    this.out = out;
  }

  public void append(BitString string) throws IOException {
    for (byte bit : string) {
      append(bit);
    }
  }

  public void append(byte bit) throws IOException {
    int pos = size % 8;
    if (bit == 1) {
      set(pos);
    }
    if (pos == 7) {
      out.write(buffer);
      buffer = 0;
    }
    size++;
  }

  private void set(int pos) {
    buffer |= (1 << pos);
  }

  @Override
  public void close() throws IOException {
    if (size % 8 != 0) {
      out.write(buffer);
    }
    out.close();
  }
}
