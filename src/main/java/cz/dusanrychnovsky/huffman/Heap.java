package cz.dusanrychnovsky.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implements an unbounded binary heap.
 *
 * The heap stores (K key, V value) elements ordered by key. A comparator is
 * required to determine the actual order of keys. For convenience, two
 * orderings have been pre-defined:
 *
 *  * {@link #newMinHeap}
 *  * {@link #newMaxHeap}
 *
 * The heap implements the following efficient operations (n being the number
 * of elements stored in the heap):
 *
 *  * {@link #add} - O(log n)
 *  * {@link #poll} - O(log n)
 *  * {@link #getMinKey()} - O(1)
 *  * {@link #size} - O(1)
 *  * {@link #isEmpty} - O(1)
 *
 */
public class Heap<K, V> {

  // TODO: return Optional instead of null for operations

  private final Comparator<K> comparator;
  private final ArrayList<Node<K, V>> data = new ArrayList<>();
  private int size;

  /**
   * Creates a new min-heap - a heap with elements sorted by lowest key first.
   */
  public static <K extends Comparable<K>, V> Heap<K, V> newMinHeap() {
    return new Heap<>(Comparable::compareTo);
  }

  /**
   * Creates a new max-heap - a heap with elements sorted by highest key first.
   */
  public static <K extends Comparable<K>, V> Heap<K, V> newMaxHeap() {
    return new Heap<>((first, second) -> second.compareTo(first));
  }

  /**
   * Creates a new heap with a custom key comparator. The heap will have
   * elements sorted by lowest key first, lowest being according to the
   * comparator.
   */
  public Heap(Comparator<K> comparator) {
    this.comparator = comparator;
  }

  /**
   * Adds a new value to the heap under the given key. If there already is
   * a value with the same key in the heap, both values will be stored.
   */
  public void add(K key, V value) {

    // insert the new node as a last leaf
    data.add(size, new Node<>(key, value));
    size++;

    // move up and fix the heap invariant
    int currIdx = size - 1, parentIdx = getParentIdx(currIdx);
    while (parentIdx != -1) {
      Node<K, V> curr = data.get(currIdx);
      Node<K, V> parent = data.get(parentIdx);

      if (isCorrect(parent, curr)) {
        // the invariant is correct at this level - the whole heap is correct
        break;
      }

      data.set(parentIdx, curr);
      data.set(currIdx, parent);

      currIdx = parentIdx;
      parentIdx = getParentIdx(currIdx);
    }
  }

  /**
   * Returns the lowest (according to the comparator) key in the heap.
   */
  public K getMinKey() {
    return isEmpty() ? null : data.get(0).getKey();
  }

  /**
   * Retrieves and removes value with lowest (according to the comparator) key.
   */
  public V poll() {

    if (size == 0) {
      return null;
    }

    // save root-node value to be returned as the result
    V result = data.get(0).getValue();

    if (size == 1) {
      size = 0;
      return result;
    }

    // drop root-node and replace it by last leaf
    data.set(0, data.get(size - 1));
    data.remove(size - 1);
    size--;

    // move down and fix the heap invariant
    int currIdx = 0, childIdx = getMinChildIdx(currIdx);
    while (childIdx != -1) {
      Node<K, V> curr = data.get(currIdx);
      Node<K, V> child = data.get(childIdx);

      if (isCorrect(curr, child)) {
        // the invariant is correct at this level - the whole heap is correct
        break;
      }

      data.set(currIdx, child);
      data.set(childIdx, curr);

      currIdx = childIdx;
      childIdx = getMinChildIdx(currIdx);
    }

    return result;
  }

  /**
   * Returns the size of the heap, i.e. the number of values it stores.
   */
  public int size() {
    return size;
  }

  /**
   * Returns true if the size of the heap is zero, i.e. if the heap stores
   * no values.
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  private boolean isCorrect(Node<K, V> parent, Node<K, V> child) {
    return comparator.compare(parent.getKey(), child.getKey()) <= 0;
  }

  private int getParentIdx(int idx) {
    if (idx > 0) {
      return (idx - 1) / 2;
    }
    else {
      return -1;
    }
  }

  private int getMinChildIdx(int idx) {

    int leftChildIdx = getLeftChildIdx(idx);
    if (leftChildIdx == -1) {
      // if a node has no left child, it has no childs at all
      return -1;
    }

    int rightChildIdx = getRightChildIdx(idx);
    if (rightChildIdx == -1) {
      // if a node has no right child, the min child is it's left child
      return leftChildIdx;
    }

    K leftChildKey = data.get(leftChildIdx).getKey();
    K rightChildKey = data.get(rightChildIdx).getKey();

    if (comparator.compare(leftChildKey, rightChildKey) <= 0) {
      return leftChildIdx;
    }
    else {
      return rightChildIdx;
    }
  }

  private int getLeftChildIdx(int idx) {
    int result = 2 * idx + 1;
    if (result < size) {
      return result;
    }
    else {
      return -1;
    }
  }

  private int getRightChildIdx(int idx) {
    int result = 2 * idx + 2;
    if (result < size) {
      return result;
    }
    else {
      return -1;
    }
  }

  private static final class Node<K, V> {

    private final K key;
    private final V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "Node(" + key + ", " + value + ")";
    }
  }
}
