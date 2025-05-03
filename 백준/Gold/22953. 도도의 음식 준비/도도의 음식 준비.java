import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int chefCount, foodCount, encourageCount;
	static int[] cookTime;

	public static void main(String[] args) throws IOException {
		init();

		System.out.println(binarySearch());
	}

	static long binarySearch() {
		long low = 0;
		long high = 1_000_000L * 1_000_000L;

		while (low <= high) {
			long mid = low + (high - low) / 2;

			if (canCook(mid))
				high = mid - 1;
			else
				low = mid + 1;
		}

		return low;
	}

	static boolean canCook(long time) {
		long[][] dp = new long[chefCount + 1][encourageCount + 1];

		for (int chefIdx = 1; chefIdx <= chefCount; chefIdx++) {
			for (int encourageIdx = 0; encourageIdx <= encourageCount; encourageIdx++) {
				long currentTime = cookTime[chefIdx - 1];
				long madeFood = time / currentTime;

				dp[chefIdx][encourageIdx] = dp[chefIdx - 1][encourageIdx] + madeFood;

				for (int e = 1; e <= encourageIdx; e++) {
					currentTime = Math.max(1L, (long)cookTime[chefIdx - 1] - e);
					madeFood = time / currentTime;

					dp[chefIdx][encourageIdx] = Math.max(dp[chefIdx][encourageIdx], dp[chefIdx - 1][encourageIdx - e] + madeFood);
				}
			}
		}

		return dp[chefCount][encourageCount] >= foodCount;
	}

	static void init() throws IOException {
		st = new StringTokenizer(input.readLine());
		chefCount = Integer.parseInt(st.nextToken());
		foodCount = Integer.parseInt(st.nextToken());
		encourageCount = Integer.parseInt(st.nextToken());

		cookTime = new int[chefCount];
		st = new StringTokenizer(input.readLine());
		for (int idx = 0; idx < chefCount; idx++)
			cookTime[idx] = Integer.parseInt(st.nextToken());
	}
}