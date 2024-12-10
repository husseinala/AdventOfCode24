package solution

import utils.*
import utils.CharGridUtils.liesOn

object Day8 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0

        val grid = processInput()
        val antennas = getAntennas(grid)

        for (antenna in antennas.keys) {
            val loc = antennas[antenna]!!

            for (a in loc.indices) {
                val antennaOne = loc[a]
                for (b in a + 1 until loc.size) {
                    val antennaTwo = loc[b]

                    val aChange = antennaOne - antennaTwo
                    val bChange = antennaTwo - antennaOne

                    val topPoint = antennaOne + aChange
                    val bottomPoint = antennaTwo + bChange

                    if (topPoint liesOn grid && grid[topPoint] != '#') {
                        ans++
                        grid[topPoint] = '#'
                    }

                    if (bottomPoint liesOn grid && grid[bottomPoint] != '#') {
                        ans++
                        grid[bottomPoint] = '#'
                    }

                }
            }
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0

        val grid = processInput()
        val antennas = getAntennas(grid)

        for (antenna in antennas.keys) {
            val loc = antennas[antenna]!!
            if (loc.size == 1) continue
            ans += loc.size
            for (a in loc.indices) {
                val antennaOne = loc[a]
                for (b in a + 1 until loc.size) {
                    val antennaTwo = loc[b]

                    val aChange = antennaOne - antennaTwo
                    val bChange = antennaTwo - antennaOne

                    var topPoint = antennaOne + aChange
                    var bottomPoint = antennaTwo + bChange

                    while (topPoint liesOn grid) {
                        if (grid[topPoint] == '.') {
                            ans++
                            grid[topPoint] = '#'
                        }

                        topPoint += aChange
                    }

                    while (bottomPoint liesOn grid) {
                        if (grid[bottomPoint] == '.') {
                            ans++
                            grid[bottomPoint] = '#'
                        }

                        bottomPoint += bChange
                    }
                }
            }
        }

        println("Part 1: $ans")
    }

    private fun getAntennas(grid: List<CharArray>): Map<Char, List<Point>> {
        val antennas = mutableMapOf<Char, MutableList<Point>>()

        for (i in grid.indices) {
            for (j in grid.indices) {
                if (grid[i][j] != '.') {
                    antennas.getOrPut(grid[i][j]) { mutableListOf() }.add(i to j)
                }
            }
        }

        return antennas
    }

    private fun processInput(): List<CharArray> {
        return readFileLines("day8.txt").map { it.toCharArray() }
    }
}