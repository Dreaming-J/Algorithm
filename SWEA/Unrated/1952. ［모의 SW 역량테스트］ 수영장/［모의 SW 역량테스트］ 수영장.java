/*
= SWEA 1952. [모의 SW 역량테스트] 수영장

= 로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 1일, 1달, 3달, 1년 이용권 가격 입력
	1-2. 올해 이용 계획 입력
	1-3. 변수 초기화
2. 최소 비용 탐색
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
    static final int MONTH_SIZE = 12, MARCH = 3, DECEMBER = 12;
    static int testCase;
    static int dayPrice, monthPrice, threeMonthPrice, yearPrice;
    static int[] plan, minPrices;
    
    public static void findMinPrice() {
    	for (int monthIdx = 1; monthIdx <= MONTH_SIZE; monthIdx++) {
    		//2-1. (1일 이용권 + 지난달 요금), (1달 이용권 + 지난달 요금) 중 더 작은 값 선택 
    		minPrices[monthIdx] = Math.min(dayPrice * plan[monthIdx], monthPrice) + minPrices[monthIdx - 1];
    		
    		//2-2. 3월부터 (3달 비용 + 3달 전 요금), 기록된 값 중 더 작은 값 선택
    		if (monthIdx >= MARCH)
    			minPrices[monthIdx] = Math.min(minPrices[monthIdx - 3] + threeMonthPrice, minPrices[monthIdx]);
    		
    		//2-3. 12월은 1년 비용, 기록된 값 중 더 작은 값 선택
    		if (monthIdx == DECEMBER)
    			minPrices[monthIdx] = Math.min(yearPrice, minPrices[monthIdx]);
    	}
    }
 
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());
        
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. 최소 비용 탐색
            findMinPrice();
 
            //3. 출력
            output.append("#").append(tc).append(" ").append(minPrices[MONTH_SIZE]).append("\n");
        }
        System.out.println(output);
    }
 
    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 1일, 1달, 3달, 1년 이용권 가격 입력
        st = new StringTokenizer(input.readLine().trim());
        dayPrice = Integer.parseInt(st.nextToken());
        monthPrice = Integer.parseInt(st.nextToken());
        threeMonthPrice = Integer.parseInt(st.nextToken());
        yearPrice = Integer.parseInt(st.nextToken());
 
        //1-2. 올해 이용 계획 입력
        plan = new int[MONTH_SIZE + 1];
        st = new StringTokenizer(input.readLine().trim());
        for (int idx = 1; idx <= MONTH_SIZE; idx++) {
            plan[idx] = Integer.parseInt(st.nextToken());
        }
 
        //1-3. 변수 초기화
        minPrices = new int[MONTH_SIZE + 1];
    }
}