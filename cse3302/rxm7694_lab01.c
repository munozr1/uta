//1) Was one language easier or faster to write the code for this? If so, describe in detail why, as in what about the language made that the case.
//Java was the easiest to write the code for.
//This is because java offers much more abstraction between lower level tasks,
//such as memory allocation, over languages like C.
////Perl was also simple but took me way longer because I
///had to look up examples and tutorials over the language since it was my first time using it.
///Javas object ideology also helped because there were already built in functions. For example,
//getting the size of the files was easy even though i've never done it because i just looked 
//through the built in function suggestions in my text editor. Im not sure how to describe perl
//since it used flags to check if the file was a file or directory and even to get its length.

//2) Even though a language may not (e.g. FORTRAN) support recursion, describe how you could write a program to produce the same results without using recursion. Would that approach have any limitations and if so, what would they be?
//To simulate recursion you can replace the recursive call with a while loop.
//This approach would actually might be better since there is no recursive stack the computer has to store.


//Rodrigo Munoz
//ID: 1001847694
//macos 13.3.1 (a) (22E772610a)
//Apple clang version 14.0.3 (clang-1403.0.22.14.1)
//Target: x86_64-apple-darwin22.4.0
//Thread model: posix

#include "stdio.h"
#include "string.h"
#include "stdlib.h"
#include "dirent.h"
#include <stdio.h>
#include <sys/dirent.h>


int fileSize(const char * path);
int folderSize(const char * path);

int main (int argc, char *argv[])
{
    long long int size = folderSize(".");
    if(size == -1){
        return 1;
    }

    printf("Size of %s = %llu \n", ".", size);
    return 0;
}

//Recursive function to crawl the entire folder structure
int folderSize(const char * path){
    long long int size = 0;
    DIR *folder;
    /*
     #define DT_UNKNOWN       0
     #define DT_FIFO          1
     #define DT_CHR           2
     #define DT_DIR           4
     #define DT_BLK           6
     #define DT_REG           8
     #define DT_LNK          10
     #define DT_SOCK         12
     #define DT_WHT          14
    */
    struct dirent * dir;
    folder = opendir(path);
    //Err handling
    if(folder == NULL){
        printf("Opening %s failed\n", path);
        return -1;
    }

    dir = readdir(folder);
    // check if the type of the file is a directory or regular file and igonre every other type
    while (dir != NULL){
        char * newPath = (char*)malloc(1 + sizeof(path) + sizeof(dir->d_name));
        strcpy(newPath,path);
        strcat(newPath, "/");
        strcat(newPath, dir->d_name);
        
        // if dir pointer points to a regular file
        if(dir->d_type == DT_REG){
            size += fileSize(newPath);
        }
        // if dir pointer points to a new directory
        else if ((dir->d_type == DT_DIR) && (strcmp(dir->d_name, ".")) && (strcmp(dir->d_name, ".."))){
            size += folderSize(newPath);
        }
        //move the dir pointer to the next file in the current directory
        dir = readdir(folder);
        free(newPath);
    }

    //Clean up
    closedir(folder);
    return size;
}

//Helper function to calculte the size of a specific file
int fileSize(const char * path){
    FILE * fp = fopen(path, "r");

    if (fp == NULL) {
        printf("File Not Found!\n");
        printf("Path: %s\n", path);
        return -1;
    }

    fseek(fp, 0L, SEEK_END);
    long int size= ftell(fp);

    fclose(fp);
    return size;
}
