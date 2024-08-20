/*
#SWEA 1486. 장훈이의 높은 선반
  
0. 테스트 케이스 입력
1. 점원의 수, 선반의 높이 입력
2. 점원의 수만큼 점원의 키 입력
3. 점원의 키에 대한 부분집합을 구한다.
	3-1. 현재 높이가 선반의 높이보단 크지만 선반 높이와의 차이가 최소 높이 차이보다 크다면 탐색 종료
	3-2. 모든 원소를 탐색했다면 탐색 종료
		3-2-1. 최소 높이 차이 업데이트
	3-3. 다음 원소 탐색
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
  
class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int testCase;
    static int personSize;
    static int shelfHeight;
    static int[] people;
    static int minHeightDifference;
      
    public static void initTestCase() throws IOException {
        //1. 점원의 수, 선반의 높이 입력
    	st = new StringTokenizer(input.readLine().trim());
    	personSize = Integer.parseInt(st.nextToken());
    	shelfHeight = Integer.parseInt(st.nextToken());
         
        //2. 점원의 수만큼 점원의 키 입력
    	people = new int[personSize];
    	st = new StringTokenizer(input.readLine().trim());
        for (int idx = 0; idx < personSize; idx++)
        	people[idx] = Integer.parseInt(st.nextToken());
         
        //3. 변수 초기화
        minHeightDifference = Integer.MAX_VALUE;
    }
    
    //3. 점원의 키에 대한 부분집합을 구한다.
    public static void findPowerSetOfPeople(int selectIdx, int height, int peopleUsedCheckBit) {
    	//3-1. 현재 높이가 선반의 높이보단 크지만 선반 높이와의 차이가 최소 높이 차이보다 크다면 탐색 종료
    	if (height >= 0 && height >= minHeightDifference)
    		return;
    	
    	//3-2. 모든 원소를 탐색했다면 탐색 종료
    	if (selectIdx == personSize) {
    		//3-2-1. 최소 높이 차이 업데이트
    		if (height >= 0)
    			minHeightDifference = Math.min(height, minHeightDifference);
    		
    		return;
    	}
    	
    	//3-3. 다음 원소 탐색
    	findPowerSetOfPeople(selectIdx + 1, height + people[selectIdx], peopleUsedCheckBit | 1 << selectIdx);
    	findPowerSetOfPeople(selectIdx + 1, height, peopleUsedCheckBit);
    }
  
    public static void main(String args[]) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());
        for(int tc = 1; tc <= testCase; tc++) {
            initTestCase();
 
            findPowerSetOfPeople(0, -shelfHeight, 0);
              
            output.append("#").append(tc).append(" ").append(minHeightDifference).append("\n");
        }
        System.out.println(output);
    }
}