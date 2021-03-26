function printHeader() {
    console.log("language, benchmark, run_id, time");
}

function measure(act, label, num_iters) {
    for (let it_num = 0; it_num < num_iters; it_num++) {
        let x1 = process.hrtime();
        act();
        let x2 = process.hrtime();
        let diff_seconds = x2[0] - x1[0];
        let diff_nanos = x2[1] - x1[1];
        let diff = (diff_seconds * 1000.0) + (diff_nanos / 1000000.0);
        console.log("js, " + label + ", " + it_num + ", " + diff.toFixed(10));
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
// n_iters = 10
let size = 1000; // TODO FIXME temporary workaround to quickly test
let depth = 4;
let n_iters = 2;

measure(function() {
    Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 100);
}, "100ms", 10);

measure(function() { return sum(size); }, "sum", n_iters);
