package cz.dusanrychnovsky.huffman;

/**
 * Contains an encrypted plain text. This is a product of {@link Encoder}. Use
 * {@link Decoder} to get the original plain text.
 */
public class Cipher {

  // TODO: one byte can represent up to 8 bits, not just one
  private final byte[] bytes;
  private final Node tree;

  Cipher(byte[] bytes, Node tree) {
    this.bytes = bytes;
    this.tree = tree;
  }

  byte[] getBytes() {
    return bytes;
  }

  Node getTree() {
    return tree;
  }
}
