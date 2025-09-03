#include <iostream>
#include <vector>
#include <string>
using namespace std;

const int REPRESENTATIVE = 0, NONE = 0, POSSIBLE = 1, IMPOSSIBLE = 0;

int people_size, network_size;
vector<int> allies, enemies;

void init()
{
	cin >> people_size >> network_size;

	allies.resize(people_size + 1);
	enemies.resize(people_size + 1);
}

int find(int element)
{
	if (allies[element] == REPRESENTATIVE)
		return element;

	return allies[element] = find(allies[element]);
}

bool union_set(int element1, int element2)
{
	int rep1 = find(element1);
	int rep2 = find(element2);

	if (rep1 == rep2)
		return false;

	allies[rep1] = rep2;
	return true;
}

int prove()
{
	for (int idx = 1; idx <= network_size; idx++)
	{
		int person1, person2;

		cin >> person1 >> person2;

		if (find(person1) == find(person2))
			return IMPOSSIBLE;

		if (enemies[person1] == NONE)
			enemies[person1] = person2;
		else
			union_set(enemies[person1], person2);

		if (enemies[person2] == NONE)
			enemies[person2] = person1;
		else
			union_set(enemies[person2], person1);
	}

	return POSSIBLE;
}

int main()
{
	init();

	cout << prove();

	return 0;
}