package cz.dusanrychnovsky.huffman;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a sequence of bits in a memory efficient way. The bits are stored
 * in a byte array, 8 bits per byte.
 */
public class BitString implements Iterable<Byte> {

  private final byte[] data;
  private final int size;

  /**
   * Instantiates a {@link BitString} representing the given list of bits. In
   * the given list each item should represent a single bit (i.e. should have
   * value of either 0 or 1).
   */
  public BitString(List<Byte> bits) {
    size = bits.size();
    data = new byte[arrayLength(bits)];
    int i = 0;
    byte value = 0, pos = 0;
    for (Byte bit : bits) {
      if (bit == 1) {
        value |= (1 << pos);
      }
      pos++;
      if (pos == 8) {
        data[i] = value;
        i++;
        value = 0;
        pos = 0;
      }
    }
    if (pos != 0) {
      data[i] = value;
    }
  }

  private int arrayLength(List<Byte> bits) {
    int result = bits.size() / 8;
    if (bits.size() % 8 != 0) {
      result++;
    }
    return result;
  }

  /**
   * Returns an iterator over stored bits. The iterator will return one bit
   * at a time, represented as a byte with value of either 0 or 1.
   */
  @Override
  public Iterator<Byte> iterator() {
    return new Iterator<Byte>() {

      int i = 0;

      @Override
      public boolean hasNext() {
        return i < size;
      }

      @Override
      public Byte next() {
        int result = (data[i / 8] >> (i % 8)) & 1;
        i++;
        return (byte) result;
      }
    };
  }
}
