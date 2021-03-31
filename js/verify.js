let impl = require('./implementations.js');

let ten_million = 10000000;
let million     = 1000000;
let tree_depth  = 17;

console.log(impl.sum(ten_million));
console.log(impl.sum_vector(impl.alloc_vector(million)));
console.log(impl.sum_list(impl.alloc_list(million)));
console.log(impl.sum_tree(impl.alloc_full_tree(tree_depth)));
