package cz.dusanrychnovsky.huffman;

import org.junit.Test;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class EncoderTest {

  private final Encoder encoder = new Encoder();
  private final Decoder decoder = new Decoder();

  @Test
  public void encodesAPlainTextToACipher() throws IOException {
    String text = "Hello world!";
    assertEquals(text, decode(decoder, encode(encoder, text)));
  }

  @Test
  public void encodesASingleCharacterText() throws IOException {
    String text = "aaaaa";
    assertEquals(text, decode(decoder, encode(encoder, text)));
  }

  // ==========================================================================
  // RANDOMIZED TESTS
  // ==========================================================================

  @Test
  public void randomizedTest() throws IOException {

    Random rnd = new Random(0L);

    int REPETITIONS = 10;
    String ALPHABET = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int MIN_LENGTH = 100;
    int MAX_LENGTH = 1000;

    for (int rep = 0; rep < REPETITIONS; rep++) {
      String text = randText(rnd, ALPHABET, randLength(rnd, MIN_LENGTH, MAX_LENGTH));
      assertEquals(text, decode(decoder, encode(encoder, text)));
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

  // ==========================================================================
  // UTILS
  // ==========================================================================


  private String decode(Decoder decoder, byte[] cipher) throws IOException {

    InputStream in = new ByteArrayInputStream(cipher);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    decoder.decode(in, out);

    return new String(out.toByteArray());
  }

  private byte[] encode(Encoder encoder, String text) throws IOException {

    MultiPassInputStream in = new MultiPassStringInputStream(text);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    encoder.encode(in, out);

    return out.toByteArray();
  }

  private static class MultiPassStringInputStream extends MultiPassInputStream {

    private final String value;
    private ByteArrayInputStream in;

    private MultiPassStringInputStream(String value) {
      this.value = value;
      in = new ByteArrayInputStream(value.getBytes());
    }

    @Override
    public void close() throws IOException {
      // do nothing
    }

    @Override
    public int read() throws IOException {
      return in.read();
    }

    @Override
    public void reset() throws IOException {
      in = new ByteArrayInputStream(value.getBytes());
    }
  }
}
