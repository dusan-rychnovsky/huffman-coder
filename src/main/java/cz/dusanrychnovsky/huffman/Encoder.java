package cz.dusanrychnovsky.huffman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

  public void encode(MultiPassInputStream mIn, OutputStream out)
      throws IOException {

    log.info("Going to run ENCODE.");

    log.info("Building Huffman tree.");
    Tree tree = new Tree.Builder().buildFrom(mIn.get());

    log.info("Saving the tree to output.");
    tree.saveTo(out);

    log.info("Building translation table.");
    Map<Character, BitString> table = buildTranslationTable(tree.getRootNode());

    log.info("Generating bit-string.");
    BitString result = encode(mIn.get(), table);

    log.info("Saving bit-string to output.");
    result.saveTo(out);

    log.info("ENCODE done.");
  }

  private Map<Character, BitString> buildTranslationTable(Node tree) {
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

  private BitString encode(InputStream in, Map<Character, BitString> table)
      throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    BitString result = new BitString();
    int i;
    while ((i = reader.read()) != -1) {
      char ch = (char) i;
      BitString string = table.get(ch);
      result = result.append(string);
    }

    return result;
  }
}
