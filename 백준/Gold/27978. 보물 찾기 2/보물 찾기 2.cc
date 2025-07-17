#include <iostream>
#include <queue>
using namespace std;

constexpr int MAX_SIZE = 500, MAX = 1e9;
constexpr char SEA = '.', ROCK = '#', TREASURE = '*', BOAT = 'K';
constexpr int DELTAS[8][2] = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

struct Point {
    int row = 0;
    int col = 0;
    int fuel = 0;

    Point() = default;
    Point(int row, int col, int fuel = 0) : row(row), col(col), fuel(fuel) {}

    bool operator<(const Point& other) const {
        return fuel > other.fuel;
    }
};

int rowSize, colSize;
char map[MAX_SIZE][MAX_SIZE];
int visited[MAX_SIZE][MAX_SIZE];
Point start;

void init() {
    cin >> rowSize >> colSize;

    for (int row = 0; row < rowSize; row++) {
        for (int col = 0; col < colSize; col++) {
            cin >> map[row][col];

            if (map[row][col] == BOAT) {
                start = {row, col};
            }
        }
    }

    for (int row = 0; row < rowSize; row++) {
        for (int col = 0; col < colSize; col++) {
            visited[row][col] = MAX;
        }
    }
}

bool isOut(int row, int col) {
    return row < 0 || row >= rowSize || col < 0 || col >= colSize;
}

int findMinFuel() {
    priority_queue<Point> queue;

    queue.push(start);
    visited[start.row][start.col] = 0;

    while (!queue.empty()) {
        Point cur = queue.top();
        queue.pop();

        if (map[cur.row][cur.col] == TREASURE) {
            return cur.fuel;
        }

        for (auto& delta : DELTAS) {
            int nr = cur.row + delta[0];
            int nc = cur.col + delta[1];
            int nf = cur.fuel + (delta[1] == 1 ? 0 : 1);

            if (isOut(nr, nc) || map[nr][nc] == ROCK || nf >= visited[nr][nc]) {
                continue;
            }
            
            visited[nr][nc] = nf;
            queue.push(Point(nr, nc, nf));
        }
    }

    return -1;
}

int main() {
    init();

    cout << findMinFuel();

    return 0;
}