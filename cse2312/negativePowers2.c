#include <stdio.h>
#include <math.h>


int main(void)
{
		for(int i = 0; i < 32; i++)
		{
				printf("2^%d = %.32lf\n",i, pow((double)2, (double)-i)); 
		}
		return 0;
}
