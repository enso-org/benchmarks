import time, os
from implementations import *

if __name__ == "__main__":
    print_header()
    n_iters = 20
    iter_size = 10

    ten_million = 10000000
    million = 1000000
    tree_depth = 17

    measure(lambda: time.sleep(0.01), "10ms", iter_size, n_iters)

    measure(lambda: sum_bench(ten_million), "sum", iter_size, n_iters)

    measure(lambda: alloc_vector(million), "alloc_vector", iter_size, n_iters)
    vec = alloc_vector(million)
    measure(lambda: sum_vector(vec), "sum_vector", iter_size, n_iters)

    measure(lambda: alloc_list(million), "alloc_list", iter_size, n_iters)
    lst = alloc_list(million)
    measure(lambda: sum_list(lst), "sum_list", iter_size, n_iters)

    measure(lambda: alloc_full_tree(tree_depth), "alloc_full_tree", iter_size, n_iters)
    tree = alloc_full_tree(tree_depth)
    measure(lambda: sum_tree(tree), "sum_tree", iter_size, n_iters)
