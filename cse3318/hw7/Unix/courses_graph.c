#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

struct course {
    char * name;
    struct course * prereq;
    int index;
};

typedef struct course course;

void print_course(course * c){
    printf("[%d] %s ", c->index,c->name);
}

void dfs(int **matrix, int rows, int cols, int row, int col, int *visit_order, int index)
{
    // mark the current cell as visited
    matrix[row][col] = 1;

    // store the index of the current cell in the visit_order array
    visit_order[index] = row * cols + col;

    // recursively call the function for all unvisited neighbors of the current cell
    if (row > 0 && matrix[row - 1][col] == 0)
        dfs(matrix, rows, cols, row - 1, col, visit_order, index + 1);
    if (row < rows - 1 && matrix[row + 1][col] == 0)
        dfs(matrix, rows, cols, row + 1, col, visit_order, index + 1);
    if (col > 0 && matrix[row][col - 1] == 0)
        dfs(matrix, rows, cols, row, col - 1, visit_order, index + 1);
    if (col < cols - 1 && matrix[row][col + 1] == 0)
        dfs(matrix, rows, cols, row, col + 1, visit_order, index + 1);
}

int **alloc_2d(int rows, int columns)
{
    int row;
    // allocate space to keep a pointer for each row
    int **table = (int **)calloc(rows, sizeof(int *));
    // VERY IMPORTANT: allocate the space for each row
    for (row = 0; row < rows; row++)
        table[row] = (int *)calloc(columns, sizeof(int));
    return table;
}

void free_2d(int **array, int rows, int columns)
{
    // VERY IMPORTANT: free the space for each row
    for (int row = 0; row < rows; row++)
        free(array[row]);
    // free the space with the pointer to each row.
    free(array);
}

void print_matrix(int** matrix, int course_count){
    int i = 0;
    int j = 0;
    for (i = 0; i < course_count; i++)
    {
        for (j = 0; j < course_count; j++)
        {
            printf("0 ");
        }
        printf("\n");
    }
}

void free_list(course * head){
    while(head != NULL)
    {
        course * temp = head;
        head = head->prereq;
        free(temp);
    }
}


void print_list(course* head)
{
    course * ptr = head;
    // start from the beginning
    printf("\n");
    while (ptr != NULL)
    {
        print_course(ptr);
        ptr = ptr->prereq;
    }
    printf("\n");
}

void free_AList(course **Alist, int size)
{
    int i = 0;
    // print_course(Alist[0]);
    for (i = 0; i < size; i++)
    {
        // print_list(Alist[i]);
        free_list(Alist[i]);
    }
    free(Alist);
}
void print_AList(course **Alist, int size)
{
    int i = 0;
    // print_course(Alist[0]);
    for (i = 0; i < size; i++)
    {
        print_list(Alist[i]);
        // free_list(Alist[i]);
    }
    // free(Alist);
}

void free_AMatrix(int **Alist, int size)
{
    int i = 0;
    // print_course(Alist[0]);
    for (i = 0; i < size; i++){
        free(Alist[i]);
    }
    free(Alist);
}

void sortedInsert(course **head,
                  course *new_course)
{
    course *current;
    /* Special case for the head end */
    if (*head == NULL || (strcmp((*head)->name,new_course->name) >= 0))
    {
        new_course->prereq = *head;
        *head = new_course;
    }
    else
    {
        /* Locate the node before the point of insertion */
        current = *head;
        while (current->prereq != NULL && (strcmp(current->prereq->name ,new_course->name) < 0))
        {
            current = current->prereq;
        }
        new_course->prereq = current->prereq;
        current->prereq = new_course;
    }
}



int main(){

    //Adjacency List
    char ** AList = malloc(sizeof(char*));
    char filename[100];
    printf("This program will read, from a file, a list of courses and their prerequisites and will print the list in which to take cousres.\n");
    printf("Enter filename: ");
    scanf("%s", filename);
    // printf("filename: %s\n",filename );

    //TODO Read in file
    FILE * file = fopen(filename,"r");
    if(file == NULL)
    {
        printf("Could not open file %s\n\n", filename);
        printf("Failed to read from file. Program will terminate");
        fclose(file);
		exit(EXIT_FAILURE);
    }
    //parse each line and tokenize
    char * token = NULL;
    char line[2000];
    int course_count = 0;
    while(fgets(line, sizeof(line), file))
    {
        course_count++;
        AList =  realloc(AList, sizeof(char*)* course_count);
        // remove new line
        char *pos;
        if ((pos = strchr(line, '\n')) != NULL)
            *pos = '\0';

        //tokenize
        char *token = NULL;
        token = strtok(line, " ");
        char name[35];
        strcpy(name, token);
        AList[course_count-1] = malloc(sizeof(char)*35);
        strcpy(AList[course_count-1], name);
        // printf("%s ", name);
        //insert head here
        while(token != NULL){
            token = strtok(NULL, " ");
            if(token != NULL){
                strcpy(name, token);
                //insert new nodes here
                // printf("%s ", name);
            }
        }
    }
    fclose(file);
    file = fopen(filename, "r");

    int i = 0;
    printf("Number of vertices in built graph:  N = %d\n", course_count);
    printf("coursename correspondence \n");

    for (i = 0; i < course_count; i++)
    {
        printf("%d - %s \n", i,AList[i]);
    }


    int ** AMatrix = alloc_2d(course_count, course_count);

    printf("\n");
    int index = 0;
    while(fgets(line, sizeof(line), file)){
        //remove newline
        char *pos;
        if ((pos = strchr(line, '\n')) != NULL)
            *pos = '\0';

        //tokenize
        char * token = NULL;
        //throw away first token
        token = strtok(line, " ");

        while(token != NULL){
            token = strtok(NULL, " ");
            for(i = 0 ; i < course_count; i++){
                // printf("\n%d", i);
                // printf("%s", token);
                // int res = ()
                if(token != NULL && (strcmp(token, AList[i]) == 0)){   
                    AMatrix[i][index] = 1;
                    // printf("%d", index);
                }
            }
        }
        // printf("%s", token);
        index++;
    }

    // int i = 0;
    int j = 0;
    int k = 0;
    
    // print_AList(&AList, course_count);
    // printf("%d", AMatrix[0][0]);

    printf("\nAdjacency matrix: \n");

    printf("  |");
    for(i = 0; i < course_count; i++){
        printf("%4d ", i);
    }
    printf("\n");
    printf("--");
    for(i = 0; i < course_count; i++){
        printf("-----");
    }
    printf("\n");
    for(i = 0; i < course_count; i++){
        for(j = 0; j < course_count; j++){
            if(j == 0)
                printf("%d| ", i);
            printf("%4d ", AMatrix[i][j]);
        }
        printf("\n");
    }
    //do topological sort

    printf("Order in which to take the classes: \n");
    int count = 0;
    int * visited = malloc(sizeof(int) * course_count);
    dfs(AMatrix, course_count, course_count, 0, 0, visited, 0);
    printf("\n\n");

    for(i = 0; i < course_count; i++){
        printf("%d\n", visited[i]);
    }

    // free_AList(AList, course_count);
    free_AMatrix(AMatrix, course_count);
    for(i = 0; i < course_count; i++){
        free(AList[i]);
        // free(visited[i]);
        // free(finished[i]);
    }
    free(AList);
    free(visited);
    fclose(file);
}
