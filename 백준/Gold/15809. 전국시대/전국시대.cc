#include <iostream>
#include <vector>
#include <deque>
#include <algorithm>

using namespace std;

const int ALLIANCE = 1, WAR = 2;

struct Log {
	int command, country1, country2;
};

int countrySize, queyrSize, remainedCountrySize;
vector<int> soldierCounts, parents;
deque<Log> queries;

void init() {
	cin >> countrySize >> queyrSize;

	remainedCountrySize = countrySize;

	soldierCounts.resize(countrySize + 1);
	parents.resize(countrySize + 1);

	for (int idx = 1; idx <= countrySize; idx++) {
		cin >> soldierCounts[idx];
	}

	int command, country1, country2;
	for (int idx = 1; idx <= queyrSize; idx++) {
		Log log;
		cin >> log.command >> log.country1 >> log.country2;
		queries.push_back(log);
	}
}

int find(int country) {
	if (parents[country] == 0)
		return country;

	return parents[country] = find(parents[country]);
}

void union_set(int command, int country1, int country2) {
	int parent1 = find(country1);
	int parent2 = find(country2);

	remainedCountrySize--;

	if (command == ALLIANCE) {
		parents[parent2] = parent1;
		soldierCounts[parent1] += soldierCounts[parent2];
		soldierCounts[parent2] = 0;

		return;
	}

	if (soldierCounts[parent1] > soldierCounts[parent2]) {
		parents[parent2] = parent1;
		soldierCounts[parent1] -= soldierCounts[parent2];
		soldierCounts[parent2] = 0;
	}
	else if (soldierCounts[parent2] > soldierCounts[parent1]) {
		parents[parent1] = parent2;
		soldierCounts[parent2] -= soldierCounts[parent1];
		soldierCounts[parent1] = 0;
	}
	else {
		soldierCounts[parent1] = soldierCounts[parent2] = 0;
		remainedCountrySize--;
	}
}

void query() {
	while (!queries.empty()) {
		Log log = queries.front();
		queries.pop_front();

		union_set(log.command, log.country1, log.country2);
	}
}

int main() {
	init();

	query();

	sort(soldierCounts.begin(), soldierCounts.end());

	cout << remainedCountrySize << '\n';

	for (int soldierCount : soldierCounts) {
		if (soldierCount == 0)
			continue;

		cout << soldierCount << " ";
	}

	return 0;
}