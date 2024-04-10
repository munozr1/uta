#include <stdio.h>
#include <string.h>
int main() {
	char str[] = "AlmostEveryInformationSecurityLabIsMindBlowinglyAwesome";
	memset(str,'-',6);
	puts(str);
	return 0;
}
