/*
= BOJ 4195. 친구 네트워크
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder output = new StringBuilder();
    static int friendshipSize;
    static HashMap<String, String> parents;

    public static void union(String element1, String element2) {
        String parent1 = find(element1);
        String parent2 = find(element2);
        int size1 = Integer.parseInt(parents.get(parent1));
        int size2 = Integer.parseInt(parents.get(parent2));

        if (parent1.equals(parent2))
            return;

        parents.put(parent2, parent1);
        parents.put(parent1, String.valueOf(size1 + size2));
    }

    public static String find(String element) {
        try {
            Integer.parseInt(parents.get(element));
            return element;
        } catch (NumberFormatException e) {
            String parent = find(parents.get(element));
            parents.put(element, parent);
            return parent;
        }
    }

    public static void main(String[] args) throws IOException {
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //초기 세팅
            init();

            //친구 관계 생성
            for (int idx = 0; idx < friendshipSize; idx++) {
                st = new StringTokenizer(input.readLine());
                String name1 = st.nextToken();
                String name2 = st.nextToken();

                parents.putIfAbsent(name1, "1");
                parents.putIfAbsent(name2, "1");

                union(name1, name2);

                //출력
                output.append(parents.get(find(name1)))
                        .append("\n");
            }
        }
        System.out.println(output);
    }

    public static void init() throws IOException {
        friendshipSize = Integer.parseInt(input.readLine());

        parents = new HashMap<>();
    }
}