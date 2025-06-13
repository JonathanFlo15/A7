import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;

public class Clustering {
    // clusters for each point
    private final int[] clusters;
    // number of clusters
    private final int k;

    // Constructor: run the clustering algorithm and create the clusters
    public Clustering(Point2D[] locations, int k) {
        // throw exceptions
        if (locations == null) {
            throw new IllegalArgumentException("locations cannot be null");
        }
        // check if in range of 0 and m - 1
        if (k < 1 || k > locations.length) {
            throw new IllegalArgumentException("k is out of bounds");
        }
        // assign instance variables
        this.k = k;
        int m = locations.length;
        // make a clusters of m points
        this.clusters = new int[m];

        // make a new edge weighted graph of points m
        EdgeWeightedGraph g = new EdgeWeightedGraph(m);
        // iterate through edges, nested to prevent duplicate edges
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                // distance from location i to location j
                double dist = locations[i].distanceTo(locations[j]);
                // add new edge to graph
                g.addEdge(new Edge(i, j, dist));

            }
        }

        // make mst
        // takes edge weighted graph g
        KruskalMST mst = new KruskalMST(g);

        // create the cluster graph
        // remove k - 1 heaviest edges
        EdgeWeightedGraph clustergraph = new EdgeWeightedGraph(m);

        // keep track of how many edges being added
        int count = 0;
        // iterate through edges in mst
        for (Edge e : mst.edges()) {
            if (count++ < m - k) {
                clustergraph.addEdge(e);
            }
            else {
                break;
            }
        }

        // compute connected components to determine clusters
        CC cc = new CC(clustergraph);
        // iterate through points
        for (int i = 0; i < m; i++) {
            // assigns connected components
            clusters[i] = cc.id(i);
        }
    }

    // Returns the cluster of the ith location
    public int clusterOf(int i) {
        if (i < 0 || i >= clusters.length) {
            throw new IllegalArgumentException("index out of bounds");
        }
        // returns cluster at ith location
        return clusters[i];
    }

    // Reduces the dimensions of an input array using clusters
    public int[] reduceDimensions(int[] input) {
        if (input == null || input.length != clusters.length) {
            throw new IllegalArgumentException(
                    "input length must match number of locations");
        }
        // array of size k
        int[] reduced = new int[k];
        for (int i = 0; i < input.length; i++) {
            reduced[clusters[i]] += input[i];
        }
        return reduced;
    }

// Unit testing (required)
