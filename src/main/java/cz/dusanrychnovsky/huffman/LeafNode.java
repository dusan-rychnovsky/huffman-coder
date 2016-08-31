package cz.dusanrychnovsky.huffman;

class LeafNode extends Node {

  private final Character character;

  public LeafNode(Character character) {
    this.character = character;
  }

  public Character getCharacter() {
    return character;
  }
}
