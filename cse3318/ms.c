#include <stdio.h>




int main() {
    
}


void merge(int *a, int left, int mid, int right, int * copy){
    int li = left;
    int ri = mid +1;
    for(int ci = left; ci <= right; ci++){
        //check for ri out of bounds
        if(ri > right){
            copy[ci] = a[li];
            li++;
            ci++;
        }
        //check for li out of bounds
        else if(li > mid){
            copy[ci] = a[ri];
            ri++;
            ci++;
        }
        else if(a[li] <= a[ri]){
            copy[ci] = a[li];
            ci++;
            li++;
        }
        else{
            copy[ci] = a[ri];
            ci++;
            ri++;
        }
    }
}
