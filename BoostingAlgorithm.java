import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.List;


public class BoostingAlgorithm {
    private final Clustering clustering;            // clustering object
    private final int[][] reducedDim;             // reduced input dimensions
    private final int[] labels;                     // labels of the input
    private final double[] weights;                 // weights of input
    private final List<WeakLearner> weakLearners;   // weaklearn list

    // create the clusters and initialize your data structures
    public BoostingAlgorithm(int[][] input, int[] labels, Point2D[] locations, int k) {
        if (input == null || labels == null || locations == null)
            throw new IllegalArgumentException("no null arguments are allowed");
        if (input.length != labels.length)
            throw new IllegalArgumentException("input not same as label");
        if (k < 1 || k > locations.length)
            throw new IllegalArgumentException("invalid # of clusters");

        for (int label : labels) {
            if (label != 0 && label != 1)
                throw new IllegalArgumentException("labels can only be 0 or 1");
        }

        int n = input.length;
        clustering = new Clustering(locations, k);
        reducedDim = new int[n][];
        for (int i = 0; i < n; i++) {
            reducedDim[i] = clustering.reduceDimensions(input[i]);
        }
        this.labels = labels.clone();
        weights = new double[n];

        for (int i = 0; i < n; i++) {
            weights[i] = (double) 1 / n;
        }
        weakLearners = new ArrayList<>();
    }

    // return the current weight of the ith point
    public double weightOf(int i) {
        if (i < 0 || i >= weights.length)
            throw new IllegalArgumentException("index not valid");
        return weights[i];
    }

    // apply one step of the boosting algorithm
    public void iterate() {
        // create weaklearner object and add it the list of weaklearners
        WeakLearner wkLearner = new WeakLearner(reducedDim, weights, labels);
        weakLearners.add(wkLearner);

        double weightTotal = 0;
        // for loop that checks if learner correctly predicts label
        // if not, then weight is doubled to force learner to adapt
        for (int i = 0; i < weights.length; i++) {
            if (wkLearner.predict(reducedDim[i]) != labels[i]) {
                weights[i] *= 2;
            }

            weightTotal += weights[i];
        }
        // for loop that normalizes weights in array
        for (int i = 0; i < weights.length; i++) {
            weights[i] /= weightTotal;
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        if (sample == null)
            throw new IllegalArgumentException("cannot be null");
        // arrays created to store samples and predictions
        int[] reducedSample = clustering.reduceDimensions(sample);
        int[] predictions = new int[weakLearners.size()];
        // loop that predicts using weak learners predict
        for (int i = 0; i < weakLearners.size(); i++) {
            predictions[i] = weakLearners.get(i).predict(reducedSample);
        }

        int fraud = 0;
        int clean = 0;
        // for each loop that predicts if something is a fraud or clean
        for (int prediction : predictions) {
            if (prediction == 0) {
                fraud++;
            }
            else {
                clean++;
            }
        }
        // checks if algorithm predicted fraud or clean more
        if (fraud >= clean) {
            return 0;
        }
        else {
            return 1;
        }
    }

    // unit testing
    public static void main(String[] args) {


    }
}

