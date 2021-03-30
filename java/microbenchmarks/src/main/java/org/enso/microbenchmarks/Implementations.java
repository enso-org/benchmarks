package org.enso.microbenchmarks;

public class Implementations {

  public static long sum(long n) {
    long acc = 0;
    long i = 1;
    while (i <= n) {
      acc += i;
      i++;
    }
    return acc;
  }

  public static Point<Long>[] allocVector(int n) {
    var array = new Point[n];
    for (int i = 0; i < n; i++) {
      long x = i;
      array[i] = new Point<Long>(x, x);
    }
    return array;
  }

  public static long sumVector(Point<Long>[] vec) {
    long acc = 0;
    for (int i = 0; i < vec.length; i++) {
      long point_sum = vec[i].x + vec[i].y;
      acc += point_sum;
    }
    return acc;
  }

  public static Cons<Point<Long>> allocList(long n) {
    Cons<Point<Long>> res = null;
    for (long i = n; i >= 0; i--) {
      res = new Cons<>(new Point<Long>(i, i), res);
    }
    return res;
  }

  public static long sumList(Cons<Point<Long>> list) {
    long acc = 0;
    while (list != null) {
      acc += list.head.x + list.head.y;
      list = list.tail;
    }
    return acc;
  }

  private static class PointTreeAllocator {

    private final long depth;
    private long ix = 0;

    public PointTreeAllocator(long depth) {
      this.depth = depth;
    }

    private Tree<Point<Long>> go(long remainingDepth) {
      if (remainingDepth == 0) {
        return null;
      } else {
        Tree l = go(remainingDepth - 1);
        long i = ix++;
        var e = new Point<>(i, i);
        Tree r = go(remainingDepth - 1);
        return new Tree<>(l, e, r);
      }
    }

    public Tree<Point<Long>> allocate() {
      return go(depth);
    }
  }

  public static Tree<Point<Long>> allocFullTree(long depth) {
    return (new PointTreeAllocator(depth)).allocate();
  }

  public static long sumTree(Tree<Point<Long>> tree) {
    if (tree == null) {
      return 0;
    }

    long l = sumTree(tree.left);
    long r = sumTree(tree.right);
    return l + tree.elem.x + tree.elem.y + r;
  }

}
