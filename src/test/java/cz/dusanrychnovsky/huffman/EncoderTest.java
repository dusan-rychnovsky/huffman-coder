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

    Random rnd = new Random(0);

    String alphabet = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int minLength = 100;
    int maxLength = 1000;

    int COUNT = 100;
    for (int i = 0; i < COUNT; i++) {
      String text = randText(rnd, alphabet, randLength(rnd, minLength, maxLength));
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
