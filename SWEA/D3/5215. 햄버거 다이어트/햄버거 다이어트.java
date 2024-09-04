/*
= SWEA 5215. 햄버거 다이어트

= 로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 재료의 수, 제한 칼로리 입력
	1-2. N개의 줄에 걸쳐 재료의 점수와 칼로리 입력
	1-3. 초기화
2. 최고 점수 탐색
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int ingredientSize, limitKcal;
    static Ingredient[] ingredients;
    static int[] bestScoreAboutKcal;

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();
            
            //2. 최고 점수 탐색
            for (Ingredient ingredient : ingredients) {
                //앞의 값을 참고하기 때문에 오염 방지를 위해 뒤에서부터 탐색
	        	for (int kcal = limitKcal; kcal > 0; kcal--) {
	        		if (kcal < ingredient.kcal)
	        			bestScoreAboutKcal[kcal] = bestScoreAboutKcal[kcal - 1];
	        		else
	        			bestScoreAboutKcal[kcal] = Math.max(bestScoreAboutKcal[kcal - ingredient.kcal] + ingredient.score, bestScoreAboutKcal[kcal]);
	        	}
            }

            //3. 출력
            output.append("#").append(tc).append(" ").append(bestScoreAboutKcal[limitKcal]).append("\n");
        }
        System.out.println(output);
    }
    
    public static class Ingredient {
    	int score, kcal;

		public Ingredient(int score, int kcal) {
			this.score = score;
			this.kcal = kcal;
		}
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 재료의 수, 제한 칼로리 입력
        st = new StringTokenizer(input.readLine().trim());
        ingredientSize = Integer.parseInt(st.nextToken());
        limitKcal = Integer.parseInt(st.nextToken());
        
        //1-2. N개의 줄에 걸쳐 재료의 점수와 칼로리 입력
        ingredients = new Ingredient[ingredientSize];
        for (int idx = 0; idx < ingredientSize; idx++) {
            st = new StringTokenizer(input.readLine().trim());
            ingredients[idx] = new Ingredient(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        //1-3. 초기화
        bestScoreAboutKcal = new int[limitKcal + 1];
    }
    
    
}