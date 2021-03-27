package org.enso.microbenchmarks;

public class Implementations  {

  public static long sum(long n) {
    long acc = 0;
    long i = 1;
    while (i <= n) {
      acc += i;
      i++;
    }
    return acc;
  }

  public static long[] allocVector(int n) {
    var array = new long[n];
    for (int i = 0; i < n; i++) {
      array[i] = i;
    }
    return array;
  }

  public static long sumVector(long[] vec) {
    long acc = 0;
    for (int i = 0; i < vec.length; i++) {
      acc += vec[i];
    }
    return acc;
  }

  public static long sleep() {
    try {
      Thread.sleep(100);
      return 0;
    } catch (InterruptedException e) {
      throw new RuntimeException("Unexpected interrupt", e);
    }
  }

  public static Cons allocList(long n) {
    Cons res = null;
    for (long i = n; i >= 0; i--) {
      res = new Cons(i, res);
    }
    return res;
  }

  public static long sumList(Cons list) {
    long acc = 0;
    while (list != null) {
      acc += list.head;
      list = list.tail;
    }
    return acc;
  }

  private static class TreeAllocator {
    private final long depth;
    private long ix = 0;

    public TreeAllocator(long depth) {
      this.depth = depth;
    }

    private Tree go(long remainingDepth) {
      if (remainingDepth == 0) {
        return null;
      } else {
        Tree l = go(remainingDepth - 1);
        long e = ix++;
        Tree r = go(remainingDepth - 1);
        return new Tree(l, e, r);
      }
    }

    public Tree allocate() {
      return go(depth);
    }
  }

  public static Tree allocFullTree(long depth) {
    return (new TreeAllocator(depth)).allocate();
  }

  public static long sumTree(Tree tree) {
    if (tree == null) {
      return 0;
    }

    long l = sumTree(tree.left);
    long r = sumTree(tree.right);
    return l + tree.elem + r;
  }

}
