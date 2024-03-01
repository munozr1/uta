#include "hw1_student.h"  // .h extenssion, not .c
#include <limits.h>

// You do not need to change this print_1D function. It is included here for 
// your convenience, in case you need to call it while debugging.
void print_1D(int sz, int * arr){
	int k = 0;
	for(k = 0; k< sz; k++){
		printf("%8d", arr[k]);
	}
	printf("\n");
}


/* dynamically allocates an array and populates it with all values from arr 
	that are strictly smaller than thresh. 
	Returns:
		- the pointer to the dynamically allocated array.
		- NULL if all elements of arr are greater or equal to thres. In this case it does not allocate any memory, and sets content of sz_res to 0.

thresh: x < thresh to be in the new array
sz_arr: the size of the array passed in holding the values to iterate through
arr   : The start of the array to iterate through
sz_res: The size of the resulting array
*/

int* get_scores_below(int thresh, int sz_arr, int * arr, int* sz_res){
	// change code here to correct function implementation
	int new_size = 0;
	// Array with values below the thresh hold
	int * new_array = NULL;
	// iterate through sz_arr
	int i = 0;
	// iterate through new_array
	int j = 1;
	for(i = 0; i < sz_arr; i++){
		if(arr[i] < thresh){
				new_array = (int *)realloc(new_array, sizeof(int)*j);
				new_array[j-1] = arr[i];
				j++;
		}
	}
	j = j-1;
	*sz_res = j;
	return new_array;
}


void update_min_max(int num_rows, int num_cols, int* arr2D[], int* arr_min, int* arr_max){
	// write your code here
	int local_min = INT_MAX;
	int local_max = 0;
	int r,c;
	for(r = 0; r < num_rows; r++){
		for(c = 0; c < num_cols; c++){
		    if(arr2D[r][c] < local_min)
				local_min = arr2D[r][c];
			if(arr2D[r][c] > local_max)
				local_max = arr2D[r][c];
		}
	}

	*arr_min = local_min;
	*arr_max = local_max;
}


// improve this code to print as required in the homework
void print_2D(int num_rows, int num_cols, int* arr2D[]){	
	int r,c;
	printf("    |");
	for(c = 0; c< num_cols; c++){
		printf("%8d|", c);
	}
	printf("\n------------");
	for(c = 0; c< num_cols *8; c++){
		printf("-");
	}
	printf("\n");
	for (r = 0; r<num_rows; r++){
		for(c=0; c<num_cols; c++){
		    if(c == 0)
				printf("   %d|%8d|",r,arr2D[r][c]);
			else
				printf("%8d|", arr2D[r][c]);
		}
		printf("\n");
	}	
}


