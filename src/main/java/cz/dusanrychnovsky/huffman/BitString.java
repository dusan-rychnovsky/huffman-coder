package cz.dusanrychnovsky.huffman;

import java.util.Arrays;
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
   * Instantiates an empty {@link BitString}.
   */
  public BitString() {
    size = 0;
    data = new byte[0];
  }

  /**
   * Instantiates a {@link BitString} representing the given list of bits. In
   * the given list each item should represent a single bit (i.e. should have
   * value of either 0 or 1).
   */
  public BitString(List<Byte> bits) {
    size = bits.size();
    data = new byte[arrayLength(size)];
    for (int i = 0; i < bits.size(); i++) {
      set(i, bits.get(i));
    }
  }

  private int arrayLength(int size) {
    int result = size / 8;
    if (size % 8 != 0) {
      result++;
    }
    return result;
  }

  private void set(int pos, Byte value) {
    if (value == 1) {
      set(pos);
    }
    else {
      clear(pos);
    }
  }

  private void set(int pos) {
    data[pos / 8] |= (1 << (pos % 8));
  }

  private void clear(int pos) {
    data[pos / 8] &= ~(1 << (pos % 8));
  }

  private BitString(int size) {
    this.size = size;
    data = new byte[arrayLength(size)];
  }

  /**
   * Returns a concatenation of the represented and the given
   * {@link BitString}s as a new {@link BitString}.
   */
  public BitString append(BitString other) {

    BitString result = new BitString(size + other.size);
    int pos = 0;

    for (byte b : this) {
      result.set(pos, b);
      pos++;
    }
    for (byte b : other) {
      result.set(pos, b);
      pos++;
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

  @Override
  public int hashCode() {
    return data.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (!(obj instanceof BitString)) {
      return false;
    }

    BitString other = (BitString) obj;
    return Arrays.equals(data, other.data);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    Iterator<Byte> it = iterator();
    while (it.hasNext()) {
      builder.append(it.next());
      if (it.hasNext()) {
        builder.append(", ");
      }
    }

    return builder.toString();
  }
}
