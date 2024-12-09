package solution

import utils.readFileLines

object Day9 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0L

        val input = processInput()

        val map = buildList {
            var isSpace = false
            var id = 0

            input.forEach {
                if (isSpace) {
                    repeat(it) { add(".") }
                } else {
                    repeat(it) { add(id.toString()) }
                    id++
                }

                isSpace = !isSpace
            }
        }

        var left = 0
        var right = map.size - 1

        while (left <= right) {
            if (map[left] == ".") {
                if (map[right] != ".") {
                    ans += map[right].toInt() * left
                    left++
                }
                right--
            } else {
                ans += map[left].toInt() * left
                left++
            }
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0L

        val input = processInput()

        val map = buildList {
            var isSpace = false
            var id = 0

            input.forEach {
                if (isSpace) {
                    add(-1 to it)
                } else {
                    add(id to it)
                    id++
                }

                isSpace = !isSpace
            }
        }.toMutableList()

        var left = 0
        var right = map.size - 1

        var realIndex = 0

        while (left <= right) {
            if (map[left].first == -1) {
                if (map[right].first != -1 && map[right].second <= map[left].second) {
                    repeat(map[right].second) {
                        ans += map[right].first * realIndex
                        realIndex++
                    }
                    if (map[left].second - map[right].second > 0) {
                        map[left] = -1 to map[left].second - map[right].second
                    } else {
                        left++
                    }
                    map[right] = -1 to map[right].second
                    right = map.size - 1
                } else if (right == left) {
                    right = map.size - 1
                    realIndex += map[left].second
                    left++
                } else {
                    right--
                }
            } else {
                repeat(map[left].second) {
                    ans += map[left].first * realIndex
                    realIndex++
                }
                left++
            }
        }

        println("Part 2: $ans")
    }

    private fun processInput(): List<Int> {
        return readFileLines("day9.txt").joinToString("").map { it.digitToInt() }
    }

}