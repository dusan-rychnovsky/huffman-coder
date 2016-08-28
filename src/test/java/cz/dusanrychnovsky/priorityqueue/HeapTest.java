package cz.dusanrychnovsky.priorityqueue;

import org.junit.Test;

import static cz.dusanrychnovsky.priorityqueue.Heap.newMaxHeap;
import static cz.dusanrychnovsky.priorityqueue.Heap.newMinHeap;
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
}
