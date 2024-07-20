import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int t = Integer.parseInt(br.readLine()) + 1;
        List<Integer> intervals = new ArrayList<>();
        for (int i = 1; i < N; i++) {
            int T = Integer.parseInt(br.readLine());
            if (t != T) intervals.add(T - t);
            t = T + 1;
        }

        int cnt = intervals.size() - (K - 1);
        int answer = N;
        if (cnt > 0) {
            answer += intervals.stream()
                    .sorted()
                    .limit(cnt)
                    .mapToInt(i -> i)
                    .sum();
        }

        System.out.println(answer);
    }
}