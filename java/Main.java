import java.util.function.Supplier;

public class Main {
    public static void printHeader() {
        System.out.println("language, benchmark, run_id, time");
    }

    public static void measure(Supplier<?> act, String label, long num_iters) {
        for (long it_num = 0; it_num < num_iters; it_num++) {
            double x1 = System.nanoTime();
            act.get();
            double x2 = System.nanoTime();
            double diff = (x2 - x1) / 1.0e6;
            System.out.println("java, " + label + ", " + it_num + ", " + diff);
        }
    }

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

    public static class Cons {
        public final long head;
        public final Cons tail;

        public Cons(long head, Cons tail) {
            this.head = head;
            this.tail = tail;
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

    public static class Tree {
        public final Tree left, right;
        public final long elem;

        public Tree(Tree left, long elem, Tree right) {
            this.left = left;
            this.elem = elem;
            this.right = right;
        }
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

    public static void main(String[] args) {
        printHeader();
        int size = Integer.parseInt(System.getenv("BENCH_SIZE"));
        long depth = Integer.parseInt(System.getenv("BENCH_DEPTH"));
        long n_iters = Integer.parseInt(System.getenv("BENCH_ITER"));

        measure(() -> sleep(), "100ms", 10);

        measure(() -> sum(size), "sum", n_iters);

        measure(() -> allocVector(size), "alloc_vector", n_iters);
        var vec = allocVector(size);
        measure(() -> sumVector(vec), "sum_vector", n_iters);


        measure(() -> allocList(size), "alloc_list", n_iters);
        var list = allocList(size);
        measure(() -> sumList(list), "sum_list", n_iters);

        measure(() -> allocFullTree(depth), "alloc_full_tree", n_iters);
        var tree = allocFullTree(depth);
        measure(() -> sumTree(tree), "sum_tree", n_iters);
    }
}
