#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

const int grid_size = 4;
const int reduced_grid_size = 2;

int array_size;
vector<vector<int>> grid;
vector<vector<int>> reduced_grid;

void init()
{
	cin >> array_size;
	grid.resize(grid_size);

	for (int array_idx = 0; array_idx < array_size; array_idx++)
	{
		for (int grid_idx = 0; grid_idx < grid_size; grid_idx++)
		{
			int element;
			cin >> element;
			grid[grid_idx].push_back(element);
		}
	}
}

void reduce()
{
	reduced_grid.resize(reduced_grid_size);
	
	for (int i = 0; i < array_size; i++)
	{
		for (int j = 0; j < array_size; j++)
		{
			for (int k = 0; k < reduced_grid_size; k++)
			{
				int sum = grid[k][i] + grid[k + 2][j];

				reduced_grid[k].push_back(sum);
			}
		}
	}
}

long long find()
{
	long long number_of_zero = 0;

	for (int i = 0, j = 0; i < reduced_grid[0].size() && j < reduced_grid[1].size();)
	{
		int sum = reduced_grid[0][i] + reduced_grid[1][j];

		if (sum < 0)
			i++;
		else if (sum > 0)
			j++;
		else
		{
			long long same_i = 1;
			long long same_j = 1;

			while (i + same_i < reduced_grid[0].size() && reduced_grid[0][i] == reduced_grid[0][i + same_i])
				same_i++;
			while (j + same_j < reduced_grid[1].size() && reduced_grid[1][j] == reduced_grid[1][j + same_j])
				same_j++;

			number_of_zero += same_i * same_j;
			i += same_i;
			j += same_j;
		}
	}

	return number_of_zero;
}

int main()
{
	init();

	reduce();

	sort(reduced_grid[0].begin(), reduced_grid[0].end());
	sort(reduced_grid[1].begin(), reduced_grid[1].end(), greater<int>());

	cout << find();
}