let helpers = require('./helpers.js');

helpers.bench(
    function() {
        Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 10);
    },
    "10ms"
);
