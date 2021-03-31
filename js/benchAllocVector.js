let helpers = require('./helpers.js');
let impl = require('./implementations.js');

helpers.bench(
    function() {
        impl.alloc_vector(helpers.arg_int());
    },
    "alloc_vector"
);
