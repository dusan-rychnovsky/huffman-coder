package cz.dusanrychnovsky.huffman;

import java.io.*;

/**
 * Decodes a {@link Cipher} as a {@link String} plain-text using Huffman
 * encoding. Use {@link Encoder} to encode a plain-text to a {@link Cipher}.
 */
public class Decoder {

  public void decode(InputStream in, OutputStream out)
      throws IOException {

    Tree tree = Tree.loadFrom(in);
    BitString bits = BitString.loadFrom(in);

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

    Node rootNode = tree.getRootNode();
    Node currNode = rootNode;
    for (byte currBit : bits) {

      if (currBit == 0) {
        currNode = ((InnerNode) currNode).getLeftNode();
      }
      else {
        currNode = ((InnerNode) currNode).getRightNode();
      }

      if (currNode instanceof LeafNode) {
        writer.append(((LeafNode) currNode).getCharacter());
        currNode = rootNode;
      }
    }

    writer.flush();
  }
}
