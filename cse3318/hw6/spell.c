#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "spell.h"
#include "limits.h"

/*  Suggestions
- When you work with 2D arrays, be careful. Either manage the memory yourself, or
work with local 2D arrays. Note C99 allows parameters as array sizes as long as 
they are declared before the array in the parameter list. See: 
https://www.geeksforgeeks.org/pass-2d-array-parameter-c/

Worst case time complexity to compute the edit distance from T test words
 to D dictionary words where all words have length MAX_LEN:
Student answer:  Theta(T*D)


Worst case to do an unsuccessful binary search in a dictionary with D words, when 
all dictionary words and the searched word have length MAX_LEN 
Student answer:  Theta(log(D))
*/


/* You can write helper functions here */

char * lookup(char** dict, char* word, int low , int high){
	int mid = 0;
	int compared = 0;
	char lowercased[strlen(word)];
	strcpy(lowercased, word);
	for(int i = 0; lowercased[i]; i++){
		lowercased[i] = tolower(lowercased[i]);
	}
	while(low != high){
		mid = (low + high)/2;
		compared++;
		int test = strcmp(lowercased, dict[mid]);
		if (test == 0){
			printf("  (words compared when searching: %d)", compared);
			return dict[mid];
		}
		else if (test > 0){ 
			low = mid + 1;
		}
		else{
			high = mid - 1;
		}
	}

	if(strcmp(dict[low], word) == 0){
		printf("  (words compared when searching: %d)", compared);
		return dict[low];
	}
	if(strcmp(dict[high], word) == 0){
		printf("  (words compared when searching: %d)", compared);
		return dict[low];
	}
	printf("  (words compared when searching: %d final: %s)", compared, dict[mid]);
	return NULL;
}




int find_min(int num1, int num2, int num3){
	int min = -1;
	if(num1 < num2 && num1 < num3)
	{
		min = num1;
	}
	else if(num2 < num3)
	{
		min = num2;
	}
	else
	{
		min = num3;
	}
	return min;
}

void add_char(char *dest, char end) {
    int len = strlen(dest);
    // for the check i still assume dest tto contain a valid '\0' terminated string, so len will be smaller than size
    memset( dest+len, end, 1);   
    dest[len + 1] = '\0';
}

void print_edit_distance_tablec(int **table, int M , int N){
		int i = 0;
		int j = 0;
		printf("\n\n");
		for(i = 0; i < M+1; i++){
				for(j = 0; j < N+1; j++){
						printf("\t%c", table[i][j]);
				}
				printf("\n");
				printf("\n");
		}

}

void print_edit_distance_table(int **table, int M , int N, char* str1, char* str2){
		int i = 0;
		int j = 0;
		printf("\n\n");
		for(i = 0; i < N; i++){
			if(i == 0){
				printf("   |   |  %c", str2[i]);
			}
			else{
				printf("|%3c", str2[i]);
			}
		}
		printf("\n");
		for(i = 0; i < N; i++){
			printf("-----");
		}
		// printf("\n");
		printf("-------\n");



		for(i = 0; i < M+1; i++){
				if(i !=0){
					printf("%3c|", str1[i-1]);
				}
				else{
						printf("   |");
				}
				for(j = 0; j < N+1; j++){
					// if(j ==0){
					// 	printf("    |");
					// }
					printf("%3d|",table[i][j]);
				}
				printf("\n");
				for(j = 0; j < N+1; j++){
					printf("-----");
					if(j == N){
						printf("--");
					}
				}
				printf("\n");
		}

}


