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

exports.sum = sum;
exports.alloc_vector = alloc_vector;
exports.alloc_list = alloc_list;
exports.alloc_full_tree = alloc_full_tree;
exports.sum_vector = sum_vector;
exports.sum_list = sum_list;
exports.sum_tree = sum_tree;
