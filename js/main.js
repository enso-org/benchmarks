function printHeader() {
    console.log("language, benchmark, run_id, time");
}

function measure(act, label, iter_size, num_iters) {
    for (let it_num = 0; it_num < num_iters; it_num++) {
        let time_sum = 0;
        for (let i = 0; i < iter_size; i++) {
            let x1 = process.hrtime();
            act();
            let x2 = process.hrtime();
            let diff_seconds = x2[0] - x1[0];
            let diff_nanos = x2[1] - x1[1];
            let diff = (diff_seconds * 1000.0) + (diff_nanos / 1000000.0);
            time_sum += diff;
        }
        let avg = time_sum / iter_size;
        console.log("js, " + label + ", " + it_num + ", " + avg.toFixed(10));
    }
}

function sum(n) {
    let acc = 0;
    let i = 1;
    while (i <= n) {
        acc += i;
        i++;
    }
    return acc;
}

printHeader();

// size = 100000000
// depth = 25
// iter_size = 100
// n_iters = 10
let size = 1000; // TODO FIXME temporary workaround to quickly test
let depth = 4;
let iter_size = 2;
let n_iters = 2;

measure(function() { return sum(size); }, "sum", iter_size, n_iters);
