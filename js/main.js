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
        let diff = (diff_seconds * 1000.0) + (diff_nanos / 1.0e6);
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

function alloc_vector(n) {
    let array = new Int32Array(n);
    for (let i = 0; i < n; ++i) {
        array[i] = i;
    }
    return array;
}

function sum_vector(vec) {
    let N = vec.length;
    let acc = 0;
    for (let i = 0; i < N; ++i) {
        acc += vec[i];
    }
    return acc;
}

class Cons {
    constructor(head, tail) {
        this.head = head;
        this.tail = tail;
    }
}

function alloc_list(n) {
    let res = null;
    for (let i = n; i >= 0; i--) {
        res = new Cons(i, res);
    }
    return res;
}

function sum_list(list) {
    let acc = 0;
    while (list != null) {
        acc += list.head;
        list = list.tail;
    }
    return acc;
}

class Tree {
    constructor(left, elem, right) {
        this.left = left;
        this.elem = elem;
        this.right = right;
    }
}

function alloc_full_tree(depth) {
    let ix = 0;
    function go(remaining_depth) {
        if (remaining_depth == 0) {
            return null;
        } else {
            let l = go(remaining_depth - 1);
            let e = ix++;
            let r = go(remaining_depth - 1);
            return new Tree(l, e, r);
        }
    }
    return go(depth);
}

function sum_tree(tree) {
    if (tree === null) {
        return 0;
    } else {
        let l = sum_tree(tree.left);
        let r = sum_tree(tree.right);
        return l + tree.elem + r;
    }
}

printHeader();

let size = parseInt(process.env.BENCH_SIZE);
let depth = parseInt(process.env.BENCH_DEPTH);
let n_iters = parseInt(process.env.BENCH_ITER);

measure(function() {
    Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 100);
}, "100ms", 10);

measure(function() { return sum(size); }, "sum", n_iters);

measure(function() { return alloc_vector(size); }, "alloc_vector", n_iters);
let vec = alloc_vector(size);
measure(function() { return sum_vector(vec); }, "sum_vector", n_iters);

measure(function() { return alloc_list(size); }, "alloc_list", n_iters);
let list = alloc_list(size);
measure(function() { return sum_list(list); }, "sum_list", n_iters);

measure(function() { return alloc_full_tree(depth); }, "alloc_full_tree", n_iters);
let tree = alloc_full_tree(depth);
measure(function() { return sum_tree(tree); }, "sum_tree", n_iters);

