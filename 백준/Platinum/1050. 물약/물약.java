/*
= BOJ 1050. 물약

- 특이사항
문자열을 이용한 탐색이 필요하므로 HashMap을 이용해 해당 재료의 비용을 저장
재료는 중복이 없지만, 물약의 레시피는 중복이 가능
따라서 레시피 저장을 위해 HashMap의 value는 ArrayList로 저장

- 반례
1 4
A 1
A=1B+1C
B=1A+1C
C=1A
LOVE=1A+1B+1C
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Recipe {
        String name;
        List<Component> components;

        public Recipe(String name, List<Component> components) {
            this.name = name;
            this.components = components;
        }
    }

    static class Component {
        String name;
        int count;

        public Component(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }

    static final long MAX_VALUE = 1_000_000_001, IMPOSSIBLE = -1;
    static final String LOVE = "LOVE";

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int marketIngredientSize, recipeSize;
    static Map<String, Long> potions;
    static List<Recipe> recipes;

    public static void main(String[] args) throws IOException {
        init();

        makePotions();

        System.out.println(potions.getOrDefault(LOVE, IMPOSSIBLE));
    }

    private static void makePotions() {
        //더 저렴하거나 새로운 포션을 하나라도 만들었음을 표시하는 flag
        boolean isAnyRecipeSuccessful;

        do {
            isAnyRecipeSuccessful = false;
            for (Recipe recipe : recipes) {
                long totalCost = 0;

                //해당 레시피로 물약 제작 시도
                boolean isRecipeSuccessful = true;
                for (Component componenet : recipe.components) {
                    if (!potions.containsKey(componenet.name)) {
                        isRecipeSuccessful = false;
                        break;
                    }

                    totalCost += potions.get(componenet.name) * componenet.count;

                    if (totalCost >= MAX_VALUE) {
                        totalCost = MAX_VALUE;
                        break;
                    }
                }

                //물약 제조에 실패했다면
                if (!isRecipeSuccessful)
                    continue;

                //현재 등록되어 있는 비용보다 크다면
                if (potions.containsKey(recipe.name) && totalCost >= potions.get(recipe.name))
                    continue;

                isAnyRecipeSuccessful = true;
                potions.put(recipe.name, totalCost);
            }
        } while (isAnyRecipeSuccessful);
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        marketIngredientSize = Integer.parseInt(st.nextToken());
        recipeSize = Integer.parseInt(st.nextToken());

        potions = new HashMap<>();
        for (int idx = 0; idx < marketIngredientSize; idx++) {
            st = new StringTokenizer(input.readLine());
            String name = st.nextToken();
            Long cost = Long.parseLong(st.nextToken());

            potions.put(name, cost);
        }

        recipes = new ArrayList<>();
        for (int idx = 0; idx < recipeSize; idx++) {
            String[] recipe = input.readLine().split("=");

            List<Component> components = new ArrayList<>();
            for (String component : recipe[1].split("\\+")) {
                int count = component.charAt(0) - '0';
                String name = component.substring(1);

                components.add(new Component(name, count));
            }

            recipes.add(new Recipe(recipe[0], components));
        }
    }
}