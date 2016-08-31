package cz.dusanrychnovsky.huffman;

class InnerNode extends Node {

  // TODO: use lombok all over the place

  private final Node leftNode;
  private final Node rightNode;

  public InnerNode(Node leftNode, Node rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }

  public Node getLeftNode() {
    return leftNode;
  }

  public Node getRightNode() {
    return rightNode;
  }
}