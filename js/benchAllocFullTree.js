let helpers = require('./helpers.js');
let impl = require('./implementations.js');

helpers.bench(
    function() {
        impl.alloc_full_tree(helpers.arg_int());
    },
    "alloc_full_tree"
);
