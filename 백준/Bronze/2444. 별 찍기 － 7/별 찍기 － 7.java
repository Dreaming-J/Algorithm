import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
#백준 2444. 별 찍기 - 7
예제를 보고 규칙을 유추한 뒤 별 찍기
    #예제 입력
    5
    #예제 출력
        *
       ***
      *****
     *******
    *********
     *******
      *****
       ***
        *

#입력
첫째 줄: N (1 <= N <= 100)
#출력
첫째 줄부터 2xN-1번째 줄까지 차례대로 별 출력

#로직
이중 for문으로 별 출력
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder answer = new StringBuilder();
    static int lineCount;
    static int center;

    public static void main(String[] args) throws IOException {
        lineCount = Integer.parseInt(br.readLine()) * 2 - 1;
        center = lineCount / 2;

        for (int row = 0; row < lineCount; row++) {
            int gap = Math.abs(center - row);
            for (int col = 0; col < lineCount - gap; col++) {
                if (col < gap) {
                    answer.append(" ");
                    continue;
                }
                answer.append("*");
            }
            answer.append("\n");
        }

        System.out.println(answer.toString());
    }
}
