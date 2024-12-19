package solution

import utils.*

object Day19 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val (patterns, designs) = processInput()

        val ans = designs.count { getPossibleDesignArrangements(it, patterns, mutableMapOf()) >= 1 }

        println("Part 1: $ans")
    }

    private fun part2() {
        val (patterns, designs) = processInput()

        val ans = designs.sumOf { getPossibleDesignArrangements(it, patterns, mutableMapOf()) }

        println("Part 2: $ans")
    }

    private fun getPossibleDesignArrangements(
        design: String, patterns: Map<Char, List<String>>, memo: MutableMap<String, Long>,
    ): Long {
        if (design.isEmpty()) return 1
        if (memo.contains(design)) return memo[design]!!

        val ans = patterns[design[0]]?.filter { design.startsWith(it) }?.sumOf {
            getPossibleDesignArrangements(design.removePrefix(it), patterns, memo)
        } ?: 0

        memo[design] = ans
        return ans
    }

    private fun processInput(): Pair<Map<Char, List<String>>, List<String>> {
        return readFileAsString("day19.txt", "\n").let { input ->
            val (pattern, designs) = input.split("\n\n")

            pattern.split(",").map { it.trim() }.groupBy { it[0] } to designs.split("\n")
        }
    }
}