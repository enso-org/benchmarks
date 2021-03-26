#include <chrono>
#include <functional>
#include <iostream>
#include <string>
#include <thread>

void print_header() {
  std::cout << "language, benchmark, run_id, time" << std::endl;
}

void measure(std::function<void(void)> act, std::string label, int num_iters) {
  for (int it_num = 0; it_num < num_iters; it_num++) {
    auto x1 = std::chrono::steady_clock::now();
    act();
    auto x2 = std::chrono::steady_clock::now();
    std::chrono::nanoseconds diff = x2 - x1;
    double millis = diff.count() / 1000000.0;
    std::cout << "cpp, " << label << ", " << it_num << ", " << millis << std::endl;
  }
}

int sum(int n) {
  int acc = 0;
  int i = 1;
  while (i <= n) {
    acc += i;
    i++;
  }
  return acc;
}

int* alloc_vector(int n) {
  int *res = new int[n];
  for (int i = 0; i < n; ++i) {
    res[i] = i;
  }
  return res;
}

int sum_vector(const int *vec, size_t size) {
  int acc = 0;
  for (size_t i = 0; i < size; ++i) {
    acc += vec[i];
  }
  return acc;
}

struct Cons {
  int head;
  Cons *tail;
};

Cons* alloc_list(int n) {
  Cons *res = nullptr;
  for (int i = n; i >= 0; --i) {
    res = new Cons{i, res};
  }
  return res;
}

int sum_list(Cons *list) {
  int acc = 0;
  while (list != nullptr) {
    acc += list->head;
    list = list->tail;
  }
}

struct Tree {
  Tree *left;
  int elem;
  Tree *right;
};

Tree* allocate_helper(int& ix, int remaining_depth) {
  if (remaining_depth == 0) {
    return nullptr;
  } else {
    auto l = allocate_helper(ix, remaining_depth - 1);
    int e = ix++;
    auto r = allocate_helper(ix, remaining_depth - 1);
    return new Tree{l, e, r};
  }
}

Tree* alloc_full_tree(int depth) {
  int ix = 0;
  return allocate_helper(ix, depth);
}

int sum_tree(Tree* tree) {
  if (tree == nullptr) {
    return 0;
  } else {
    int l = sum_tree(tree->left);
    int r = sum_tree(tree->right);
    return l + tree->elem + r;
  }
}

int main() {
  print_header();
  // size = 100000000
  // depth = 25
  // n_iters = 10
  int size = 100; //TODO FIXME temporary workaround to quickly test
  int depth = 4;
  int n_iters = 2;

  measure([]() { std::this_thread::sleep_for(std::chrono::milliseconds(100)); }, "100ms", 10);

  measure([&]() { sum(size); }, "sum", n_iters);

  measure([&]() { alloc_vector(size); }, "alloc_vector", n_iters);
  auto vec = alloc_vector(size);
  measure([&]() { sum_vector(vec, size); }, "sum_vector", n_iters);

  measure([&]() { alloc_list(size); }, "alloc_list", n_iters);
  auto list = alloc_list(size);
  measure([&]() { sum_list(list); }, "sum_list", n_iters);

  measure([&]() { alloc_full_tree(depth); }, "alloc_tree", n_iters);
  auto tree = alloc_full_tree(depth);
  measure([&]() { sum_tree(tree); }, "sum_tree", n_iters);
}
