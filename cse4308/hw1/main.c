#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define INITIAL_SIZE 10

/*
* Function: count_unique_cities
* ----------------------------
*   Calculates the number of unique cities in a file.
*  It is used to allocate the cities array.
*  char *graph_file: a pointer to the file name
* returns: The number of unique cities in the file, or -1 if we cannot open the file.
*
*/
int count_unique_cities(const char *graph_file);
/*
 * Function: init_cities
 * ----------------------------
 *   Calculates the number of lines in a file.
 *
 *   char *graph_file: a pointer to the file name
 *   char ***cities: a pointer to the cities array
 *
 *   returns: The number of lines in the file, or -1 if we cannot open the file.
 */
int init_cities(char *graph_file, char ***cities);
/*
 * Function: initGraph
 * ----------------------------
 *   Initializes the graph from the map file.
 *
 *   char *graph_file: a pointer to the file name
 *   char ***graph: a pointer to the graph
 *   int *n: a pointer to the number of cities
 *
 *   returns: 0 if the graph was initialized successfully, or -1 if we cannot
 * open the file.
 */
int initGraph(char *graph_file, int ***graph, int lines, char **cities);
void informed_search(char *graph_file, char *origin_city,
                     char *destination_city, char *heuristic);
/*
 * Function: get_city_index
 * ----------------------------
 *   Returns the index of a city in the cities array.
 *   If the city is not found, returns -1.
 */
int get_city_index(char *city, char **cities, int lines);

int main(int argc, char **argv) {
  // print all args
  int lines = 0;
  int **graph = NULL;
  char **cities = (char **)malloc(INITIAL_SIZE * sizeof(char *));
  int err = 0;
  switch (argc) {
  case 5:
    printf("Informed Search\n");
    if (err < 0) {
      printf("switch(argc) => case 5 => Error opening file\n");
      return 1;
    }
    break;
  case 2:
    lines = count_unique_cities(argv[1]);
    printf("%d", lines);
    break;
  case 4:
    printf("Uninformed Search\n");
    lines = init_cities(argv[1], &cities);
    err = initGraph(argv[1], &graph, lines, cities);
    if (err)
      break;
  default:
    printf("Invalid number of arguments\n");
    printf("Usage: \n\t Informed Search: <map file> <origin_city> "
           "<destination_city> [heuristic]\n\t Uninformed Search: <map file> "
           "<origin_city> <destination_city>\n");
    return 1;
  }

  // free graph and cities
  for (int i = 0; i < lines; i++) {
    free(graph[i]);
    free(cities[i]);
  }
  return 0;
}

int initGraph(char *graph_file, int ***graph, int lines, char **cities) {
  FILE *fp = fopen(graph_file, "r");
  if (fp == NULL) {
    return -1;
  }

  *graph = (int **)malloc(lines * sizeof(int *));
  for (int i = 0; i < lines; i++) {
    (*graph)[i] =
        (int *)calloc(lines, sizeof(int)); // Use calloc to initialize to 0
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
        (*graph)[city2_index][city1_index] =
            distance; // Assuming undirected graph
      }
    }
  }

  fclose(fp);
  return 0;
}

int init_cities(char *graph_file, char ***cities) {
  FILE *fp = fopen(graph_file, "r");
  if (fp == NULL) {
    return -1;
  }

  int lines = 0;
  char buffer[100];
  *cities = (char **)malloc(INITIAL_SIZE * sizeof(char *));

  while (fgets(buffer, sizeof(buffer), fp) && strncmp(buffer, "END OF INPUT", 12) != 0) {
    char *token = strtok(buffer, " ");
    if (token && get_city_index(token, *cities, lines) == -1 ) {
      lines++;
    }
  }

  fclose(fp);
  return lines;
}

int count_unique_cities(const char *graph_file) {
    FILE *fp = fopen(graph_file, "r");
    if (fp == NULL) {
        return -1; // Error opening file
    }

    char **cities = NULL;
    int num_cities = 0;
    char buffer[100];

    while (fgets(buffer, 100, fp) != NULL) {
        if (strncmp(buffer, "END OF INPUT", 12) == 0) {
            break; // Stop at the end marker
        }

        char *city1 = strtok(buffer, " ");

        // Check city1 for uniqueness
        if (get_city_index(city1, cities, num_cities) == -1) {
            num_cities++;
            cities = realloc(cities, num_cities * sizeof(char *)); // Expand cities array
            cities[num_cities - 1] = strdup(city1); // Store a copy of the city name
        }

    }

    fclose(fp);

    // Free memory
    for (int i = 0; i < num_cities; i++) {
        free(cities[i]);
    }
    free(cities);

    return num_cities;
}


int get_city_index(char *city, char **cities, int lines) {
  for (int i = 0; i < lines; i++) {
    if (strcmp(city, cities[i]) == 0) {
      return i;
    }
  }
  return -1;
}
