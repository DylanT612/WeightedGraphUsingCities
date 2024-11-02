/*
I certify, that this computer program submitted by me is all of my own work.
Signed: Dylan Theis 10/16/2024

Author: Dylan Theis
Date: Fall 2024
Class: CSC420
Project: Simple Routing via Weighted Graphs
Description: Create a Minimum Spanning Tree using the appropriate cities, displaying
the Start and End city, and the distance required to travel between them.
*/


import java.io.*;
import java.util.*;

public class theisWeightedGraph {
    // Weighted graph using hashmap
    private Map<String, List<theisEdge>> distanceMap;

    // Constructor to initialize graph
    public theisWeightedGraph() {
        distanceMap = new HashMap<>();
    }

    // Create weighed graph edge
    public void addEdge(String city1, String city2, int distance) {
        // If our first city doesn't exist
        distanceMap.putIfAbsent(city1, new ArrayList<>());
        // If our second city doesn't exist
        distanceMap.putIfAbsent(city2, new ArrayList<>());
        // Add edge from city to city and its reflection
        distanceMap.get(city1).add(new theisEdge(city2, distance));
        distanceMap.get(city2).add(new theisEdge(city1, distance));
    }

    // Read city and distances from file
    public void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Using buffered reader go line by line splitting on ","
            // Place them into variables and add edge  to hashmap using information provided on each line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String city1 = parts[0].trim();
                String city2 = parts[1].trim();
                int distance = Integer.parseInt(parts[2].trim());
                addEdge(city1, city2, distance);
            }
            // Catch error resulting in file not being read correctly
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Finding the shortest path between two cities
    public void findShortestPath(String startCity, String endCity) {
        // Create two new hashmaps and a priority queue to find the shortest path
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousCities = new HashMap<>();
        // Priority queue compares the edges distance
        PriorityQueue<theisEdge> priorityQueue = new PriorityQueue<>();

        // For dijkstra make all the cities have max val an int can have
        for (String city : distanceMap.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        // Start with starting city having a distance of 0(because we are already there)
        distances.put(startCity, 0);

        // Add starting city edge to priority queue
        priorityQueue.add(new theisEdge(startCity, 0));

        // While something in priority queue
        while (!priorityQueue.isEmpty()) {
            // Get edge from queue
            theisEdge currentEdge = priorityQueue.poll();
            // Get city from edge
            String currentCity = currentEdge.city;

            // If we are at the end
            if (currentCity.equals(endCity)) {
                break;
            }

            // View edges connected to city
            for (theisEdge neighbor : distanceMap.get(currentCity)) {
                // Get distance between cities
                int newDistance = distances.get(currentCity) + neighbor.distance;
                // If distance is min distance from node
                if (newDistance < distances.get(neighbor.city)) {
                    // Assign neighbor with distance in Hashmap
                    distances.put(neighbor.city, newDistance);
                    // Add the neighbor city into previous city
                    previousCities.put(neighbor.city, currentCity);
                    // Add the edge of neighbor city to priority queue
                    priorityQueue.add(new theisEdge(neighbor.city, newDistance));
                }
            }
        }

        // Follow path from endCity to startCity
        // Create new path
        List<String> path = new ArrayList<>();
        // For each vertice, and add that vertice to path
        for (String vertice = endCity; vertice != null; vertice = previousCities.get(vertice)) {
            path.add(vertice);
        }
        // Reverse the path since in end to start order
        Collections.reverse(path);

        // Display result using dijkstra algorithm
        System.out.println("The shortest route from " + startCity + " to " + endCity + ": "
                + String.join(" >> ", path) + " (cost " + distances.get(endCity) + ")");
    }

    // Main method
    public static void main(String[] args) {
        // Print my information
        System.out.println("Dylan Theis - theisd@csp.edu");
        System.out.println("I certify that this is my own work.\n");

        // Create new weighted graph using hashmap
        theisWeightedGraph graph = new theisWeightedGraph();

        // Load cities and their distances from file
        graph.loadFromFile("theisCityDistances.txt");

        // List cities that distances can be selected
        List<String> cities = Arrays.asList("Hollywood", "San Francisco", "Salt Lake City", "Albuquerque",
                "Seattle", "Calgary", "Helena", "Winnipeg", "Denver", "Dallas", "Duluth", "Kansas City",
                "Chicago", "Montreal", "Atlanta", "New Orleans", "New York", "Washington", "Miami");

        Scanner scanner = new Scanner(System.in);
        // Display the cities list
        System.out.println("Please select a Starting and Destination city: <Enter the city number>");
        // For each city in the list give it its own index
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(i + " > " + cities.get(i));
        }

        // Get users start and end city
        System.out.println("Please enter starting city: ");
        int startCityIndex = scanner.nextInt();
        System.out.println("Please enter destination city: ");
        int endCityIndex = scanner.nextInt();

        // Get city names based on index selection
        String startCity = cities.get(startCityIndex);
        String endCity = cities.get(endCityIndex);

        // Use Dijkstra's algorithm to find the shortest path from the start to the end city
        graph.findShortestPath(startCity, endCity);
    }
}


