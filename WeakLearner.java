public class WeakLearner {

    // value predictor
    private final int vp;
    // dimension predictor
    private final int dp;
    // sign predictor
    private final int sp;


    // train the weak learner
    public WeakLearner(int[][] input, double[] weights, int[] labels) {
        if (input == null || weights == null || labels == null) {
            throw new IllegalArgumentException("arguments can't be null");
        }
        int n = input.length;
        if (n == 0) {
            throw new IllegalArgumentException(" value for input");
        }
        int k = input[0].length;
        if (weights.length != n || labels.length != n) {
            throw new IllegalArgumentException("lenghts are incompatible");
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        return

    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dp;
    }

    // return the value the learner uses to separate the data
    public int valuePredictor() {
        return vp;

    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return sp;
    }

    // unit testing
    public static void main(String[] args) {

    }
}
