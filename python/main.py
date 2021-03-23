import time

def print_header():
    print("language, benchmark, run_id, time")

def measure(act, label, iter_size, num_iters):
    for it_num in range(num_iters):
        iters = []
        for i in range(iter_size):
            x1 = time.perf_counter()
            act()
            x2 = time.perf_counter()
            iters.append((x2 - x1)  * 1000.0)
        avg = sum(iters) / len(iters)
        print("python, " + label + ", " + str(it_num) + ", " + str(avg))

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
    r = Nil()
    for i in range(n, -1, -1):
        r = List(i, r)
    return r

def sum_list(lst):
    pass

if __name__ == "__main__":
    print_header()
    # size = 100000000
    # depth = 25
    # iter_size = 100
    # n_iters = 10
    size = 100 # TODO FIXME temporary workaround to quickly test
    depth = 4
    iter_size = 2
    n_iters = 2

    measure(lambda: time.sleep(0.1), "100ms", 1, 1)

    measure(lambda: sum_bench(size), "sum", iter_size, n_iters)

    measure(lambda: alloc_vector(size), "alloc_vector", iter_size, n_iters)
    vec = alloc_vector(size)
    measure(lambda: sum_vector(vec), "sum_vector", iter_size, n_iters)
