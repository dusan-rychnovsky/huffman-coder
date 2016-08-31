package cz.dusanrychnovsky.huffman;

/**
 * Decodes a {@link Cipher} as a {@link String} plain-text using Huffman
 * encoding. Use {@link Encoder} to encode a plain-text to a {@link Cipher}.
 */
public class Decoder {

  /**
   * Decodes the given {@link Cipher} to a {@link String} plain-text.
   */
  public String decode(Cipher cipher) {
    StringBuilder result = new StringBuilder();

    byte[] bytes = cipher.getBytes();
    Node rootNode = cipher.getTree();

    Node currNode = rootNode;
    for (int i = 0; i < bytes.length; i++) {
      byte currValue = bytes[i];

      if (currValue == 0) {
        currNode = ((InnerNode) currNode).getLeftNode();
      }
      else {
        currNode = ((InnerNode) currNode).getRightNode();
      }

      if (currNode instanceof LeafNode) {
        result.append(((LeafNode) currNode).getCharacter());
        currNode = rootNode;
      }
    }

    return result.toString();
  }
}
