package cz.dusanrychnovsky.huffman;

import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    BitString bits = new BitString(emptyList());
    assertFalse(bits.iterator().hasNext());
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
