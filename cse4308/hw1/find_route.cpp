#include <iostream>
#include <sstream>
#include <fstream>
#include <unordered_set>
#include <vector>
#include <string>
#include <algorithm>
#include <queue>
#include <unordered_map>

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
    int heuristic;
    bool operator==(const Node& other) const {
        return city == other.city;
    }
};



class Compare {
public:
    bool operator()(const Node& n1, const Node& n2) {
        return n1.distance > n2.distance;
    }
};

struct NodeComparator {

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
            path = uninformed_search(adjacency_matrix, cities, origin_city, destination_city, info);
            if (!path.empty()) {
                for (const std::string& city : path) {
                    std::cout << city;
                    if (city != path.back()) {
                        std::cout << " -> ";
                    }
                }
                std::cout << std::endl;
                std::cout<< "nodes_popped: " << info.nodes_popped << std::endl;
                std::cout<< "nodes_expanded: " << info.nodes_expanded << std::endl;
                std::cout << "nodes_generated: " << info.nodes_generated << std::endl;
                std::cout << "distance: " << info.distance << std::endl;
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
    std::unordered_set<struct Node> closed;
    std::priority_queue<struct Node, std::vector<struct Node>,Compare> fringe;

    struct Node start_node = {start, 0, 0};

    fringe.push(start_node);

    while (true){
        // check if fringe is empty
        if (fringe.empty()) return std::vector<std::string>();

        struct Node current_node = fringe.top();
        fringe.pop();
        info.nodes_popped++;

        if (closed.find(current_node) != closed.end()) continue; // Skip if node is already explored

        closed.insert(current_node); // Add the current node to the closed set
        info.nodes_expanded++;

        if (current_node.city == goal) {
            std::vector<std::string> path;
            // Trace back the path here if necessary
            // For now, we just return the goal city
            path.push_back(goal);
            return path;
        }

        // Get the index of the current city
        auto it = std::find(cities.begin(), cities.end(), current_node.city);
        int city_index = std::distance(cities.begin(), it);

        // Add all successors of the current node to the fringe
        for (int i = 0; i < cities.size(); ++i) {
            if (adjacency_matrix[city_index][i] > 0) { // Check if there is a connection
                Node successor = {cities[i], current_node.distance + adjacency_matrix[city_index][i], 0};
                fringe.push(successor);
                info.nodes_generated++;
            }
        }
    }
}
