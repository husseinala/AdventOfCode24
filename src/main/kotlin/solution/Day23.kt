package solution

import utils.*

object Day23 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val connections = processInput()

        val sets = mutableSetOf<String>()

        connections.forEach { (key, value) ->
            if (value.size > 1) {
                value.combos()
                    .filter { (a, b) -> connections[a]?.contains(b) == true }
                    .map { (a, b) -> listOf(key, a, b) }
                    .map { it.sorted() }
                    .filter { it.any { it.startsWith('t') } }
                    .forEach { sets.add(it.joinToString()) }
            }
        }

        println("Part 1: ${sets.size}")
    }

    private fun part2() {
        val connections = processInput()

        val password = connections.entries.sortedBy { it.value.size }.fold(emptyList<String>()) { currentMax, pair ->
            val (key, value) = pair

            if (currentMax.size < value.size) {
                val res = findMaxConnections(nodes = value, connections = connections)
                if (currentMax.size - 1 < res.size) {
                    return@fold listOf(key) + res
                }
            }

            currentMax
        }
            .sorted()
            .joinToString(",")

        println("Part 2: $password")
    }

    private fun findMaxConnections(
        start: Int = 0,
        nodesTaken: MutableList<String> = mutableListOf(),
        nodes: List<String>,
        connections: Map<String, List<String>>,
        currentMax: Int = 0
    ): List<String> {
        if (start == nodes.size) return nodesTaken.toList()
        if (nodesTaken.size + (nodes.size - (start + 1)) <= currentMax) return emptyList()

        var max = emptyList<String>()
        for (i in start until nodes.size) {
            val node = nodes[i]
            if (nodesTaken.isEmpty() || nodesTaken.all { connections[it]!!.contains(node) }) {
                nodesTaken.add(node)
                val a = findMaxConnections(i + 1, nodesTaken, nodes, connections, max.size)
                if (max.size < a.size) max = a
                nodesTaken.removeLast()
            }
        }

        return max
    }

    private fun processInput(): Map<String, List<String>> {
        val input = readFileLines("day23.txt").map { it.split("-") }

        val connections = mutableMapOf<String, MutableList<String>>()

        input.forEach { (a, b) ->
            connections.getOrPut(a) { mutableListOf() }.add(b)
            connections.getOrPut(b) { mutableListOf() }.add(a)
        }

        return connections
    }

    private fun <T> List<T>.combos(): List<Pair<T, T>> {
        return flatMapIndexed { index, a ->
            take(index + 1).mapNotNull { b -> a to b }
        }
    }
}