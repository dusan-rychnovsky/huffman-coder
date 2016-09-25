package cz.dusanrychnovsky.huffman;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Tree implements Serializable {

  private static final Logger log = LoggerFactory.getLogger(Tree.class);

  private final Node rootNode;

  public Tree(Node rootNode) {
    this.rootNode = rootNode;
  }

  public String dump() {
    StringBuilder builder = new StringBuilder();
    rootNode.dump(builder, 0);
    return builder.toString();
  }

  public Node getRootNode() {
    return rootNode;
  }

  // ==========================================================================
  // SAVE TO OUTPUT-STREAM
  // ==========================================================================

  public void saveTo(OutputStream out) throws IOException {
    log.info("Saving the tree to output.");
    byte[] bytes = SerializationUtils.serialize(this);
    out.write(toBytes(bytes.length));
    out.write(bytes);
  }

  private static byte[] toBytes(int value) {
    return ByteBuffer.allocate(4).putInt(value).array();
  }

  // ==========================================================================
  // LOAD FROM INPUT-STREAM
  // ==========================================================================

  public static Tree loadFrom(InputStream in) throws IOException {
    int length = readInt(in);
    byte[] bytes = new byte[length];
    in.read(bytes);
    return SerializationUtils.deserialize(bytes);
  }

  private static int readInt(InputStream in) throws IOException {
    byte[] bytes = new byte[4];
    in.read(bytes);
    return toInt(bytes);
  }

  private static int toInt(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getInt();
  }

  // ==========================================================================
  // BUILD FROM INPUT-STREAM
  // ==========================================================================

  public static class Builder {

    public Tree buildFrom(InputStream in) throws IOException {
      log.info("Building Huffman tree.");

      BufferedReader reader = new BufferedReader(new InputStreamReader(in));

      Map<Character, Long> occurrences = countOccurrences(reader);
      Heap<Long, Node> heap = buildHeap(occurrences);
      Node rootNode = buildTree(heap);
      rootNode = sanitizeTree(rootNode);
      labelNodes(rootNode);

      log.info("Huffman tree built.");
      return new Tree(rootNode);
    }

    private Map<Character, Long> countOccurrences(Reader reader) throws IOException {
      log.info("Counting occurences.");

      Map<Character, Long> result = new HashMap<>();
      int i;
      while ((i = reader.read()) != -1) {
        result.merge((char) i, 1L, (k, v) -> v + 1);
      }
      return result;
    }

    private Heap<Long, Node> buildHeap(Map<Character, Long> occurrences) {
      log.info("Building heap.");
      Heap<Long, Node> result = Heap.newMinHeap();
      for (Map.Entry<Character, Long> entry : occurrences.entrySet()) {
        result.add(entry.getValue(), new LeafNode(entry.getKey()));
      }
      return result;
    }

    private Node buildTree(Heap<Long, Node> heap) {
      log.info("Building tree.");
      while (heap.size() > 1) {
        Long firstKey = heap.getMinKey();
        Node firstNode = heap.poll();
        Long secondKey = heap.getMinKey();
        Node secondNode = heap.poll();
        heap.add(
          firstKey + secondKey,
          firstNode.mergeWith(secondNode)
        );
      }
      return heap.poll();
    }

    private Node sanitizeTree(Node rootNode) {
      log.info("Sanitizing tree.");
      if (rootNode instanceof InnerNode) {
        return rootNode;
      }
      else {
        return new InnerNode(rootNode, new DummyNode());
      }
    }

    private void labelNodes(Node node) {
      log.info("Labeling nodes.");
      label(node);
    }

    private void label(Node node) {
      if (node instanceof InnerNode) {
        // process left sub-tree
        Node leftNode = ((InnerNode) node).getLeftNode();
        leftNode.setLabel((byte) 0);
        label(leftNode);
        // process right sub-tree
        Node rightNode = ((InnerNode) node).getRightNode();
        rightNode.setLabel((byte) 1);
        label(rightNode);
      }
    }
  }
}
