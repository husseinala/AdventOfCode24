package solution

import utils.CharGridUtils.liesOn
import utils.GridDirections.down
import utils.GridDirections.left
import utils.GridDirections.right
import utils.GridDirections.up
import utils.Point
import utils.get
import utils.plus
import utils.readFileLines

private data class Step(
    val point: Point,
    val dir: Point,
    val points: Long,
    val tiles: Set<Point>,
)

object Day16 {

    fun solve() {
        val ans = bfs(processInput())
        part1(ans)
        part2(ans)
    }

    private fun part1(ans: Pair<Long, Set<Point>>) {
        println("Part 1: ${ans.first}")
    }

    private fun part2(ans: Pair<Long, Set<Point>>) {
        println("Part 2: ${ans.second.size}")
    }

    private fun bfs(map: List<CharArray>): Pair<Long, Set<Point>> {
        var startPoint = 0 to 0

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'S') {
                    startPoint = i to j
                    break
                }
            }
        }

        var ans = Long.MAX_VALUE to emptySet<Point>()
        val queue = ArrayDeque<Step>()
        val visited = mutableMapOf<String, Long>()

        queue.addLast(Step(startPoint, right, 0, emptySet()))

        while (queue.isNotEmpty()) {
            var (p, dir, total, tiles) = queue.removeFirst()

            tiles += p

            if (map[p] == 'E') {
                if (ans.first > total) {
                    ans = total to tiles
                } else if (ans.first == total) {
                    ans = ans.copy(second = ans.second + tiles)
                }
            } else {
                visited["$p-$dir"] = total
                buildList {
                    add(dir to 1)
                    if (dir == up || dir == down) {
                        add(left to 1001)
                        add(right to 1001)
                    } else {
                        add(up to 1001)
                        add(down to 1001)
                    }
                }
                    .filter { (dir, points) ->
                        p + dir liesOn map && map[p + dir] != '#' && total + points < ans.first && (!visited.contains(
                            "${p + dir}-$dir"
                        ) || visited["${p + dir}-$dir"]!! > total + points)
                    }
                    .forEach {
                        queue.addLast(Step(p + it.first, it.first, total + it.second, tiles))
                    }
            }
        }

        return ans
    }

    private fun processInput(): List<CharArray> {
        return readFileLines("day16.txt").map { it.toCharArray() }
    }
}