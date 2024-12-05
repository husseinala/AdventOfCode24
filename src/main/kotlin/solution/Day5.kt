package solution

import utils.readFileLines

object Day5 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val (inDegrees, updates) = processInput()

        val ans = updates.filter { update ->
            val neededBefore = mutableSetOf<Int>()

            update.all { page ->
                inDegrees[page]?.let { neededBefore.addAll(it) }

                !neededBefore.contains(page)
            }
        }.sumOf { update ->
            update[update.size / 2]
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0

        val (inDegrees, updates) = processInput()

        for (update in updates) {
            val toBePrinted = update.toMutableSet()
            val output = mutableListOf<Int>()

            while (toBePrinted.isNotEmpty()) {
                printPageWithDep(toBePrinted.first(), toBePrinted, output, inDegrees)
            }

            if (output.toList() != update) {
                ans += output[output.size / 2]
            }
        }

        println("Part 1: $ans")
    }

    private fun printPageWithDep(
        page: Int,
        toBePrinted: MutableSet<Int>,
        output: MutableList<Int>,
        inDegrees: Map<Int, Set<Int>>,
    ) {
        inDegrees[page]?.filter { toBePrinted.contains(it) }
            ?.forEach { printPageWithDep(it, toBePrinted, output, inDegrees) }


        if (toBePrinted.contains(page)) {
            toBePrinted.remove(page)
            output.add(page)
        }
    }

    private fun processInput(): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        val inDegrees = mutableMapOf<Int, MutableSet<Int>>()
        val updates = mutableListOf<List<Int>>()

        var processingOrder = true
        readFileLines("day5.txt") { line ->
            if (line == "") {
                processingOrder = false
            } else if (processingOrder) {
                val (from, to) = line.split("|")
                inDegrees.getOrPut(to.toInt()) { mutableSetOf() }.add(from.toInt())
            } else {
                updates.add(line.split(",").map { it.toInt() })
            }
        }

        return inDegrees to updates
    }
}