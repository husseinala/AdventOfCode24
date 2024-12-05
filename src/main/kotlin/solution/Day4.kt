package solution

import utils.readFileLines

object Day4 {

    private val dirs = listOf(
        1 to 0,
        0 to 1,
        1 to 1,
        -1 to 1,
    )

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0
        val grid = processInput()

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                ans += dfs(
                    grid = processInput(),
                    i = i,
                    j = j,
                    searchTerm = "XMAS",
                    curr = grid[i][j].toString(),
                    currRev = grid[i][j].toString(),
                    dir = dirs,
                )
            }
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0

        val grid = processInput()

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val res = dfs(
                    grid = processInput(),
                    i = i,
                    j = j,
                    searchTerm = "MAS",
                    curr = grid[i][j].toString(),
                    currRev = grid[i][j].toString(),
                    dir = listOf(1 to 1),
                )

                if (
                    res == 1 &&
                    ((grid[i + 2][j] == 'S' && grid[i][j + 2] == 'M')
                            || (grid[i + 2][j] == 'M' && grid[i][j + 2] == 'S'))
                ) {
                    ans++
                }
            }
        }

        println("Part 2: $ans")
    }

    private fun dfs(
        grid: List<CharArray>,
        i: Int,
        j: Int,
        searchTerm: String,
        curr: String,
        currRev: String,
        dir: List<Pair<Int, Int>>,
    ): Int {
        if (curr == searchTerm || currRev == searchTerm) return 1
        if (!searchTerm.startsWith(curr) && !searchTerm.endsWith(currRev)) return 0

        return dir.filter {
            it.first + i in grid.indices && it.second + j in grid[i].indices
        }.sumOf {
            dfs(
                grid = grid,
                i = it.first + i,
                j = it.second + j,
                searchTerm = searchTerm,
                curr = curr + grid[it.first + i][it.second + j],
                currRev = grid[it.first + i][it.second + j] + currRev,
                dir = listOf(it),
            )
        }
    }

    private fun processInput(): List<CharArray> {
        val grid = mutableListOf<CharArray>()
        readFileLines("day4.txt") {
            grid.add(it.toCharArray())
        }

        return grid
    }
}