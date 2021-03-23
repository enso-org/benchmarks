import java.util.function.Supplier;

public class Main {
    public static void printHeader() {
        System.out.println("language, benchmark, run_id, time");
    }

    public static void measure(Supplier<?> act, String label, int iter_size, int num_iters) {
        for (int it_num = 0; it_num < num_iters; it_num++) {
            double time_sum = 0;
            for (int i = 0; i < iter_size; i++) {
                double x1 = System.nanoTime();
                act.get();
                double x2 = System.nanoTime();
                time_sum += (x2 - x1) / 1000000.0;
            }
            double avg = time_sum / ((double) iter_size);
            System.out.println("java, " + label + ", " + it_num + ", " + avg);
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


    public static void main(String[] args) {
        printHeader();
        // size = 100000000
        // depth = 25
        // iter_size = 100
        // n_iters = 10
        int size = 100; // TODO FIXME temporary workaround to quickly test
        int depth = 4;
        int iter_size = 2;
        int n_iters = 2;

        measure(() -> sum(size), "sum", iter_size, n_iters);
    }
}
