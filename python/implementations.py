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

ten_million = 10000000
million = 1000000
tree_depth = 17
