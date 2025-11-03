# Assignment 4 DAA
# By Madiyar Kenzhebayev SE-2407
# For Design And Analysis of Algorithms

# Graph Analysis - Smart City Scheduling

## Overview
This project implements graph algorithms for analyzing task dependencies in smart city scheduling:
- Strongly Connected Components (Kosaraju's algorithm)
- Topological Sorting (Kahn's algorithm)
- Shortest/Longest Paths in DAGs

# Compile
mvn compile

or use 
GraphGenerator.java
GraphProcessor.java

# Run all tests
mvn test

or use test classes:
DAGSPTest.java
SCCTest.java  
TopoSortTest.java

# Create JAR file
mvn package

# Algorithms Implemented
SCC: Kosaraju's algorithm (O(V+E))

Topological Sort: Kahn's algorithm (O(V+E))

Shortest Path: Dynamic programming on topological order (O(V+E))

# Data Format
# Input JSON files in data/ folder:

json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    ...
  ],
  "source": 4,
  "weight_model": "edge"
}
# Results
# The analysis provides:

Strongly connected components and their sizes

Condensation graph (DAG of components)

Topological ordering of components

Shortest paths from source node

Critical path (longest path) and its length

# Testing
The project includes JUnit tests for each algorithm:

SCC detection with cycles

Topological sort validation

Shortest path calculations

