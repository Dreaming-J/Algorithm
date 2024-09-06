/*
= SWEA 5658. [모의 SW 역량테스트] 보물상자 비밀번호

= 로직
1. 초기 세팅
	1-1. 숫자의 개수, 크기 순서 입력
	1-2. 숫자 입력
2. 생성 가능한 수 찾기
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int ROTATE_SIZE = 4, HEX = 16;
    static int numberSize, rankNum, passwordSize;
    static String numbers;
    static SortedSet<Integer> passwords;
    
    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine().trim());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();
            
            //2. 생성 가능한 수 찾기
            for (int rotateIdx = 0; rotateIdx < passwordSize; rotateIdx++) {
                for (int idx = passwordSize + rotateIdx; idx <= numberSize + rotateIdx ; idx += passwordSize) {
    				passwords.add(Integer.parseInt(numbers.substring(idx - passwordSize, idx), HEX));
    			}
            }
            
            //3. 출력
            output.append("#").append(tc).append(" ").append(passwords.toArray()[rankNum - 1]).append("\n");
        }
        System.out.println(output);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 숫자의 개수, 크기 순서 입력
        st = new StringTokenizer(input.readLine());
        numberSize = Integer.parseInt(st.nextToken());
        rankNum = Integer.parseInt(st.nextToken());
        
        //1-2. 숫자 입력
        numbers = input.readLine().trim();
        numbers += numbers;
        
        //1-3. 변수 초기화
        passwordSize = numberSize / ROTATE_SIZE;
        passwords = new TreeSet<>((o1, o2) -> o2 - o1);
    }
}