import time, os

def print_header():
    print("language, benchmark, run_id, time")

def measure(act, label, num_iters):
    for it_num in range(num_iters):
        x1 = time.perf_counter()
        act()
        x2 = time.perf_counter()
        diff = (x2 - x1)  * 1000.0
        print("python, " + label + ", " + str(it_num) + ", " + str(diff), flush=True)

def sum_bench(n):
    acc = 0
    i = 1
    while i <= n:
        acc += i
        i += 1
    return acc

def alloc_vector(n):
    return [i for i in range(n)]

def sum_vector(vec):
    acc = 0
    for i in vec:
        acc += i
    return acc

class Cons:
    def __init__(self, head, tail):
        self.head = head
        self.tail = tail

def alloc_list(n):
    r = None
    for i in range(n, -1, -1):
        r = Cons(i, r)
    return r

def sum_list(lst):
    acc = 0
    while lst is not None:
        acc += lst.head
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
            e = ix
            ix += 1
            r = go(remaining_depth - 1)
            return Tree(l, e, r)
    return go(depth)

def sum_tree(tree):
    if tree is None:
        return 0
    l = sum_tree(tree.left)
    r = sum_tree(tree.right)
    return l + tree.elem + r

if __name__ == "__main__":
    print_header()
    size = int(os.getenv("BENCH_SIZE"))
    depth = int(os.getenv("BENCH_DEPTH"))
    n_iters = int(os.getenv("BENCH_ITER"))

    measure(lambda: time.sleep(0.1), "100ms", 10)

    measure(lambda: sum_bench(size), "sum", n_iters)

    measure(lambda: alloc_vector(size), "alloc_vector", n_iters)
    vec = alloc_vector(size)
    measure(lambda: sum_vector(vec), "sum_vector", n_iters)

    measure(lambda: alloc_list(size), "alloc_list", n_iters)
    lst = alloc_list(size)
    measure(lambda: sum_list(lst), "sum_list", n_iters)

    measure(lambda: alloc_full_tree(depth), "alloc_full_tree", n_iters)
    tree = alloc_full_tree(depth)
    measure(lambda: sum_tree(tree), "sum_tree", n_iters)
