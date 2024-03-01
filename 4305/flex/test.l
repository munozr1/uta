IF [if]
WHILE [while]
FOR [for]
STRINGLITERAL ["^\n""]*
INTEGERLITERAL [0-9]+
ID [_a-zA-Z][_a-zA-Z0-9]*
IGNORE [\t\n\r]
%%
DIGIT {
    printf("INTEGER");
}

IF { 
    printf("IF");
}

STRINGLITERAL {
    printf("STRING LITERAL");    
}

ID {
    printf("ID");
}

