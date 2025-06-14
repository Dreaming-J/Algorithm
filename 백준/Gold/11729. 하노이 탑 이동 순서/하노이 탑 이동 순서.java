import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static BufferedReader input;
	static StringBuilder output;
	static int plateSize;

	public static void main(String[] args) throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		plateSize = Integer.parseInt(input.readLine());

		output.append((1 << plateSize) - 1)
			.append("\n");

		hanoi(plateSize, 1, 2, 3);

		System.out.println(output);
	}

	public static void hanoi(int size, int start, int to, int end) {
		if (size == 1) {
			output.append(start)
				.append(" ")
				.append(end)
				.append("\n");
			return;
		}

		hanoi(size - 1, start, end, to);

		output.append(start)
			.append(" ")
			.append(end)
			.append("\n");

		hanoi(size - 1, to, start, end);
	}
}