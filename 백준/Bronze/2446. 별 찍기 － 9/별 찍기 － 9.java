import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
#백준 2446. 별 찍기 - 9
예제를 보고 규칙을 유추한 뒤 별 찍기
    #예제 입력
    5
    #예제 출력
    *********
     *******
      *****
       ***
        *
       ***
      *****
     *******
    *********

#입력
첫째 줄: N (1 <= N <= 100)
#출력
첫째 줄부터 2xN-1번째 줄까지 차례대로 별 출력

#로직
이중 for문 2개를 이용해 별 출력
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int lineCount;

    public static void main(String[] args) throws IOException {
        lineCount = Integer.parseInt(br.readLine());

        for (int row = 0; row < lineCount; row++) {
            System.out.println(" ".repeat(row) + "*".repeat(2 * (lineCount - row) - 1));
        }

        for (int row = lineCount - 2; row >= 0; row--) {
            System.out.println(" ".repeat(row) + "*".repeat(2 * (lineCount - row) - 1));
        }
    }
}
