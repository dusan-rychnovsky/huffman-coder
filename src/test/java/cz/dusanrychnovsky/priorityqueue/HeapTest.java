package cz.dusanrychnovsky.priorityqueue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static cz.dusanrychnovsky.priorityqueue.Heap.newMaxHeap;
import static cz.dusanrychnovsky.priorityqueue.Heap.newMinHeap;
import static java.util.Collections.sort;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeapTest {

  // ==========================================================================
  // MIN-HEAP
  // ==========================================================================

  @Test
  public void minHeap_pollRetrievesAndRemovesLowestKeyElement() {

    Heap<String> queue = newMinHeap();
    queue.add(4, "first-item");
    queue.add(5, "second-item");
    queue.add(2, "third-item");
    queue.add(4, "fourth-item");
    queue.add(1, "fifth-item");
    queue.add(6, "sixth-item");
    assertEquals(6, queue.size());

    assertEquals("fifth-item", queue.poll());
    assertEquals(5, queue.size());

    assertEquals("third-item", queue.poll());
    assertEquals(4, queue.size());

    assertEquals("fourth-item", queue.poll());
    assertEquals(3, queue.size());

    assertEquals("first-item", queue.poll());
    assertEquals(2, queue.size());

    assertEquals("second-item", queue.poll());
    assertEquals(1, queue.size());

    assertEquals("sixth-item", queue.poll());
    assertEquals(0, queue.size());
  }

  @Test
  public void minHeap_pollReturnsNullOnEmptyQueue() {

    Heap<String> queue = newMinHeap();
    assertTrue(queue.isEmpty());

    assertEquals(null, queue.poll());
  }

  @Test
  public void minHeap_getMinKeyReturnsLowestKey() {

    Heap<String> queue = newMinHeap();
    queue.add(4, "first-item");
    queue.add(2, "second-item");
    queue.add(1, "third-item");
    queue.add(5, "fourth-item");

    assertEquals(new Integer(1), queue.getMinKey());
  }

  @Test
  public void minHeap_getMinKeyReturnsNullOnEmptyQueue() {

    Heap<String> queue = newMinHeap();
    assertTrue(queue.isEmpty());

    assertEquals(null, queue.getMinKey());
  }

  // ==========================================================================
  // MAX-HEAP
  // ==========================================================================

  @Test
  public void maxHeap_pollRetrievesAndRemovesLowestKeyElement() {

    Heap<String> queue = newMaxHeap();
    queue.add(4, "first-item");
    queue.add(5, "second-item");
    queue.add(2, "third-item");
    queue.add(4, "fourth-item");
    queue.add(1, "fifth-item");
    queue.add(6, "sixth-item");
    assertEquals(6, queue.size());

    assertEquals("sixth-item", queue.poll());
    assertEquals(5, queue.size());

    assertEquals("second-item", queue.poll());
    assertEquals(4, queue.size());

    assertEquals("first-item", queue.poll());
    assertEquals(3, queue.size());

    assertEquals("fourth-item", queue.poll());
    assertEquals(2, queue.size());

    assertEquals("third-item", queue.poll());
    assertEquals(1, queue.size());

    assertEquals("fifth-item", queue.poll());
    assertEquals(0, queue.size());
  }

  @Test
  public void maxHeap_pollReturnsNullOnEmptyQueue() {

    Heap<String> queue = newMaxHeap();
    assertTrue(queue.isEmpty());

    assertEquals(null, queue.poll());
  }

  @Test
  public void maxHeap_getMinKeyReturnsLowestKey() {

    Heap<String> queue = newMaxHeap();
    queue.add(4, "first-item");
    queue.add(2, "second-item");
    queue.add(1, "third-item");
    queue.add(5, "fourth-item");

    assertEquals(new Integer(5), queue.getMinKey());
  }

  @Test
  public void maxHeap_getMinKeyReturnsNullOnEmptyQueue() {

    Heap<String> queue = newMaxHeap();
    assertTrue(queue.isEmpty());

    assertEquals(null, queue.getMinKey());
  }

  // ==========================================================================
  // RANDOMIZED TESTS
  // ==========================================================================

  @Test
  public void randomizedTest() {

    Random rnd = new Random(0L);

    Heap<Integer> minHeap = Heap.newMinHeap();
    Heap<Integer> maxHeap = Heap.newMaxHeap();

    int COUNT = 100_000;
    List<Integer> keys = new ArrayList<>(COUNT);

    for (int i = 0; i < COUNT; i++) {
      Integer key = rnd.nextInt();
      keys.add(key);
      minHeap.add(key, key);
      maxHeap.add(key, key);
    }

    sort(keys);

    for (int i = 0; i < COUNT; i++) {
      assertEquals(keys.get(i), minHeap.getMinKey());
      minHeap.poll();
      assertEquals(keys.get(COUNT - i - 1), maxHeap.getMinKey());
      maxHeap.poll();
    }

    assertTrue(minHeap.isEmpty());
  }
}
