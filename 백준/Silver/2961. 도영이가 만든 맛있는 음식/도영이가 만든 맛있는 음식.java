/*
#백준 2961. 도영이가 만든 맛있는 음식

1. 재료의 개수를 입력받는다.
2. 개수만큼 재료의 신맛과 쓴맛을 입력받는다.
3. 공집합을 제외한 부분집합을 구한다.
    3-1. 하나 이상 선택한 부분집합의 점수를 비교한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int ingredientSize;
    static int[] sourTaste, bitterTaste;
    static int bestScore;

    public static void findBestFood(int elementIdx, int countSize, int sourScore, int bitterScore) {
        if (elementIdx == ingredientSize) {
            //3-1. 하나 이상 선택한 부분집합의 점수를 비교한다.
            if (countSize > 0)
                bestScore = Math.min(Math.abs(sourScore - bitterScore), bestScore);
            return;
        }

        findBestFood(elementIdx + 1, countSize + 1, sourScore * sourTaste[elementIdx],
                bitterScore + bitterTaste[elementIdx]);
        findBestFood(elementIdx + 1, countSize, sourScore, bitterScore);
    }

    public static void main(String[] args) throws IOException {
        bestScore = Integer.MAX_VALUE;

        //1. 재료의 개수를 입력받는다.
        ingredientSize = Integer.parseInt(input.readLine());

        //2. 개수만큼 재료의 신맛과 쓴맛을 입력받는다.
        sourTaste = new int[ingredientSize];
        bitterTaste = new int[ingredientSize];
        for (int idx = 0; idx < ingredientSize; idx++) {
            st = new StringTokenizer(input.readLine().trim());
            sourTaste[idx] = Integer.parseInt(st.nextToken());
            bitterTaste[idx] = Integer.parseInt(st.nextToken());
        }

        //3. 공집합을 제외한 부분집합을 구한다.
        findBestFood(0, 0, 1, 0);

        System.out.println(bestScore);
    }
}
