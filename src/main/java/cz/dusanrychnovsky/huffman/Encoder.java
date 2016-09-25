package cz.dusanrychnovsky.huffman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Encodes a {@link String} plain-text as a {@link Cipher} using Huffman
 * encoding. Use {@link Decoder} to convert the cipher back to the original
 * plain-text.
 */
public class Encoder {

  private static final Logger log = LoggerFactory.getLogger(Encoder.class);

  // TODO add debug logging

  public void encode(MultiPassInputStream in, OutputStream out)
      throws IOException {

    log.info("Running ENCODE.");

    Tree tree = new Tree.Builder().buildFrom(in);
    tree.saveTo(out);

    Map<Character, BitString> table = buildTranslationTable(tree.getRootNode());

    in.reset();
    long totalBitsSize = countSize(in, table);
    saveTo(totalBitsSize, out);

    in.reset();
    encode(in, out, table);
  }

  private void saveTo(long value, OutputStream out) throws IOException {
    out.write(toBytes(value));
  }

  private byte[] toBytes(long value) {
    return ByteBuffer.allocate(8).putLong(value).array();
  }

  private long countSize(InputStream in, Map<Character, BitString> table) throws IOException {
    log.info("Counting total bit size.");

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    long result = 0;
    int i;
    while ((i = reader.read()) != -1) {
      BitString string = table.get((char) i);
      result += string.size();
    }

    log.info("### total cipher size (bits): " + result);
    return result;
  }

  private Map<Character, BitString> buildTranslationTable(Node tree) {
    log.info("Building translation table.");
    Map<Character, BitString> result = new HashMap<>();
    LinkedList<Byte> prefix = new LinkedList<>();
    buildTranslationTable(((InnerNode) tree).getLeftNode(), result, prefix);
    buildTranslationTable(((InnerNode) tree).getRightNode(), result, prefix);
    return result;
  }

  private void buildTranslationTable(
          Node node, Map<Character, BitString> acc, LinkedList<Byte> prefix) {

    prefix.add(node.getLabel());
    if (node instanceof LeafNode) {
      acc.put(((LeafNode) node).getCharacter(), new BitString(prefix));
    }
    if (node instanceof InnerNode) {
      buildTranslationTable(((InnerNode) node).getLeftNode(), acc, prefix);
      buildTranslationTable(((InnerNode) node).getRightNode(), acc, prefix);
    }
    prefix.remove(prefix.size() - 1);
  }

  private void encode(InputStream in, OutputStream out, Map<Character, BitString> table)
      throws IOException {
    log.info("Encoding the characters.");

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    BitOutputStream bOut = new BitOutputStream(out);

    int i;
    while ((i = reader.read()) != -1) {
      BitString string = table.get((char) i);
      bOut.append(string);
    }

    bOut.close();
  }
}
