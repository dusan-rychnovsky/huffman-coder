package cz.dusanrychnovsky.huffman;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class EncoderTest {

  private final Encoder encoder = new Encoder();
  private final Decoder decoder = new Decoder();

  @Test
  public void encodesAPlainTextToACipher() {
    String text = "Hello world!";
    assertEquals(text, decoder.decode(encoder.encode(text)));
  }

  @Test
  public void encodesASingleCharacterText() {
    String text = "aaaaa";
    assertEquals(text, decoder.decode(encoder.encode(text)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void failsEncodingEmptyText() {
    encoder.encode("");
  }

  // ==========================================================================
  // RANDOMIZED TESTS
  // ==========================================================================

  @Test
  public void randomizedTest() {

    Random rnd = new Random(0L);

    int REPETITIONS = 10;
    String ALPHABET = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int MIN_LENGTH = 100;
    int MAX_LENGTH = 1000;

    for (int rep = 0; rep < REPETITIONS; rep++) {
      String text = randText(rnd, ALPHABET, randLength(rnd, MIN_LENGTH, MAX_LENGTH));
      assertEquals(text, decoder.decode(encoder.encode(text)));
    }
  }

  private String randText(Random rnd, String alphabet, int length) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      builder.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
    }
    return builder.toString();
  }

  private int randLength(Random rnd, int min, int max) {
    return min + rnd.nextInt(max - min + 1);
  }
}
