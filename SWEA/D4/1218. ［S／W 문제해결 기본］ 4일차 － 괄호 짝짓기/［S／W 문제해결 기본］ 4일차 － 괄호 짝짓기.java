/*
=SWEA 1218. [S/W 문제해결 기본] 4일차 - 괄호 짝짓기

//1. '()', '[]', '{}', '<>'를 map에 넣어 보관한다.
//    key는 닫는괄호, value는 여는괄호
//2. 테스트 케이스의 길이를 입력받는다.
//3. 문자열을 한글자 씩 탐색한다.
    //3-1. 닫는괄호는 stack의 top값과 map을 통해 얻은 value를 비교해 같은지 확인한다.
        //3-1-1. 맞지 않다면 유효하지 않음을 출력한다.
    //3-2. 여는괄호는 stack에 넣는다.
//4. 탐색이 정상적으로 종료되고, stack이 비어있다면 유효함을 출력한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static final int VERIFIED = 1, UNVERIFIED = 0;
    static Map<Character, Character> bracketMap = new HashMap<>();
    static String bracketString;
    static int verified;

    public static void main(String[] args) throws IOException {
        //1. '()', '[]', '{}', '<>'를 map에 넣어 보관한다.
        bracketMap.put(')', '(');
        bracketMap.put(']', '[');
        bracketMap.put('}', '{');
        bracketMap.put('>', '<');
        
        for (int tc = 1; tc <= 10; tc++) {
            //2. 테스트 케이스의 길이를 입력받는다.
            input.readLine();
            
            //3. 문자열을 한글자 씩 탐색한다.
            Stack<Character> stack = new Stack<>();
            verified = VERIFIED;
            bracketString = input.readLine();
            for (Character cur : bracketString.toCharArray()) {
                //3-1. 닫는괄호는 stack의 top과 map을 통해 얻은 value를 비교해 같은지 확인한다.
                if (bracketMap.containsKey(cur)) {
                    if (stack.pop() != bracketMap.get(cur)) {
                        //3-1-1. 맞지 않다면 유효하지 않음을 출력한다.
                        verified = UNVERIFIED;
                        break;
                    }
                }
                //3-2. 여는괄호는 stack에 넣는다.
                else
                    stack.push(cur);
            }
            
            //4. 탐색이 종료된 후, stack이 비어있지 않다면 유효하지 않음
            if (!stack.isEmpty())
                verified = UNVERIFIED;
                
            output.append("#").append(tc).append(" ").append(verified).append("\n");
        }

        System.out.println(output);
    }
}
