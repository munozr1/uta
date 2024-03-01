#include <stdio.h>
#include <string.h>
#include <stdlib.h>




typedef struct struct_node * nodePT;
struct struct_node{
  int data;
  nodePT next;
};

// struct_graph* is used to hide the implementation
typedef struct struct_graph * graphPT;
struct struct_graph{
  int undirected;
  int N;
  nodePT * E; // array of linked lists
};

int edgeExists(graphPT g, int v1, int v2) {
  nodePT n = NULL;
  for(n=g->E[v1]; n!=NULL; n=n->next)
    if (n->data == v2) return 1;
  return 0;
}
void addEdge(graphPT g, int v1, int v2){
  if (edgeExists(g, v1, v2)) return;
  g->E[v1] = insert_node(g->E[v1], NULL, new_node(v2, NULL)); // insert at front
  if ((v1 != v2) && (g->undirected == 1))
    g->E[v2]=insert_node(g->E[v2], NULL, new_node(v1, NULL)); //insert at front
}

void free_list(nodePT head)
{
  while (head != NULL)
  {
    nodePT *temp = head;
    head = head->next;
    free(temp);
  }
}

void print_list(nodePT head)
{
  nodePT ptr = head;
  // start from the beginning
  printf("\n");
  while (ptr != NULL)
  {
    print_nodePT(ptr);
    ptr = ptr->next;
  }
  printf("\n");
}

void free_AList(nodePT *Alist, int size)
{
  int i = 0;
  // print_nodePT(Alist[0]);
  for (i = 0; i < size; i++)
  {
    // print_list(Alist[i]);
    free_list(Alist[i]);
  }
  free(Alist);
}



int main(){
  // Adjacency List
  graphPT graph = (graphPT)malloc(sizeof(graphPT));
  graph->E = (nodePT *)malloc(sizeof(nodePT *));
  // TODO Read in file
  FILE *file = fopen("slides.txt", "r");
  if (file == NULL)
  {
    printf("Unable to open file");
    fclose(file);
    exit(EXIT_FAILURE);
  }
  // parse each line and tokenize
  char *token = NULL;
  char line[2000];
  int course_count = 0;

  while (fgets(line, sizeof(line), file)){
    printf("%s", line);
  }
