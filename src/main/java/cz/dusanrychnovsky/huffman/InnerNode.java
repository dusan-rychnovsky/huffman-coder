package cz.dusanrychnovsky.huffman;

import static org.apache.commons.lang3.StringUtils.repeat;

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

  @Override
  public void dump(StringBuilder builder, int level) {
    builder.append(repeat("--", level));
    builder.append("[" + getLabel() + "]");
    builder.append("\n");

    leftNode.dump(builder, level + 1);
    rightNode.dump(builder, level + 1);
  }
}