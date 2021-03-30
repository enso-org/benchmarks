package org.enso.microbenchmarks;

public class Cons<A> {

  public final A head;
  public final Cons tail;

  public Cons(A head, Cons tail) {
    this.head = head;
    this.tail = tail;
  }
}
