import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int testCase;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            st = new StringTokenizer(input.readLine().trim());

            int bit = Integer.parseInt(st.nextToken());
            int number = Integer.parseInt(st.nextToken());
            boolean isON = (number & ((1 << bit) - 1)) == (1 << bit) - 1;
            
            output.append("#").append(tc).append(" ").append(isON ? "ON" : "OFF").append("\n");
        }
        System.out.println(output);
    }
}
