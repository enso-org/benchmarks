function measure(act, label, iter_size, num_iters) {
    for (let it_num = 0; it_num < num_iters; it_num++) {
        let x1 = process.hrtime();
        for (let i = 0; i < iter_size; i++) {
            let r = act();
            consume(r);
        }
        let x2 = process.hrtime();
        let diff_seconds = x2[0] - x1[0];
        let diff_nanos = x2[1] - x1[1];
        let diff = (diff_seconds * 1000.0) + (diff_nanos / 1.0e6);
        let avg = diff / iter_size;
        console.log("js, " + label + ", " + it_num + ", " + avg.toFixed(3));
        global.gc();
    }
}

function bench(act, label) {
    measure(act, label, 50, 50);
}

function arg_int() {
    return parseInt(process.argv[2]);
}

/** A naive implementation of a blackhole. */
function consume(value) {
    // our benchmarks never return strings, so this will never happen
    if (value == "UNEXPECTED_RESULT") {
        throw 'Should never happen';
    }
}

exports.bench = bench;
exports.arg_int = arg_int;
