import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input;
	static StringTokenizer tokenizer;
	static int groupSize, stationSize, maxCapacity;
	static PriorityQueue<Group> minHeap;
	static SegmentTree st;
	static int totalPassengers;

	public static class Group implements Comparable<Group> {
		int src, dest, passengers;

		public Group(int src, int dest, int passengers) {
			this.src = src;
			this.dest = dest;
			this.passengers = passengers;
		}

		@Override
		public int compareTo(Group o) {
			return this.dest - o.dest;
		}
	}

	public static class SegmentTree {
		int[] tree, lazy;

		public SegmentTree(int size) {
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			lazy[node] += lazyValue;
			tree[node] += lazyValue;

			if (right < updateLeft || updateRight < left)
				return tree[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] += value;
				return tree[node] += value;
			}

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return tree[node] =
				Math.max(update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue),
					update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue));
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
			lazy[node] += lazyValue;
			tree[node] += lazyValue;

			if (right < queryLeft || queryRight < left)
				return 0;

			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return Math.max(query(queryLeft, queryRight, left, mid, node * 2, lazyValue),
				query(queryLeft, queryRight, mid + 1, right, node * 2 + 1, lazyValue));
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		while (!minHeap.isEmpty()) {
			Group cur = minHeap.poll();

			int curPassengers = st.query(cur.src, cur.dest - 1, 1, stationSize, 1, 0);

			int availablePassengers = Math.min(cur.passengers, maxCapacity - curPassengers);

			if (availablePassengers <= 0)
				continue;

			st.update(cur.src, cur.dest - 1, availablePassengers, 1, stationSize, 1, 0);
			totalPassengers += availablePassengers;
		}

		System.out.println(totalPassengers);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));

		tokenizer = new StringTokenizer(input.readLine());
		groupSize = Integer.parseInt(tokenizer.nextToken());
		stationSize = Integer.parseInt(tokenizer.nextToken());
		maxCapacity = Integer.parseInt(tokenizer.nextToken());

		minHeap = new PriorityQueue<>();
		for (int idx = 0; idx < groupSize; idx++) {
			tokenizer = new StringTokenizer(input.readLine());
			int src = Integer.parseInt(tokenizer.nextToken());
			int dest = Integer.parseInt(tokenizer.nextToken());
			int count = Integer.parseInt(tokenizer.nextToken());

			minHeap.add(new Group(src, dest, count));
		}

		st = new SegmentTree(stationSize);
	}
}