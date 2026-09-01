#include <stdlib.h>
#include <string.h>

typedef struct {
    int r;
    int c;
    int mask;
    int energy;
    int moves;
} State;

int minMoves(char** classroom, int classroomSize, int energy) {
    int m = classroomSize;
    int n = strlen(classroom[0]); // Fixed length evaluation to read individual string lengths correctly

    int startR = 0, startC = 0;
    int litterCount = 0;

    int* litterId = (int*)malloc(m * n * sizeof(int));
    for (int i = 0; i < m * n; i++) litterId[i] = -1;

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            char ch = classroom[r][c];
            if (ch == 'S') {
                startR = r;
                startC = c;
            } else if (ch == 'L') {
                litterId[r * n + c] = litterCount++;
            }
        }
    }

    if (litterCount == 0) {
        free(litterId);
        return 0;
    }

    int fullMask = (1 << litterCount) - 1;
    int numStates = 1 << litterCount;

    // Allocate flat tracking matrix initialized to -1
    int* bestEnergy = (int*)malloc(m * n * numStates * sizeof(int));
    for (int i = 0; i < m * n * numStates; i++) bestEnergy[i] = -1;

    // Use a dynamically allocated sliding queue buffer to handle massive multi-mask paths safely
    int maxQueueSize = m * n * numStates * (energy + 1);
    State* queue = (State*)malloc(maxQueueSize * sizeof(State));
    int head = 0, tail = 0;

    queue[tail++] = (State){startR, startC, 0, energy, 0};
    bestEnergy[(startR * n + startC) * numStates + 0] = energy;

    int dr[] = {-1, 1, 0, 0};
    int dc[] = {0, 0, -1, 1};
    int finalMoves = -1;

    while (head < tail) {
        State current = queue[head++];
        int r = current.r;
        int c = current.c;
        int mask = current.mask;
        int e = current.energy;
        int moves = current.moves;

        if (mask == fullMask) {
            finalMoves = moves;
            break;
        }

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
            if (classroom[nr][nc] == 'X') continue;
            if (e == 0) continue;

            int newEnergy = e - 1;
            int newMask = mask;
            char cell = classroom[nr][nc];

            // 1. Core Action: Collect litter if available at target cell
            if (cell == 'L') {
                int id = litterId[nr * n + nc];
                newMask |= (1 << id);
            }
            
            // 2. Resource Adjustment: Recharge only up to max capacity at station
            if (cell == 'R') {
                newEnergy = energy;
            }

            // 3. Pruning Check: Optimize to state tracking criteria
            int stateIdx = (nr * n + nc) * numStates + newMask;
            if (newEnergy <= bestEnergy[stateIdx]) continue;

            bestEnergy[stateIdx] = newEnergy;
            queue[tail++] = (State){nr, nc, newMask, newEnergy, moves + 1};
        }
    }

    free(litterId);
    free(bestEnergy);
    free(queue);

    return finalMoves;
}
