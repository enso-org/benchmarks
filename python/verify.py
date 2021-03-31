from implementations import *

print(sum_bench(ten_million))
print(sum_vector(alloc_vector(million)))
print(sum_list(alloc_list(million)))
print(sum_tree(alloc_full_tree(tree_depth)))
