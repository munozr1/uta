/*
  Alexandra Stefan
  Read test cases from a file and call the corresponding functions 
 */

/*
compile:
gcc -g run_test.c heap.c
run:
./a.out

Valgrind:
valgrind --leak-check=full ./a.out
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "heap.h"

int main()   {
	int N=0, cap=0, idx, item=0, new_val=0;
	int print_on =1;	
	char op[21]; // operation/action
	int *arr = NULL;

	printf("This program will create a max-heap and perform operations on it based on data from a file.\n ");

	print_on = 0;

	scanf("%s", op);
	while( op[0] != 'q' && op[0] != 'Q'  ){
		printf("\nOperation: %s\n", op);
		if ( op[0] == 'l' || op[0] == 'L'  ){  // load array
			//read the number of elements, N
			printf("load array...\n");
			scanf("%d", &N);
			cap = N;

			free(arr);
			arr = calloc(N, sizeof(int) );
			for(idx=0; idx<N; idx++) {
				scanf("%d", &arr[idx]);
			}
		}
		else if ( op[0] == 'i' || op[0] == 'I' ) { // increase and fix
			scanf("%d", &idx);
			scanf("%d", &new_val);
			arr[idx] = new_val;
			printf("increase and swim up: item at index %3d becomes %6d\n", idx, new_val);
			print_array(arr, N, cap);
			swim_up(idx, arr, print_on);
		}
		else if ( op[0] == 'd' || op[0] == 'D' ) { // decrease and fix
			scanf("%d", &idx);
			scanf("%d", &new_val);
			arr[idx] = new_val;
			printf("decrease and sink down: item at index %3d becomes %6d\n", idx, new_val);
			print_array(arr, N, cap);
			sink_down(idx, arr, N, print_on);
		}
		else if ( op[0] == 'r' || op[0] == 'R'  ) {// remove
			item = remove_top(arr, &N, cap, print_on);
			printf("removed: %6d\n", item );
		}
		else if ( op[0] == 'p' || op[0] == 'P'  ) {// turn print on/off
			scanf("%d", &print_on);
			printf("print_on = %d\n", print_on);
		}
		else if ( op[0] == 'a' || op[0] == 'A'  ) { // add
			scanf("%d", &new_val);
			printf("add:      %6d\n", new_val);
			arr = add(new_val, arr, &N, &cap, print_on);  // if arr is resized,
			                                              // add returns a new pointer for arr
		}
		else if ( op[0] == 'h' || op[0] == 'H' ) { // turn array into heap
			scanf("%d", &print_on);
			array2heap(arr, N, cap, print_on);
		}
		else if ( op[0] == 's' || op[0] == 'S'  ) { //sort with heapsort
			heap_sort(arr, N, print_on);
		}
		else if ( op[0] == 'w' || op[0] == 'W'  ){  // compute Last Stone Weight
			int weight = last_stone(arr, N, print_on);
			printf("Last stone weight is: %d\n", weight);
		}
		else {
			printf("Operation not recognized\n");
		}
		printf("\nin main:\n");
		print_array(arr, N, cap);

		scanf("%s", op);  // read next operation
	}

	free(arr);
	return 0;
}
