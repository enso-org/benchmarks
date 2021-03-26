import java.util.function.Supplier;

public class Main {
    public static void printHeader() {
        System.out.println("language, benchmark, run_id, time");
    }

    public static void measure(Supplier<?> act, String label, int num_iters) {
        for (int it_num = 0; it_num < num_iters; it_num++) {
            double x1 = System.nanoTime();
            act.get();
            double x2 = System.nanoTime();
            double diff = (x2 - x1) / 1000000.0;
            System.out.println("java, " + label + ", " + it_num + ", " + diff);
        }
    }

    public static int sum(int n) {
        int acc = 0;
        int i = 1;
        while (i <= n) {
            acc += i;
            i++;
        }
        return acc;
    }

    public static int[] allocVector(int n) {
        var array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int sumVector(int[] vec) {
        int acc = 0;
        for (int i = 0; i < vec.length; i++) {
            acc += vec[i];
        }
        return acc;
    }

    public static int sleep() {
        try {
            Thread.sleep(100);
            return 0;
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected interrupt", e);
        }
    }

    public static class Cons {
        public final int head;
        public final Cons tail;

        public Cons(int head, Cons tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public static Cons allocList(int n) {
        Cons res = null;
        for (int i = n; i >= 0; i--) {
            res = new Cons(i, res);
        }
        return res;
    }

    public static int sumList(Cons list) {
        int acc = 0;
        while (list != null) {
            acc += list.head;
            list = list.tail;
        }
        return acc;
    }

    public static class Tree {
        public final Tree left, right;
        public final int elem;

        public Tree(Tree left, int elem, Tree right) {
            this.left = left;
            this.elem = elem;
            this.right = right;
        }
    }

    private static class TreeAllocator {
        private final int depth;
        private int ix = 0;

        public TreeAllocator(int depth) {
            this.depth = depth;
        }

        private Tree go(int remainingDepth) {
            if (remainingDepth == 0) {
                return null;
            } else {
                Tree l = go(remainingDepth - 1);
                int e = ix++;
                Tree r = go(remainingDepth - 1);
                return new Tree(l, e, r);
            }
        }

        public Tree allocate() {
            return go(depth);
        }
    }

    public static Tree allocFullTree(int depth) {
        return (new TreeAllocator(depth)).allocate();
    }

    public static int sumTree(Tree tree) {
        if (tree == null) {
            return 0;
        }

        int l = sumTree(tree.left);
        int r = sumTree(tree.right);
        return l + tree.elem + r;
    }

    public static void main(String[] args) {
        printHeader();
        // size = 100000000
        // depth = 25
        // n_iters = 10
        int size = 100; // TODO FIXME temporary workaround to quickly test
        int depth = 4;
        int n_iters = 2;

        measure(() -> sleep(), "100ms", 10);

        measure(() -> sum(size), "sum", n_iters);

        measure(() -> allocVector(size), "alloc_vector", n_iters);
        var vec = allocVector(size);
        measure(() -> sumVector(vec), "sum_vector", n_iters);


        measure(() -> allocList(size), "alloc_list", n_iters);
        var list = allocList(size);
        measure(() -> sumList(list), "sum_list", n_iters);

        measure(() -> allocFullTree(depth), "alloc_tree", n_iters);
        var tree = allocFullTree(depth);
        measure(() -> sumTree(tree), "sum_tree", n_iters);
    }
}
