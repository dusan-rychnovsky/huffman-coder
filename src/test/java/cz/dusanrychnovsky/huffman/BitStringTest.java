package cz.dusanrychnovsky.huffman;

import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BitStringTest {

  @Test
  public void storesASequenceOfBits() {
    List<Byte> data = asList((byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0);
    BitString bits = new BitString(data);
    int i = 0;
    for (Byte bit : bits) {
      assertEquals(data.get(i), bit);
      i++;
    }
  }

  @Test
  public void canStoreAnEmptySequence() {
    BitString bits = new BitString();
    assertFalse(bits.iterator().hasNext());
  }

  @Test
  public void canConcatenateTwoBitStrings() {

    List<Byte> firstData = asList((byte) 0, (byte) 1, (byte) 1);
    BitString firstBits = new BitString(firstData);

    List<Byte> secondData = asList((byte) 1, (byte) 0);
    BitString secondBits = new BitString(secondData);

    List<Byte> data = new LinkedList<>();
    data.addAll(firstData);
    data.addAll(secondData);

    BitString bits = firstBits.append(secondBits);

    assertIteratorEquals(data.iterator(), bits.iterator());
  }

  private <T> void assertIteratorEquals(Iterator<T> firstIt, Iterator<T> secondIt) {
    while (firstIt.hasNext()) {
      assertEquals(firstIt.next(), secondIt.next());
    }
    assertFalse(secondIt.hasNext());
  }

  @Test
  public void canAppendOnEmptyBitString() {

    BitString emptyBits = new BitString();

    List<Byte> data = asList((byte) 0, (byte) 1, (byte) 1);
    BitString secondBits = new BitString(data);

    BitString bits = emptyBits.append(secondBits);
    assertEquals(secondBits, bits);
  }

  @Test
  public void canAppendEmptyBitString() {

    List<Byte> data = asList((byte) 0, (byte) 1, (byte) 1);
    BitString firstBits = new BitString(data);

    BitString emptyBits = new BitString();

    BitString bits = firstBits.append(emptyBits);
    assertEquals(firstBits, bits);
  }

  // ==========================================================================
  // RANDOMIZED TEST
  // ==========================================================================

  @Test
  public void randomTest() {

    Random rnd = new Random(0L);
    int REPETITIONS = 10, SIZE = 100_000;

    for (int rep = 0; rep < REPETITIONS; rep++) {

      List<Byte> data = new ArrayList<>(SIZE);
      for (int i = 0; i < SIZE; i++) {
        data.add((byte) rnd.nextInt(2));
      }

      BitString bits = new BitString(data);

      int i = 0;
      for (byte bit : bits) {
        assertEquals((byte) data.get(i), bit);
        i++;
      }
    }
  }
}
