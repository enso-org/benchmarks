package org.enso.microbenchmarks;

public class Verify {
    public static void main(String... args) {
        int ten_million = 10_000_000;
        int million = 1_000_000;
        long tree_depth = 17;
        System.out.println(Implementations.sum(ten_million));
        System.out.println(Implementations.sumVector(Implementations.allocVector(million)));
        System.out.println(Implementations.sumList(Implementations.allocList(million)));
        System.out.println(Implementations.sumTree(Implementations.allocFullTree(tree_depth)));
    }
}
