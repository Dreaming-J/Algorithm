#include <iostream>
#include <vector>
using namespace std;

long long min_num, max_num;
int num_count;
vector<bool> is_used;

void init()
{
	ios::sync_with_stdio(0);
	cin.tie(0);

	cin >> min_num >> max_num;

	num_count = max_num - min_num + 1;
	is_used.resize(num_count);
}

int main()
{
	init();

	for (long long num = 2; num * num <= max_num; num++)
	{
		long long quotient = min_num / (num * num);

		if (min_num % (num * num) != 0)
			quotient++;

		while (quotient * (num * num) <= max_num)
		{
			if (!is_used[quotient * (num * num) - min_num])
			{
				is_used[quotient * (num * num) - min_num] = true;
				num_count--;
			}

			quotient++;
		}
	}

	cout << num_count;
}