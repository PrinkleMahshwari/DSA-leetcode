class Solution:
    def createGrid(self, m: int, n: int) -> list[str]:
        grid = [""] * m

        grid[0] = "." * n

        for i in range(1, m):
            grid[i] = "#" * (n - 1) + "."

        return grid
