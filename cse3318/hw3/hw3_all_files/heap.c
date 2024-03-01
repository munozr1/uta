#include <stdlib.h>
#include <stdio.h>

#include "heap.h"

void print_array(int arr[], int N, int cap){
	int i;
	if ( arr == NULL) {
		printf("array is NULL\n");
		return;
	}
	printf("Array:  size: %d, capacity : %d", N, cap);
	printf("\nindexes:      ");
	for(i=0; i<N; i++){
		printf("%6d, ", i);
	}
	printf("\nvalues:       ");
	for(i=0; i<N; i++){
		printf("%6d, ", arr[i]);
	}
	printf("\n");
}


/*
 Rearranges the array elements to be a heap using the build_heap method from class
 If print_on is 1 prints original array
 and array after each call to sink_down.
*/
void array2heap(int * arr, int N, int cap, int print_on){
	// keep as is
	if (print_on==1) {
		printf("in array2heap, array BEFORE it gets turned into a heap :\n");
		print_array(arr, N, cap);
	}

	/*
	// add your code here.
	// the if statement below should be placed immediately after the call to sink_down
	if (print_on==1) {
		printf("in array2heap, array after sink_down at index %d.\n", idx);
		print_array(arr, N, cap);
	}
	*/
	// place your code here
	printf("array2heap - to be implemented");  // delete this line
}

void swim_up(int idx, int * arr, int print_on){
    int parent = idx/2;
    while((idx>=0) && arr[idx] > arr[parent]){
        if(print_on){
            printf("swim_up: idx = %d\n", idx);
        }
        int temp = arr[parent];
        arr[parent] = arr[idx];
        arr[idx] = temp;
        idx = parent;
        parent = (idx-1)/2;
    }
}


void sink_down(int idx, int arr[], int N, int print_on){
	// keep this here
	if (print_on==1) {
		printf("sink_down: idx = %d\n", idx);
	}


	// write your code here
	printf("sink_down - to be implemented\n");  // delete this line
}

int* add(int new_item, int arr[], int *N, int *cap, int print_on){
    int * newarr;
	/*
	// The print statements used in the solution.
	// To match the expected out,
	// copy/paste each one where it belongs in your code.
	printf("\nFailed to resize. Add failed.\n");
	*/

	// place your code here
    // create new array of size N +1
    if(*cap == *N){
        printf("FULL. Resizing...............................\n");
        *cap = *cap +10;
        newarr = (int*)reallocf(arr, *cap * sizeof(int));
    }else{
        newarr = arr;
    }

    *N = *N +1;
    newarr[*N-1] = new_item;
    swim_up(*N-1, newarr, 1);

    //print_array(newarr, *N, *cap);
    

	return newarr;  // update this line as needed
}


int remove_top(int items[], int *N, int cap, int print_on){
	/*
	// The print statement used in the solution.
	// To match the expected out,
	// copy/paste it where it belongs in your code.
	printf("Empty heap. no remove performed.\n");    \\   Updated 10\06\2023: \n added here
	*/

	// place your code here
	printf("remove_top - to be implemented\n");  // delete this line

	return DEFAULT_ITEM ;  // update this
}



void heap_sort(int arr[], int N, int print_on){
	/*
	// The print statement used in the solution.
	// To match the expected out,
	// copy/paste it where it belongs in your code.
	printf("arr is NULL. Return.\n");
	*/

	// place this after [each] call to remove() in your code
	/*
	if (print_on == 1) {
		print_array(arr, N, cap);
	}
	*/

	printf("heap_sort - to be implemented\n");  // delete this line
}

int last_stone(int arr[], int N, int print_on){
	/*
	// The print statement used in the solution.
	// To match the expected out,
	// copy/paste it where it belongs in your code.
	printf("arr is NULL. Return.\n");
	*/

	// your code here
	// After you finish processing one pair of 2 stones print the message:
	// "removed: _ and _\n"  where _ is a place holder for the correct stone weights
	printf("last_stone - to be implemented\n");  // delete this line

	return 0;  // update this as needed
}
