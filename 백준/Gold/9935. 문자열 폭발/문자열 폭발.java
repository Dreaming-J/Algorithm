import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        String bomb = br.readLine();

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            stack.push(str.charAt(i));

            if (stack.size() >= bomb.length()) {
                boolean isSame = true;
                for (int j = 0; j < bomb.length(); j++) {
                    if (stack.get(stack.size() - bomb.length() + j) != bomb.charAt(j)) {
                        isSame = false;
                        break;
                    }
                }
                if (isSame) for (int j = 0; j < bomb.length(); j++) stack.pop();
            }
        }

        if (stack.isEmpty()) System.out.println("FRULA");
        else System.out.println(stack.stream()
                .map(Object::toString)
                .collect(Collectors.joining("")));
    }
}