import java.util.concurrent.Callable;

public class MultiplicationThread implements Callable<int[][]> {
    private int[][] a;
    private int[][] b;

    public MultiplicationThread(int[][] a, int[][] b){
        this.a = a;
        this.b = b;
    }

    @Override
    public int[][] call() throws Exception {
        int n = a.length;
        int[][] result = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                result[i][j] += a[i][j] * b[i][j];
            }
        }
        return result;
    }
}
