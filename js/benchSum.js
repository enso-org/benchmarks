let helpers = require('./helpers.js');
let impl = require('./implementations.js');

helpers.bench(
    function() {
        impl.sum(helpers.arg_int());
    },
    "sum"
);
