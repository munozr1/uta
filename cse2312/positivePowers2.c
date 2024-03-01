#include <stdio.h>
#include <math.h>


int main(void)
{
		for(int i = 0; i < 32; i++)
		{
				printf("2^%d = %f\n",i, pow(2, i)); 
		}
		return 0;
}
