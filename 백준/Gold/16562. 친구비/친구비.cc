#include <iostream>
#include <vector>
#include <string>
using namespace std;

const int REPRESENTATIVE = 0;
const string IMPOSSIBLE = "Oh no";

int student_size, network_size, budget, cost;
vector<int> parents, costs;

int find(int element)
{
	if (parents[element] == REPRESENTATIVE)
		return element;

	return parents[element] = find(parents[element]);
}

bool union_set(int element1, int element2)
{
	int parent1 = find(element1);
	int parent2 = find(element2);

	if (parent1 == parent2)
		return false;

	if (costs[parent1] < costs[parent2])
		parents[parent2] = parent1;
	else
		parents[parent1] = parent2;

	return true;
}

void init()
{
	cin >> student_size >> network_size >> budget;

	parents.resize(student_size + 1);
	costs.resize(student_size + 1);

	for (int idx = 1; idx <= student_size; idx++)
		cin >> costs[idx];

	for (int idx = 1; idx <= network_size; idx++)
	{
		int student1, student2;

		cin >> student1 >> student2;

		union_set(student1, student2);
	}
}

int main()
{
	init();

	for (int idx = 1; idx <= student_size; idx++)
	{
		if (parents[idx] != REPRESENTATIVE)
			continue;

		cost += costs[idx];
	}

	cout << (cost > budget ? IMPOSSIBLE : to_string(cost));

	return 0;
}