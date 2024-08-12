/*
#백준 17478. 재귀함수가 뭔가요?
주어진 예제 출력에 맞춰 재귀함수를 작성하라.

#입력
첫째 줄: N (1 <= N <= 50)
#출력
재귀 횟수에 따른 답변을 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final String dash = "____";
    static StringBuilder output = new StringBuilder();
    static int count;

    public static void recursiveChatBot(int n) throws IOException {
        if (n == count) {
            output.append(dash.repeat(n)).append("\"재귀함수가 뭔가요?\"\n");
            output.append(dash.repeat(n)).append("\"재귀함수는 자기 자신을 호출하는 함수라네\"\n");
            output.append(dash.repeat(n)).append("라고 답변하였지.\n");
            return;
        }

        output.append(dash.repeat(n)).append("\"재귀함수가 뭔가요?\"\n");
        output.append(dash.repeat(n)).append("\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n");
        output.append(dash.repeat(n)).append("마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n");
        output.append(dash.repeat(n)).append("그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n");
        recursiveChatBot(n + 1);
        output.append(dash.repeat(n)).append("라고 답변하였지.\n");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        count = Integer.parseInt(input.readLine());

        output.append("어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.\n");
        recursiveChatBot(0);

        System.out.println(output.toString());
    }
}