/*
Parameters:
first_string - pointer to the first string (displayed vertical in the table)
second_string - pointer to the second string (displayed horizontal in the table)
print_table - If 1, the table with the calculations for the edit distance will be printed.
              If 0 (or any value other than 1)it will not print the table
(See sample run)
Return:  the value of the edit distance (e.g. 3)
*/
int edit_distance(char * first_string, char * second_string, int print_table){
	int M = strlen(first_string);
	int N = strlen(second_string);
	// b = 2D array of M+1 rows, N+1 columns
	// int ** b = calloc(M+1, sizeof(int *));
	// for(int i=0; i< M+1; i++) b[i] = calloc(N+1, sizeof(int));

	// c = 2D array of M+1 rows, N+1 columns
	int ** c = (int**)calloc(M+1, sizeof(int*));
	for(int i=0; i< M+1; i++) c[i] = calloc(N+1, sizeof(int));

	//allocate 


// Calculate edit distance
	for(int i=0 ; i <= M; i++){
		for(int j=0 ; j <= N; j++){
				// printf("\nComparing: %c and %c\n", first_string[i-1], second_string[j-1]);
				if (i == 0){
        	c[i][j] = j; // Min. operations = j
				}
				else if (j == 0){
					c[i][j] = i; // Min. operations = i
				}
        else if (first_string[i - 1] == second_string[j - 1]){
					c[i][j] = c[i - 1][j - 1];
				}
				else{
					c[i][j]= 1+ find_min(c[i][j - 1], c[i - 1][j], c[i - 1][j - 1]); // Replace
				}
				
			}
	}
	//return edit distance
	int edit_distance = c[M][N];  // replace this line with your code
	if(print_table){
		print_edit_distance_table(c, M, N, first_string, second_string);
	}
	// for(int i=0; i< M+1; i++) {free(b[i]);};
	for(int i=0; i< M+1; i++) {free(c[i]);};
	// free(b);
	free(c);
	return edit_distance;
}


int qsort_compare_function( const void* string1, const void* string2){

	const char *str1 = *(const char**)string1;
 	const char *str2 = *(const char**)string2;
	
	return strcmp(str1, str2);
}

