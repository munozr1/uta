// Toolchain:
// Default align: gcc -o structs structs.c structs_aligned.s
// Packed:        gcc -o structs structs.c structs_packed.s -D PACK

#include <stdio.h>
#include <stdint.h>
#include <string.h>

#define MAX_NAME_LENGTH 32
#ifdef PACK
#pragma pack(push)
#pragma pack(1)
#endif

typedef struct _BUSINESS
{
    uint32_t taxId; // 4 bytes -> This address will be stored in R0
    char name[MAX_NAME_LENGTH+1]; // 33 bytes
    uint32_t addNo; // 4 bytes
    char direction; // 1 byte
    char street[MAX_NAME_LENGTH+1]; // 33 bytes
    char city[MAX_NAME_LENGTH+1]; // 33 bytes
    char state[2+1];// 3 bytes
    uint32_t zip; // 4 bytes
} BUSINESS;

// Unaligned 115 bytes
// Aligned 120 bytes

#ifdef PACK
#pragma pack(pop)
#endif

extern char* getName(const BUSINESS business[], uint32_t index);
extern uint32_t getZip(const BUSINESS business[], uint32_t index);

#define COUNT 3

int main()
{
    uint32_t i;
    BUSINESS business[COUNT] = {
        {12342332, "Home Depot", 201, 'W', "Road to Six Flags", "Arlington", "TX", 76011},
        {18091123, "Kroger", 945, 'W', "Lamar Blvd", "Arlington", "TX", 76012},
        {81927322, "Round1", 3811, 'S', "Cooper St", "Arlington", "TX", 76015} };
    printf("Addresses of selected record entries:\r\n");
    char format[] = {"%p %s\r\n"};
    printf(format, &business[0], "business[0]");
    printf(format, &business[0].taxId, "taxId");
    printf(format, &business[0].name, "name");
    printf(format, &business[0].addNo, "addNo");
    printf(format, &business[0].direction, "direction");
    printf(format, &business[0].street, "street");
    printf(format, &business[0].city, "city");
    printf(format, &business[0].state, "state");
    printf(format, &business[0].zip, "zip");
    printf(format, &business[1], "business[1]");
    printf(format, &business[2], "business[2]");
    printf("sizeof(BUSINESS) = %u\n", (uint32_t)sizeof(BUSINESS));
    printf("\r\n");

    printf("Record number: ");
    scanf("%u", &i); 
    
    printf("Name = %s\r\n", getName(business, i));
    printf("ZIP  = %u\r\n", getZip(business, i));
    return 0;
}


