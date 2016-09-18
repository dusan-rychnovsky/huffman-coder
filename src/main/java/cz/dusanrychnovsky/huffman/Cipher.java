package cz.dusanrychnovsky.huffman;

/**
 * Contains an encrypted plain text. This is a product of {@link Encoder}. Use
 * {@link Decoder} to get the original plain text.
 */
public class Cipher {

  private final BitString bits;
  private final Tree tree;

  Cipher(BitString bits, Tree tree) {
    this.bits = bits;
    this.tree = tree;
  }

  BitString getBits() {
    return bits;
  }

  Tree getTree() {
    return tree;
  }
}
