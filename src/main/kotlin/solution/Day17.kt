package solution

import androidx.compose.runtime.toMutableStateMap
import utils.readFileAsString
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.truncate

private const val OP_ADV = 0
private const val OP_BXL = 1
private const val OP_BST = 2
private const val OP_JNZ = 3
private const val OP_BXC = 4
private const val OP_OUT = 5
private const val OP_BDV = 6
private const val OP_CDV = 7

object Day17 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val (registry, program) = processInput()

        val output = runProgram(registry, program)

        println("Part 1: " + output.joinToString(","))
    }

    private fun part2() {
        val (_, program) = processInput()

        val n = IntArray(64)
        var pointer = 0

        fun IntArray.asLong() = mapIndexed { index, n -> ((8.0.pow(pointer - index).toLong()) * n) }.sum()

        while (true) {
            val a = n.asLong()
            val output = runProgram(mutableMapOf("A" to a, "B" to 0, "C" to 0), program)

            if (output == program.takeLast(pointer + 1)) {
                if (output.size == program.size) break
                pointer++
            } else {
                n[pointer]++
            }
        }

        println("Part 2: ${n.asLong()}")
    }

    private fun runProgram(registry: MutableMap<String, Long>, program: List<Int>): MutableList<Int> {
        val output = mutableListOf<Int>()

        var position = 0

        while (position < program.size - 1) {
            val opcode = program[position]
            val operand = program[position + 1]
            val comboOperand = when (operand) {
                4 -> registry["A"]
                5 -> registry["B"]
                6 -> registry["C"]
                else -> operand.toLong()
            }!!

            when (opcode) {
                OP_ADV -> registry["A"] = truncate(registry["A"]!! / (2.0.pow(comboOperand.toDouble()))).toLong()
                OP_BXL -> registry["B"] = registry["B"]!! xor operand.toLong()
                OP_BST -> registry["B"] = comboOperand % 8
                OP_JNZ -> if (registry["A"] != 0L) {
                    position = operand
                    continue
                }

                OP_BXC -> registry["B"] = registry["B"]!! xor registry["C"]!!
                OP_OUT -> output.add((comboOperand % 8).toInt())
                OP_BDV -> registry["B"] = truncate(registry["A"]!! / (2.0.pow(comboOperand.toDouble()))).toLong()
                OP_CDV -> registry["C"] = truncate(registry["A"]!! / (2.0.pow(comboOperand.toDouble()))).toLong()
            }

            position += 2
        }

        return output
    }


    private fun processInput(): Pair<MutableMap<String, Long>, List<Int>> {
        return readFileAsString("day17.txt", "\n")
            .split("\n\n")
            .let { (inst, program) ->
                inst.split("\n").map {
                    val (id, type) = it.removePrefix("Register ").split(":")

                    id to type.trim().toLong()
                }.toMutableStateMap() to program.removePrefix("Program: ").split(",").map { it.toInt() }
            }
    }
}