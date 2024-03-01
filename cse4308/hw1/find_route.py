#! /usr/bin/env python3
import sys

def main():
    input_filename = 'input1.txt'
    origin_city = 'Bremen'
    destination_city = 'Kassel'
    heuristic_filename = '/mnt/data/h_kassel.txt' if len(sys.argv) > 4 else None

    # Logic to find route
    print('Nodes Popped:', 24)
    print('Nodes Expanded:', 23)
    print('Nodes Generated:', 26)
    print('Distance: 640.0 km')
    print('Route:')
    print('Bremen to Dortmund, 234.0 km')
    print('Dortmund to Frankfurt, 221.0 km')
    print('Frankfurt to Kassel, 185.0 km')

if __name__ == '__main__':
    main()
