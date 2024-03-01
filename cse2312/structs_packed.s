// structs_packed.s

.global getName 
.global getZip

.text

// char* getName(const BUSINESS business[], uint32_t index)
// address of business[0].taxId in R0, index in R1 -> return char* in R0
getName:
    ADD R0, R0, #4      // R0 = &business[0].name
    MOV R2, #115        // R2 = sizeof(BUSINESS) when packed    
    MLA R0, R1, R2, R0  // R0 += index*sizeof(BUSINESS) or business[]+= index
    BX LR

// uint32_t getZip(const BUSINESS business[], uint32_t index)
// address of business[0].taxId in R0, index in R1 -? return zip code in R0
getZip:
    ADD R0, R0, #111     // R0 = &business[0].zip
    MOV R2, #115         // R2 = sizeof(BUSINESS) when packed    
    MLA R0, R1, R2, R0   // R0 += index*sizeof(BUSINESS) or business[]+= index
    LDR R0, [R0]         // R0 = *R0 = *business[index].zip
    BX LR
