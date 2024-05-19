import java.util.*;
import java.util.stream.*;

public class MaximumDoubleSliceSum {

    public static void main(String [] args) {
        System.out.printf("Hello Maximum Double Slice Sum solution #1%n");
        if (args != null && args.length == 1 && args[0].toLowerCase().equals("-usage")) {
            System.out.printf("java MaximumDoubleSliceSum%n");
            return;
        }
//        int [] A = new int [] {3, 2, 6};
        int [] A = new int [] {3, 2, 6, -1, 4, 5, -1, 2};
        MaximumDoubleSliceSum maximum = new MaximumDoubleSliceSum();
        int result = maximum.solution(A);
        System.out.printf("The maximum double slice sum is %d%n", result);
    }

    public int solution(int [] A) {
        int N = A.length;
        if (N < 3) return 0;
        int[] K1 = new int[N];
        int[] K2 = new int[N];
        for (int i = 1; i < N - 1; i++) {
            K1[i] = Math.max(K1[i - 1] + A[i], 0);
        }
        for (int i = N - 2; i > 0; i--) {
            K2[i] = Math.max(K2[i + 1] + A[i], 0);
        }

        System.out.printf("K1 is %s%n", Arrays.toString(K1));
        System.out.printf("K2 is %s%n", Arrays.toString(K2));

        int max = 0;

        for (int i = 1; i < N - 1; i++) {
            max = Math.max(max, K1[i - 1] + K2[i + 1]);
        }

        return max;
    }

}
