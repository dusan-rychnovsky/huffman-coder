package cz.dusanrychnovsky.huffman;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Encodes a {@link String} plain-text as a {@link Cipher} using Huffman
 * encoding. Use {@link Decoder} to convert the cipher back to the original
 * plain-text.
 */
public class Encoder {

  // TODO add debug logging

  /**
   * Encodes the given {@link String} plain-text as a {@link Cipher}.
   */
  public Cipher encode(String text) {

    if (text.isEmpty()) {
      throw new IllegalArgumentException("Text must be non-empty.");
    };

    Map<Character, Integer> occurrences = computeOccurrences(text);
    Heap<Node> heap = buildHeap(occurrences);
    Node tree = buildTree(heap);
    tree = sanitizeTree(tree);
    labelNodes(tree);

    Map<Character, BitString> table = buildTranslationTable(tree);
    BitString cipher = encode(text, table);

    return new Cipher(cipher, tree);
  }

  private Map<Character, Integer> computeOccurrences(String text) {
    Map<Character, Integer> result = new HashMap<>();
    for (char ch : text.toCharArray()) {
      result.merge(ch, 1, (k, v) -> v + 1);
    }
    return result;
  }

  private Heap<Node> buildHeap(Map<Character, Integer> occurrences) {
    Heap<Node> result = Heap.newMinHeap();
    for (Map.Entry<Character, Integer> entry : occurrences.entrySet()) {
      result.add(entry.getValue(), new LeafNode(entry.getKey()));
    }
    return result;
  }

  private Node buildTree(Heap<Node> heap) {
    while (heap.size() > 1) {
      Integer firstKey = heap.getMinKey();
      Node firstNode = heap.poll();
      Integer secondKey = heap.getMinKey();
      Node secondNode = heap.poll();
      heap.add(
        firstKey + secondKey,
        firstNode.mergeWith(secondNode)
      );
    }
    return heap.poll();
  }

  private Node sanitizeTree(Node tree) {
    if (tree instanceof InnerNode) {
      return tree;
    }
    else {
      return new InnerNode(tree, new DummyNode());
    }
  }

  private void labelNodes(Node node) {
    if (node instanceof InnerNode) {
      // process left sub-tree
      Node leftNode = ((InnerNode) node).getLeftNode();
      leftNode.setLabel((byte) 0);
      labelNodes(leftNode);
      // process right sub-tree
      Node rightNode = ((InnerNode) node).getRightNode();
      rightNode.setLabel((byte) 1);
      labelNodes(rightNode);
    }
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

  private BitString encode(String text, Map<Character, BitString> table) {
    BitString result = new BitString();
    for (Character ch : text.toCharArray()) {
      BitString cipher = table.get(ch);
      result = result.append(cipher);
    }
    return result;
  }
}
