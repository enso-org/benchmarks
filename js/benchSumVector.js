let helpers = require('./helpers.js');
let impl = require('./implementations.js');

let vec = impl.alloc_vector(helpers.arg_int());

helpers.bench(
    function() {
        impl.sum_vector(vec);
    },
    "sum_vector"
);
