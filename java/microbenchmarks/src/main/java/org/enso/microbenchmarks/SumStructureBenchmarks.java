package org.enso.microbenchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class SumStructureBenchmarks {

  @State(Scope.Thread)
  public static class StructureState extends BaseBenchmarks.BaseState {

    Point<Long>[] vec = null;
    Cons<Point<Long>> list = null;
    Tree<Point<Long>> tree = null;

    @Setup(Level.Trial)
    public void doSetup() {
      vec = Implementations.allocVector(million);
      list = Implementations.allocList(million);
      tree = Implementations.allocFullTree(tree_depth);
    }
  }

  @Benchmark
  public void sum_vector(Blackhole bh, StructureState state) {
    var res = Implementations.sumVector(state.vec);
    bh.consume(res);
  }

  @Benchmark
  public void sum_list(Blackhole bh, StructureState state) {
    var res = Implementations.sumList(state.list);
    bh.consume(res);
  }

  @Benchmark
  public void sum_tree(Blackhole bh, StructureState state) {
    var res = Implementations.sumTree(state.tree);
    bh.consume(res);
  }
}
