#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int get_city_index(char *city, char **cities, int lines);

/*
 * Function: get_lines
 * ----------------------------
 *   Calculates the number of unique cities in a file.
 *
 *   char *graph_file: a pointer to the file name
 *   char ***cities: a pointer to the pointer of cities array
 *
 *   returns: The number of unique cities in the file, or -1 if we cannot open the file.
*/
int get_lines(char *graph_file, char ***cities);


/*
* Function: initGraph
* ----------------------------
*   Initializes the graph from the map file.
*
*   char *graph_file: a pointer to the file name
*   int **graph: a pointer to the graph
*   int lines: the number of cities
*   char **cities: the cities array
*
*   returns: 0 if the graph was initialized successfully, or -1 if we cannot open the file.
*/
int initGraph(char *graph_file, int ***graph, int lines, char **cities) ;


int get_city_index(char *city, char **cities, int lines) ;


int main(int argc, char **argv) {
    if (argc < 2) {
        printf("Usage: <program> <graph file>\n");
        return 1;
    }

    char **cities = NULL;
    int lines = get_lines(argv[1], &cities);
    if (lines < 0) {
        printf("Error opening file.\n");
        return 1;
    }

    int **graph = NULL;
    if (initGraph(argv[1], &graph, lines, cities) < 0) {
        printf("Error initializing graph.\n");
        // Free cities
        for (int i = 0; i < lines; i++) {
            free(cities[i]);
        }
        free(cities);
        return 1;
    }

    // Example usage

    // Cleanup
    for (int i = 0; i < lines; i++) {
        free(graph[i]);
        free(cities[i]);
    }
    free(graph);
    free(cities);

    return 0;
}

int get_lines(char *graph_file, char ***cities) {
    FILE *fp = fopen(graph_file, "r");
    if (fp == NULL) {
        return -1;
    }

    int lines = 0;
    char buffer[100];
    *cities = NULL;

    while (fgets(buffer, sizeof(buffer), fp)) {
        char *token = strtok(buffer, " ");
        if (token && get_city_index(token, *cities, lines) == -1) {
            lines++;
            *cities = realloc(*cities, lines * sizeof(char *));
            (*cities)[lines - 1] = (char *)malloc(strlen(token) + 1);
            strcpy((*cities)[lines - 1], token);
        }
        // Skip the rest of the line if necessary
        token = strtok(NULL, "\n");
    }

    fclose(fp);
    return lines;
}

int initGraph(char *graph_file, int ***graph, int lines, char **cities) {
    FILE *fp = fopen(graph_file, "r");
    if (fp == NULL) {
        return -1;
    }

    *graph = (int **)malloc(lines * sizeof(int *));
    for (int i = 0; i < lines; i++) {
        (*graph)[i] = (int *)calloc(lines, sizeof(int)); // Use calloc to initialize to 0
    }

    char buffer[255];
    while (fgets(buffer, sizeof(buffer), fp)) {
        char city1[100], city2[100];
        int distance;
        if (sscanf(buffer, "%s %s %d", city1, city2, &distance) == 3) {
            int city1_index = get_city_index(city1, cities, lines);
            int city2_index = get_city_index(city2, cities, lines);
            if (city1_index != -1 && city2_index != -1) {
                (*graph)[city1_index][city2_index] = distance;
                (*graph)[city2_index][city1_index] = distance; // Assuming undirected graph
            }
        }
    }

    fclose(fp);
    return 0;
}

int get_city_index(char *city, char **cities, int lines) {
    for (int i = 0; i < lines; i++) {
        if (strcmp(city, cities[i]) == 0) {
            return i;
        }
    }
    return -1;
}
