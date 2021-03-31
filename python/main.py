import time, os

def print_header():
    print("language,benchmark,run_id,time")

def measure(act, label, iter_size, num_iters):
    for it_num in range(num_iters):
        x1 = time.perf_counter()
        for _ in range(iter_size):
            act()
        x2 = time.perf_counter()
        diff = (x2 - x1)  * 1000.0 / iter_size
        print("python, " + label + ", " + str(it_num) + ", " + str(diff), flush=True)

def sum_bench(n):
    acc = 0
    i = 1
    while i <= n:
        acc += i
        i += 1
    return acc

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

def alloc_vector(n):
    return [Point(i, i) for i in range(n)]

def sum_vector(vec):
    acc = 0
    for p in vec:
        acc += p.x + p.y
    return acc

class Cons:
    def __init__(self, head, tail):
        self.head = head
        self.tail = tail

def alloc_list(n):
    r = None
    for i in range(n, -1, -1):
        r = Cons(Point(i, i), r)
    return r

def sum_list(lst):
    acc = 0
    while lst is not None:
        h = lst.head
        acc += h.x + h.y
        lst = lst.tail
    return acc

class Tree:
    def __init__(self, left, elem, right):
        self.left = left
        self.elem = elem
        self.right = right

def alloc_full_tree(depth):
    ix = 0
    def go(remaining_depth):
        nonlocal ix
        if remaining_depth == 0:
            return None
        else:
            l = go(remaining_depth - 1)
            e = Point(ix, ix)
            ix += 1
            r = go(remaining_depth - 1)
            return Tree(l, e, r)
    return go(depth)

def sum_tree(tree):
    if tree is None:
        return 0
    l = sum_tree(tree.left)
    e = tree.elem
    r = sum_tree(tree.right)
    return l + e.x + e.y + r

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
