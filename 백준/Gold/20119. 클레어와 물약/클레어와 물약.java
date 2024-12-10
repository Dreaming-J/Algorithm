/*
= BOJ 20119. 클레어와 물약
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder output = new StringBuilder();
    static int potionSize;
    static int recipeSize;
    static int[] requiredIngredients;
    static ArrayList<Potion>[] recipes;
    static int brewedPotionSize;
    static boolean[] brewedPotions;

    static class Potion {
        int num, recipeId;

        public Potion(int num, int recipeId) {
            this.num = num;
            this.recipeId = recipeId;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        brewPotion();

        output.append(brewedPotionSize)
                .append("\n");
        for (int idx = 1; idx <= potionSize; idx++) {
            if (brewedPotions[idx])
                output.append(idx)
                        .append(" ");
        }

        System.out.println(output);
    }

    private static void brewPotion() {
        boolean[] brewed = new boolean[potionSize + 1];
        Deque<Potion> queue = new ArrayDeque<>();

        for (int potionIdx = 1; potionIdx <= potionSize; potionIdx++) {
            if (brewedPotions[potionIdx]) {
                queue.add(new Potion(potionIdx, -1));
            }
        }

        while (!queue.isEmpty()) {
            Potion cur = queue.poll();

            if (brewed[cur.num])
                continue;
            brewed[cur.num] = true;

            brewedPotionSize++;
            brewedPotions[cur.num] = true;

            for (Potion next : recipes[cur.num]) {
                if (--requiredIngredients[next.recipeId] == 0)
                    queue.add(next);
            }
        }
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        potionSize = Integer.parseInt(st.nextToken());
        recipeSize = Integer.parseInt(st.nextToken());

        recipes = new ArrayList[potionSize + 1];
        for (int idx = 1; idx <= potionSize; idx++)
            recipes[idx] = new ArrayList<>();
        requiredIngredients = new int[recipeSize];

        for (int recipeIdx = 0; recipeIdx < recipeSize; recipeIdx++) {
            st = new StringTokenizer(input.readLine());
            int ingredientSize = Integer.parseInt(st.nextToken());

            requiredIngredients[recipeIdx] = ingredientSize;

            int[] ingredients = new int[ingredientSize];
            for (int idx = 0; idx < ingredientSize; idx++)
                ingredients[idx] = Integer.parseInt(st.nextToken());

            int destPotion = Integer.parseInt(st.nextToken());
            for (int srcPotion : ingredients)
                recipes[srcPotion].add(new Potion(destPotion, recipeIdx));
        }

        brewedPotionSize = Integer.parseInt(input.readLine());

        brewedPotions = new boolean[potionSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < brewedPotionSize; idx++) {
            int brewedPotion = Integer.parseInt(st.nextToken());
            brewedPotions[brewedPotion] = true;
        }
        brewedPotionSize = 0;
    }
}
