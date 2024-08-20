/*
#SWEA 4796. 의석이의 우뚝 선 산
  
0. 테스트 케이스 입력
1. 산의 개수 입력
2. 산의 개수만큼 높이 입력
3. 변수 초기화
4. 구간 찾기
    4-1. 이전 값보다 크다면
        4-1-1. 끝 구간의 개수가 0이라면, 시작 구간 개수 증가
        4-1-2. 끝 구간의 개수가 0이 아니라면, 구간의 개수 계산
    4-2. 이전 값보다 작으면 끝 구간 개수 증가
    4-3. 탐색 종료 후, 아직 구간이 남아있다면 구간의 개수 계산
 */
 
import java.util.Scanner;
  
class Solution {
    static Scanner input = new Scanner(System.in);
    static StringBuilder output = new StringBuilder();
    static final int MIN_INTERVAL_SIZE = 3;
    static int testCases;
    static int mountainSize;
    static int[] mountains;
    static int start, end;
    static int intervalCount;
      
    public static void initTestCase() {
        //1. 산의 개수 입력
        mountainSize = input.nextInt();
         
        //2. 산의 개수만큼 높이 입력
        mountains = new int[mountainSize];
        for (int idx = 0; idx < mountainSize; idx++)
            mountains[idx] = input.nextInt();
         
        //3. 변수 초기화
        start = 1;
        end = 0;
        intervalCount = 0;
    }
 
    //4. 구간 찾기
    public static void findInterval() {
        for (int idx = 1; idx < mountainSize; idx++) {
            //4-1. 이전 값보다 크다면
            if (mountains[idx] > mountains[idx - 1]) {
                //4-1-1. 끝 구간의 개수가 0이라면, 시작 구간 개수 증가
                if (end == 0)
                    start++;
                 
                //4-1-2. 끝 구간의 개수가 0이 아니라면,
                else {
                    //4-1-2-1. 구간의 길이가 3이상이면 구간의 개수 계산
                    if (start + end >= MIN_INTERVAL_SIZE)
                        intervalCount += (start - 1) * end;
                    start = 2;
                    end = 0;
                }
            }
             
            //4-2. 이전 값보다 작으면 끝 구간 개수 증가
            else if (mountains[idx] < mountains[idx - 1])
                end++;
        }
         
        //4-3. 탐색 종료 후, 아직 구간이 남아있다면 구간의 개수 계산
        if (start + end >= MIN_INTERVAL_SIZE) {
            intervalCount += (start - 1) * end;
        }
    }
  
    public static void main(String args[]) {
        //0. 테스트 케이스 입력
        testCases = input.nextInt();
        for(int tc = 1; tc <= testCases; tc++) {
            initTestCase();
 
            //4. 구간 찾기
            findInterval();
              
            output.append("#").append(tc).append(" ").append(intervalCount).append("\n");
        }
        System.out.println(output);
    }
}