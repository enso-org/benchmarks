package org.enso.microbenchmarks;

public class Tree<A> {

  public final Tree left, right;
  public final A elem;

  public Tree(Tree left, A elem, Tree right) {
    this.left = left;
    this.elem = elem;
    this.right = right;
  }
}
