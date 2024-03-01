# Let's start by implementing the core functionality in Python, which includes parsing the input files,
# and setting up the structure for both uninformed and informed search algorithms.
# This implementation can be adapted to C or any other language as needed.

def parse_input_file(filename):
    """
    Parse the input file to create a graph representation of the cities and distances.
    """
    graph = {}
    with open(filename, 'r') as file:
        for line in file:
            line = line.strip()
            if line == "END OF INPUT":
                break
            parts = line.split()
            city1, city2, distance = parts[0], parts[1], float(parts[2])
            if city1 not in graph:
                graph[city1] = {}
            if city2 not in graph:
                graph[city2] = {}
            graph[city1][city2] = distance
            graph[city2][city1] = distance  # Assuming bidirectional roads
    return graph

def parse_heuristic_file(filename):
    """
    Parse the heuristic file to create a dictionary of heuristic values for each city.
    """
    heuristics = {}
    with open(filename, 'r') as file:
        for line in file:
            if line.strip() == "END OF INPUT":
                break
            city, heuristic = line.split()
            heuristics[city] = float(heuristic)
    return heuristics

# For now, we'll just print the graph and heuristic data to verify parsing
graph = parse_input_file('input1.txt')
heuristics = parse_heuristic_file('h_kassel.txt')

graph, heuristics
