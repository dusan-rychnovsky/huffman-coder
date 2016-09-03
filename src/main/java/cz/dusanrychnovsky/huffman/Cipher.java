package cz.dusanrychnovsky.huffman;

/**
 * Contains an encrypted plain text. This is a product of {@link Encoder}. Use
 * {@link Decoder} to get the original plain text.
 */
public class Cipher {

  private final BitString bits;
  private final Node tree;

  Cipher(BitString bits, Node tree) {
    this.bits = bits;
    this.tree = tree;
  }

  BitString getBits() {
    return bits;
  }

  Node getTree() {
    return tree;
  }
}
