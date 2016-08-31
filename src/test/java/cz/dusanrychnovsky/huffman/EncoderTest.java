package cz.dusanrychnovsky.huffman;

import org.junit.Test;

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
  public void encodesASingleCharacterPlainText() {
    String text = "aaaaa";
    assertEquals(text, decoder.decode(encoder.encode(text)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void failsEncodingEmptyText() {
    encoder.encode("");
  }

  // TODO add generated tests
}
