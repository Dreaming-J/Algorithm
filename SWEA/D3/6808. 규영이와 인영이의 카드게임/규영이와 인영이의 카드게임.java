/*
=SWEA 6808. 규영이와 인영이의 카드게임
1~18장의 카드를 두 명이 나눠서 받은 후, 서로 1장씩 내 크기를 비교하는 게임을 했을 때,
규영이가 이기는 경우와 지는 경우를 구하여라.
이긴 사람은 두 카드의 합만큼 점수를 어고, 진 사람은 점수가 없다.
총점이 더 높은 사람이 승자이다
규영이의 카드 조합 및 내는 순서는 입력으로 주어진다.

=입력
첫번째 줄: 테스트 케이스의 수
    =각 테스트 케이스
    첫번째 줄: 9개의 숫자 (공백 구분)
=출력
"#Ti [규영이가 이긴 게임 수] [규영이가 진 게임 수]"

=로직
1. 규영이의 카드를 통해 인영이의 카드 9개를 알아낸다.
2. 인영이의 카드로 만들 수 있는 모든 조합을 계산한다.
3. 조합마다 게임을 진행하여 게임의 승패를 결정한다.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {
    static final int CARD_SIZE = 18;
    static final int HAND_SIZE = CARD_SIZE / 2;
    static final int MIN_WIN_SCORE = 86;
    static int winCase, loseCase;

    public static void findCombi(int depth, int computerHand, int playerScore, int computerScore, int visited, int[] player, int[] computer) {
        if (depth >= 0) {
            if (player[depth] > computer[computerHand])
	            playerScore += player[depth] + computer[computerHand];
            else
	            computerScore += player[depth] + computer[computerHand];
        }

        if (playerScore >= MIN_WIN_SCORE) {
            int fact = 1;
            for (int num = 1; num < HAND_SIZE - depth; num++)
                fact *= num;
            winCase += fact;
            return;
        }
        if (computerScore >= MIN_WIN_SCORE) {
            int fact = 1;
            for (int num = 1; num < HAND_SIZE - depth; num++)
                fact *= num;
            loseCase += fact;
            return;
        }

        for (int idx = 0; idx < HAND_SIZE; idx++) {
            if ((visited & 1 << idx) != 1 << idx) {
                visited += 1 << idx;
                findCombi(depth + 1, idx, playerScore, computerScore, visited, player, computer);
                visited -= 1 << idx;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int testCases;
        //규영이(player)와 인영이(computer)의 카드 패
        int[] player = new int[HAND_SIZE];
        int[] computer = new int[HAND_SIZE];
        int bitVisited;

        testCases = Integer.parseInt(input.readLine());
        for (int testCase = 1; testCase <= testCases; testCase++) {
            //입력 및 초기화
            winCase = 0;
            loseCase = 0;
            bitVisited = 0;
            st = new StringTokenizer(input.readLine().trim());
            for (int idx = 0; idx < HAND_SIZE; idx++) {
                int num = Integer.parseInt(st.nextToken());
                player[idx] = num;
                bitVisited += 1 << (num - 1);
            }

            //로직
            //user의 방문 정보가 담긴 bitVisited를 통해 computer의 패 계산
            int computerIdx = 0;
            for (int idx = 0; idx < CARD_SIZE; idx++, bitVisited = bitVisited >>> 1) {
                if (computerIdx == HAND_SIZE)
                    break;
                if (bitVisited % 2 == 0)
                    computer[computerIdx++] = idx + 1;
            }

            //computer의 패로 만들 수 있는 모든 조합 탐색
            findCombi(-1, 0, 0, 0, 0, player, computer);

            //출력
            output.write(String.format("#%d %d %d\n", testCase, winCase, loseCase));
        }
        output.flush();
        output.close();
    }
}