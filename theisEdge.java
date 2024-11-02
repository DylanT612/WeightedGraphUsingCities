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

// Edge class implementing comparable
public class theisEdge implements Comparable<theisEdge> {
    // Variables
    String city;
    int distance;

    // Constructor
    theisEdge(String city, int distance) {
        this.city = city;
        this.distance = distance;
    }

    // Comparing edges
    @Override
    public int compareTo(theisEdge other) {
        return Integer.compare(this.distance, other.distance);
    }
}

