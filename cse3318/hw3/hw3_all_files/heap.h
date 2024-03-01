
#ifndef HEAP_H
#define	HEAP_H

#define DEFAULT_ITEM -999

// max-heap operations

void print_array(int arr[], int N, int cap);

// assumes arr was dynamically allocated.
void array2heap(int arr[], int N, int cap, int print_on); // makes a max-heap from arr. Asssumes both size and capacity are N.

void sink_down(int i, int arr[], int N, int print_on);
void swim_up(int idx, int arr[], int print_on);

/*
    Inserts new_item in array arr. It assumes arr is a heap.
    If array needs to resize, it returns the new array pointer.
    if resize is not needed it returns the current array pointer.
*/
int* add(int new_item, int arr[], int *N, int *cap, int print_on);// will resize the heap if needed

/*
    Removes the max value from arr. It assumes arr is a heap.
*/
int remove_top(int arr[], int *N, int cap, int print_on);

// sorts array arr. Assumes arr is valid.
void heap_sort(int arr[], int N, int print_on);

/*
  Solution for the Last Stone Weight problem.
*/
int last_stone(int arr[], int N, int print_on);

#endif	/* HEAP_H */
