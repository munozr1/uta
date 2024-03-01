#include <iostream>
#include <sstream>
#include <fstream>
#include <unordered_set>
#include <vector>
#include <string>
#include <algorithm>
#include <queue>
#include <unordered_map>

void print_info(struct Info &info);
void init_cities(std::string filename, std::vector<std::string> &cities);
void alloc_graph(std::vector<std::string> &cities, int ***adjacency_matrix);
void init_graph(std::string filename, std::vector<std::string> &cities, int **adjacency_matrix);
std::vector<std::string> uninformed_search(int **adjacency_matrix, std::vector<std::string> &cities, std::string start, std::string goal, struct Info &info);

struct Info {
    int nodes_popped;
    int nodes_expanded;
    int nodes_generated;
    int distance;
};

struct Node {
    std::string city;
    int distance;
    std::vector<struct Node> path;
    // Operator overload for the priority queue (not strictly needed for BFS)
    bool operator<(const Node& other) const {
    return distance > other.distance;  // Prioritize by lower distance
    }
};

class Compare {
    public:
       bool operator()(const Node& a, const Node& b){
        return a.distance > b.distance;
      }
};

int main(int argc, char *argv[]){
    std::string input_filename;
    std::string origin_city;
    std::string destination_city;
    std::string heuristic_file;
    std::vector<std::string> cities;
    int **adjacency_matrix = nullptr;
    std::vector<std::string> path;
    struct Info info = {0, 0, 0, 0};

    switch(argc){
        case 4:// uninformed search
            input_filename = argv[1];
            origin_city = argv[2];
            destination_city = argv[3];
            init_cities(input_filename, cities);
            alloc_graph(cities, &adjacency_matrix);
            init_graph(input_filename, cities, adjacency_matrix);
            //print graph
            for(int i = 0; i < cities.size(); i++){
                for(int j = 0; j < cities.size(); j++){
                    std::cout << adjacency_matrix[i][j] << " ";
                }
                std::cout << std::endl;
            }
            path = uninformed_search(adjacency_matrix, cities, origin_city, destination_city, info);
            print_info(info);
            if (!path.empty()) {
                for (const std::string& city : path) {
                    std::cout << city;
                    if (city != path.back()) {
                        std::cout << " -> ";
                    }
                }
            } else {
                std::cout << "No path exists from " << origin_city << " to " << destination_city << std::endl;
            }
            break;
        default:
            std::cout << "Usage: ./find_route <input_filename> <origin_city> <destination_city> <heuristic_file>" << std::endl;
            break;
    }

    return 0;
}

void init_cities(std::string filename, std::vector<std::string> &cities){
    std::ifstream file(filename);
    std::string line;
    while(std::getline(file, line)){
        //parse line by space
        std::string city = line.substr(0, line.find(" "));
        // check if city == "END"
        if(city == "END"){
            break;
        }
        //check if city is already in vector
        if(std::find(cities.begin(), cities.end(), city) != cities.end()){
            continue;
        }
        cities.push_back(city);
    }
    file.close();
}

void alloc_graph(std::vector<std::string> &cities, int ***adjacency_matrix) {
    int num_cities = cities.size();
    *adjacency_matrix = new int*[num_cities];
    for(int i = 0; i < num_cities; ++i) {
        (*adjacency_matrix)[i] = new int[num_cities];
        std::fill((*adjacency_matrix)[i], (*adjacency_matrix)[i] + num_cities, 0);
    }
}


void init_graph(std::string filename, std::vector<std::string> &cities, int **adjacency_matrix) {
    std::ifstream file(filename);
    std::string line;
    while(std::getline(file, line)) {
        if(line == "END OF INPUT") break; // Stop if end of file is reached

        std::istringstream iss(line);
        std::string city1, city2;
        int distance;

        // Parse the line
        iss >> city1 >> city2 >> distance;



        // Find the indices of the cities in the cities vector
        auto it1 = std::find(cities.begin(), cities.end(), city1);
        auto it2 = std::find(cities.begin(), cities.end(), city2);

        if (it1 != cities.end() && it2 != cities.end()) {
            int index1 = std::distance(cities.begin(), it1);
            int index2 = std::distance(cities.begin(), it2);

            //add distance to adjacency matrix
            adjacency_matrix[index1][index2] = distance;
            adjacency_matrix[index2][index1] = distance;
        }
    }
    file.close();
}

std::vector<std::string> uninformed_search(int **adjacency_matrix, std::vector<std::string> &cities, std::string start, std::string goal, struct Info &info) {
    std::unordered_set<std::string> closed;
    std::priority_queue<struct Node, std::vector<struct Node>, Compare> fringe;
    std::unordered_map<std::string, std::string> parent;

    // Add the start node to the fringe
    struct Node start_node = {start, 0};
    fringe.push(start_node);
    // find the index of the start node in the cities vector
    int index = std::distance(cities.begin(), std::find(cities.begin(), cities.end(), start));

    while(!fringe.empty()){
        //print the entire fringe
        // pop from the fringe and add to closed
        struct Node current = fringe.top();
        fringe.pop();
        closed.insert(current.city);
        info.nodes_popped++;
        std::cout << "Current node: " << current.city << " | Distance: " << current.distance << " | Fringe: ";
        std::priority_queue<struct Node, std::vector<struct Node>, Compare> temp = fringe;
        while(!temp.empty()){
            std::cout << temp.top().city << " " << temp.top().distance << " | ";
            temp.pop();
        }
        std::cout << std::endl;

        // check if the current node is the goal
        if(current.city == goal){
            std::vector<std::string> path;
            // std::string current_city = goal;
            // while(current_city != start){
            //     path.push_back(current_city);
            //     current_city = parent[current_city];
            // }

            // add the current nodes path to the path vector using a for loop
            for(int i = 0; i < current.path.size(); i++){
                path.push_back(current.path[i].city);
            }
            std::reverse(path.begin(), path.end());
            info.distance = current.distance;
            return path;
        }

        // find the index of the current node in the cities vector
        int index = std::distance(cities.begin(), std::find(cities.begin(), cities.end(), current.city));

        // iterate through the neighbors of the current node
        for(int i = 0; i < cities.size(); i++){
            if(adjacency_matrix[index][i] != 0){
                //print the neighbors
                std::cout <<  cities[i] << " " << adjacency_matrix[index][i] <<" | ";
                // check if the neighbor is in the closed set
                if(closed.find(cities[i]) == closed.end()){
                    // add the neighbor to the fringe
                    struct Node neighbor = {cities[i], current.distance + adjacency_matrix[index][i]};
                    // add the current nodes path to the neighbors path
                    neighbor.path = current.path;
                    fringe.push(neighbor);
                    info.nodes_generated++;
                    parent[cities[i]] = current.city;
                }
            }
        }
        std::cout <<std::endl;

        info.nodes_expanded++;
    }

    return {};
}

void print_info(struct Info &info) {
    std::cout << "nodes_popped: " << info.nodes_popped << std::endl;
    std::cout << "nodes_expanded: " << info.nodes_expanded << std::endl;
    std::cout << "nodes_generated: " << info.nodes_generated << std::endl;
    std::cout << "distance: " << info.distance << std::endl;
}
