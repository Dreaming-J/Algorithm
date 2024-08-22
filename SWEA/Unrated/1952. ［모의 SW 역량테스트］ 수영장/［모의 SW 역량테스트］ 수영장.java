/*
SWEA 1952. [모의 SW 역량테스트] 수영장
 
1. 테스트 케이스 입력
2. 1일, 1달, 3달, 1년 이용권 가격 입력
3. 올해 이용 계획 입력
4. 수영장 비용을 연 이용권으로 시작
5. 각 달을 돌며, 비용 비교
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MONTH_SIZE = 12;
    static int testCase;
    static int dayPrice, monthPrice, threeMonthPrice, yearPrice;
    static int[] plan = new int[MONTH_SIZE];
    static int minPrice;
 
    public static void initTestCase() throws IOException {
        //2. 1일, 1달, 3달, 1년 이용권 가격 입력
        st = new StringTokenizer(input.readLine().trim());
        dayPrice = Integer.parseInt(st.nextToken());
        monthPrice = Integer.parseInt(st.nextToken());
        threeMonthPrice = Integer.parseInt(st.nextToken());
        yearPrice = Integer.parseInt(st.nextToken());
 
        //3. 올해 이용 계획 입력
        st = new StringTokenizer(input.readLine().trim());
        for (int idx = 0; idx < MONTH_SIZE; idx++) {
            plan[idx] = Integer.parseInt(st.nextToken());
        }
 
        //4. 수영장 비용을 연 이용권으로 시작
        minPrice = yearPrice;
    }
 
    public static void findMinPrice(int monthIdx, int totalPrice) {
        //현재 비용이 찾아놓은 최소 비용보다 크다면 탐색 포기
        if (totalPrice >= minPrice)
            return;
 
        //모든 달을 선택했다면 탐색 종료
        if (monthIdx == MONTH_SIZE) {
            minPrice = totalPrice;
            return;
        }
 
        //1일 이용권으로 선택
        findMinPrice(monthIdx + 1, totalPrice + plan[monthIdx] * dayPrice);
        //1달 이용권으로 선택
        findMinPrice(monthIdx + 1, totalPrice + monthPrice);
        //3달 이용권으로 선택
        findMinPrice(Math.min(monthIdx + 3, MONTH_SIZE), totalPrice + threeMonthPrice);
    }
 
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(input.readLine());
        for (int tc = 1; tc <= testCase; tc++) {
            initTestCase();
 
            //5. 각 달을 돌며, 비용 비교
            findMinPrice(0, 0);
 
            output.append("#").append(tc).append(" ").append(minPrice).append("\n");
        }
        System.out.println(output);
    }
}