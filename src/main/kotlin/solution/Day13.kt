package solution

import utils.*

private data class ClawMachine(
    val buttonA: Point,
    val buttonB: Point,
    val prize: Point
)

object Day13 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val input = processInput()

        val ans = input.sumOf(::search)

        println("Part 1: $ans")
    }

    private fun part2() {
        val input = processInput()

        val ans = input.sumOf { search(it, offset = 10000000000000) }

        println("Part 2: $ans")
    }

    private fun search(machine: ClawMachine, offset: Long = 0): Long {
        val pX = (machine.prize.x + offset)
        val pY = (machine.prize.y + offset)

        // Calculate denominator
        val d = (machine.buttonB.y * machine.buttonA.x) - (machine.buttonB.x * machine.buttonA.y)

        // Calculate b presses
        val bPresses = ((pY * machine.buttonA.x) - (pX * machine.buttonA.y)) / d

        // Calculate a presses
        val aPresses = if (machine.buttonA.x != 0) {
            (pX - bPresses * machine.buttonB.x) / machine.buttonA.x
        } else {
            (pY - bPresses * machine.buttonB.y) / machine.buttonA.y
        }

        var ans = 0L

        // Check if prize reachable
        if (
            ((aPresses * machine.buttonA.x) + (bPresses * machine.buttonB.x)) == pX &&
            ((aPresses * machine.buttonA.y) + (bPresses * machine.buttonB.y)) == pY
        ) {
            ans = (aPresses * 3) + bPresses
        }

        return ans
    }

    private fun processInput(): List<ClawMachine> {
        return readFileLines("day13.txt")
            .filter { it.isNotEmpty() }
            .chunked(3)
            .map { m ->
                val numRegex = Regex("\\D+")

                val (a, b, p) = m.map { line ->
                    val (x, y) = line.split(numRegex).filter { it.isNotEmpty() }.map { it.toInt() }
                    y to x
                }

                ClawMachine(buttonA = a, buttonB = b, prize = p)
            }
    }

}