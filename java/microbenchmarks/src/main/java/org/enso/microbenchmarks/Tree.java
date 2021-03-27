package org.enso.microbenchmarks;

public class Tree {

  public final Tree left, right;
  public final long elem;

  public Tree(Tree left, long elem, Tree right) {
    this.left = left;
    this.elem = elem;
    this.right = right;
  }
}
