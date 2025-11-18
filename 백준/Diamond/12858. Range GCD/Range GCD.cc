#include <iostream>
using namespace std;

typedef long long ll;

constexpr int MAX_NODE_SIZE = 400'001;
constexpr ll CMD_GCD = 0;

ll gcd(ll x, ll y)
{
	x = abs(x);
	y = abs(y);

	return y ? gcd(y, x % y) : x;
}

class LazySegmentTree
{
public:
	ll update(int updateLeft, int updateRight, ll value, int left, int right, int node, ll lazyValue)
	{
		int rangeSize = right - left + 1;

		if (lazyValue != 0)
		{
			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;
		}

		if (updateLeft > right || updateRight < left)
			return tree[node];

		if (updateLeft <= left && right <= updateRight)
		{
			lazy[node] += value;
			return tree[node] += value * rangeSize;
		}

		int mid = (left + right) / 2;
		lazyValue = lazy[node];
		lazy[node] = 0;
		return tree[node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue)
			+ update(updateLeft, updateRight, value, mid + 1, right, (node << 1) | 1, lazyValue);
	}

	ll query(int queryLeft, int queryRight, int left, int right, int node, ll lazyValue)
	{
		int rangeSize = right - left + 1;

		if (lazyValue != 0)
		{
			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;
		}

		if (queryLeft > right || queryRight < left)
			return 0;

		if (queryLeft <= left && right <= queryRight)
			return tree[node];

		int mid = (left + right) / 2;
		lazyValue = lazy[node];
		lazy[node] = 0;
		return query(queryLeft, queryRight, left, mid, node << 1, lazyValue)
			+ query(queryLeft, queryRight, mid + 1, right, (node << 1) + 1, lazyValue);
	}

private:
	ll tree[MAX_NODE_SIZE];
	ll lazy[MAX_NODE_SIZE];
};

class SegmentTree
{
public:
	ll update(int idx, ll value, int left, int right, int node)
	{
		if (idx > right || idx < left)
			return tree[node];

		if (left == right)
			return tree[node] += value;

		int mid = (left + right) / 2;
		int x = update(idx, value, left, mid, node << 1);
		int y = update(idx, value, mid + 1, right, (node << 1) | 1);
		return tree[node] = gcd(x, y);
	}

	ll query(int queryLeft, int queryRight, int left, int right, int node)
	{
		if (queryLeft > right || queryRight < left)
			return 0;

		if (queryLeft <= left && right <= queryRight)
			return tree[node];

		int mid = (left + right) / 2;
		int x = query(queryLeft, queryRight, left, mid, node << 1);
		int y = query(queryLeft, queryRight, mid + 1, right, (node << 1) | 1);
		return gcd(x, y);
	}

private:
	ll tree[MAX_NODE_SIZE];
};

int number_size, query_size;
int command, left, right;
LazySegmentTree lazy_seg;
SegmentTree seg;

void init()
{
	cin >> number_size;
	
	int number, prev_number;
	for (int idx = 1; idx <= number_size; idx++)
	{
		cin >> number;

		lazy_seg.update(idx, idx, number, 1, number_size, 1, 0);

		if (idx > 1)
			seg.update(idx, number - prev_number, 1, number_size, 1);

		prev_number = number;
	}

	cin >> query_size;
}

int main()
{
	init();

	for (int idx = 1; idx <= query_size; idx++)
	{
		int command, left, right;
		cin >> command >> left >> right;

		if (command == CMD_GCD)
		{
			std::cout << gcd(lazy_seg.query(left, left, 1, number_size, 1, 0), seg.query(left + 1, right, 1, number_size, 1)) << std::endl;
		}
		else
		{
			lazy_seg.update(left, right, command, 1, number_size, 1, 0);

			if (left > 1)
				seg.update(left, command, 1, number_size, 1);

			if (right < number_size)
				seg.update(right + 1, -command, 1, number_size, 1);
		}
	}

	return 0;
}