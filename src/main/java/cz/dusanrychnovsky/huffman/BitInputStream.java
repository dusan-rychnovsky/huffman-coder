package cz.dusanrychnovsky.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BitInputStream {

  private final InputStream in;
  private final long size;
  private long read = 0;
  private byte buffer;

  public BitInputStream(InputStream in) throws IOException {
    this.in = in;
    size = loadSize(in);
  }

  private long loadSize(InputStream in) throws IOException {
    byte[] bytes = new byte[8];
    in.read(bytes);
    return toLong(bytes);
  }

  private long toLong(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getLong();
  }

  public byte read() throws IOException {
    if (read == size) {
      return -1;
    }

    if (read % 8 == 0) {
      buffer = (byte) in.read();
    }

    int result = (buffer >> (read % 8)) & 1;
    read++;

    return (byte) result;
  }
}
