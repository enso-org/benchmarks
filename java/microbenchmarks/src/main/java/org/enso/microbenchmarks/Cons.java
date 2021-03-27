package org.enso.microbenchmarks;

public class Cons {

  public final long head;
  public final Cons tail;

  public Cons(long head, Cons tail) {
    this.head = head;
    this.tail = tail;
  }
}
