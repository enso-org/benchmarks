let helpers = require('./helpers.js');
let impl = require('./implementations.js');

let l = impl.alloc_list(helpers.arg_int());

helpers.bench(
    function() {
        impl.sum_list(l);
    },
    "sum_list"
);