/*
Parameters:
testname - string containing the name of the file with the paragraph to spell check, includes .txt e.g. "text1.txt" 
dictname - name of file with dictionary words. Includes .txt, e.g. "dsmall.txt"
printOn - If 1 it will print EXTRA debugging/tracing information (in addition to what it prints when 0):
			 dictionary as read from the file	
			 dictionary AFTER sorting in lexicographical order
			 for every word searched in the dictionary using BINARY SEARCH shows all the "probe" words and their indices indices
			 See sample run.
	      If 0 (or anything other than 1) does not print the above information, but still prints the number of probes.
		     See sample run.
*/
void spell_check(char * testname, char * dictname, int printOn){
	// Write your code here
	FILE * dict_file;
	FILE * text_file;
	FILE * output_file;
	char line[102]; 
	char word[102]; 
	int words = 0; 
	int dict_size = 0;
	char ** dict_array = NULL;
	char ** words_array= NULL;
	int i = 0;
	int word_size = 0;
	int k = 0;

	dict_file 	= fopen(dictname, "r");
	text_file 	= fopen(testname, "r");
	output_file = fopen("out.txt", "w+");
	if(dict_file == NULL){
		printf("Failed to open dict_file: %s",dictname);
		if(output_file){
			fclose(output_file);
		}
		if(text_file){
			fclose(text_file);
		}
		exit(EXIT_FAILURE);
	}
	if(text_file == NULL){
		printf("Failed to open text_file: %s",testname);
		if(output_file){
			fclose(output_file);
		}
		if(dict_file){
			fclose(dict_file);
		}
		exit(EXIT_FAILURE);
	}

	fscanf(dict_file, "%d ", &dict_size);
	// printf("dictionary size: %d", dict_size);
	dict_array = calloc(dict_size, sizeof(char *));
	if(printOn==1){
			printf("\n\nBefore Sort: \n");
		}
	for(i = 0; i < dict_size; i++){
		// read = getline(&line, &line_length, dict_file);
		fscanf(dict_file, "%s ", line);
		word_size = strlen(line);
		dict_array[i] = calloc(word_size+1, sizeof(char));
		
		strcpy(dict_array[i], line);
		if(printOn==1){
			printf("%s\n", line);
		}
	}

	qsort(dict_array, dict_size,sizeof(char*), qsort_compare_function);

	if(printOn==1){
			printf("\n\nAfter Sort: \n");
		for(i = 0; i < dict_size; i++){
			printf("%s\n", dict_array[i]);
		}
	}


	//read in the text file
	while(fscanf(text_file,"%s",word) == 1){
		char punctuation = ' ';
		char original[105];
		strcpy(original, word);
		if(strchr(word, ',')){
			punctuation = ',';
		}
		if(strchr(word, '.')){
			punctuation = '.';
		}
		if(strchr(word, '!')){
			punctuation = '!';
		}
		if(strchr(word, '?')){
			punctuation = '?';
		}
		if(strchr(word, ';')){
			punctuation = ';';
		}
		char * token = strtok(word, ",?!.");
		//gets here succesfully
		// printf("\n--->  |%s| %zu ", token, strlen(token) );
		printf("\n --->  |%s| ", token);
		//infinite loop here ?
		for(k = 0;k< (int)strlen(token); k++){
			token[k] = tolower(token[k]);
			// printf("%d. %c\n",k, token[k]);
		}
		//never gets here

		// printf("size: %d\n", dict_size);
		char * exists = lookup(dict_array, token, 0 , dict_size);
		int smallest_distance = INT_MAX;
		char * best_word = NULL;
		int distance = 0;
		
		// printf("\texists: %s\n", exists);
		if(exists){
			printf("\n\t- OK\n");
			// printf("\n\t- putting\n");
			// if(punctuation != ' '){
			// 		add_char(word, punctuation);
			// }
			add_char(original, ' ');
			fputs(original, output_file);
		}

		if(!exists){
			char ** similar_words = calloc(1, sizeof(char*));
			int similar_words_count = 0;
			printf("\n-1 - type correction\n");
			//TODO

			// find the best word to match 
			for(k = 0; k < dict_size; k++){
				distance = edit_distance(token, dict_array[k], printOn);
				if(distance <= smallest_distance){
					smallest_distance = distance;
					best_word = dict_array[k];
				}
			}
			// find all similar words with lowest edit distance

			for(k = 0; k < dict_size; k++){
				distance = edit_distance(token, dict_array[k], printOn);
				if(distance == smallest_distance){
					similar_words_count++;
					similar_words = realloc(similar_words, (similar_words_count +1)*sizeof(*similar_words));
					similar_words[similar_words_count-1] = calloc(strlen(dict_array[k])+1, sizeof(char));
					strcpy(similar_words[similar_words_count-1], dict_array[k]);
				}
			}
	
			// print similar words with similar edit distance
			// printf("\n\t- Best Words:", smallest_distance);
			printf(" 0 - leave word as is (do not fix spelling)\n     Minimum distance: %d (computed edit distances: %d)\n     Words that give minimum distance: \n", smallest_distance, dict_size);
			for(i = 0; i < similar_words_count; i++){
				printf(" %d - %s\n",i+1 ,similar_words[i]);
			}
			int choice = 0;
			char correction[105];
			scanf("%d", &choice);
			if(choice < 0){
				scanf(" %s", correction);
				if(punctuation != ' '){
					add_char(correction, punctuation);
				}
				add_char(correction, ' ');
				fputs(correction, output_file);
			}
			else if(choice == 0){
				// if(punctuation != ' '){
				// 	add_char(, punctuation);
				// }
				add_char(original, ' ');
				fputs(original, output_file);
			}
			else if(choice > 0 && choice <= dict_size){
				if(punctuation != ' '){
					add_char(correction, punctuation);
				}
				add_char(similar_words[choice-1], ' ');
				fputs(similar_words[choice-1], output_file);
			}

			for(i = 0; i < similar_words_count; i++){
				free(similar_words[i]);
			}
			free(similar_words);
		}
		
	}



	fclose(dict_file);
	fclose(text_file);
	fclose(output_file);
	for(i = 0; i < dict_size; i++){
		free(dict_array[i]);
	}
	free(dict_array);
	exit(EXIT_SUCCESS);

}
