let helpers = require('./helpers.js');
let impl = require('./implementations.js');

let t = impl.alloc_full_tree(helpers.arg_int());

helpers.bench(
    function() {
        impl.sum_tree(t);
    },
    "sum_tree"
);
