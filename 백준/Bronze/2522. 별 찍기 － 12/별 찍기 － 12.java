import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
#백준 2522. 별 찍기 - 12
예제를 보고 규칙을 유추한 뒤 별 찍기
    #예제 입력
    3
    #예제 출력
      *
     **
    ***
     **
      *

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

        for (int row = 1; row <= lineCount; row++) {
            System.out.println(" ".repeat(lineCount - row) + "*".repeat(row));
        }

        for (int row = lineCount - 1; row >= 1; row--) {
            System.out.println(" ".repeat(lineCount - row) + "*".repeat(row));
        }
    }
}
