from collections import deque

class State:
    __slots__ = ['r', 'c', 'mask', 'energy', 'moves']
    def __init__(self, r, c, mask, energy, moves):
        self.r = r
        self.c = c
        self.mask = mask
        self.energy = energy
        self.moves = moves

class Solution:
    def minMoves(self, classroom: list[str], energy: int) -> int:
        m = len(classroom)
        n = len(classroom[0])

        startR = 0
        startC = 0
        litterCount = 0

        # assign an index to every litter cell
        litterId = [[-1] * n for _ in range(m)]

        for r in range(m):
            for c in range(n):
                ch = classroom[r][c]
                if ch == 'S':
                    startR = r
                    startC = c
                elif ch == 'L':
                    litterId[r][c] = litterCount
                    litterCount += 1

        # already clean
        if litterCount == 0:
            return 0

        fullMask = (1 << litterCount) - 1

        bestEnergy = [[[-1] * (1 << litterCount) for _ in range(n)] for _ in range(m)]

        queue = deque()
        queue.append(State(startR, startC, 0, energy, 0))
        bestEnergy[startR][startC][0] = energy

        dr = [-1, 1, 0, 0]
        dc = [0, 0, -1, 1]

        while queue:
            current = queue.popleft()
            r, c, mask, e, moves = current.r, current.c if hasattr(current, 'c') else current.c, current.mask, current.energy, current.moves

            if mask == fullMask:
                return moves

            for d in range(4):
                nr = r + dr[d]
                nc = c + dc[d]

                # outside grid
                if nr < 0 or nr >= m or nc < 0 or nc >= n:
                    continue
                # obstacle
                if classroom[nr][c_col:=nc] == 'X':
                    continue
                # no energy to make this move
                if e == 0:
                    continue

                newEnergy = e - 1
                newMask = mask
                cell = classroom[nr][nc]

                # collect litter
                if cell == 'L':
                    id_val = litterId[nr][nc]
                    newMask |= (1 << id_val)

                # reset energy
                if cell == 'R':
                    newEnergy = energy

                if newEnergy <= bestEnergy[nr][nc][newMask]:
                    continue

                bestEnergy[nr][nc][newMask] = newEnergy
                queue.append(State(nr, nc, newMask, newEnergy, moves + 1))

        return -1
