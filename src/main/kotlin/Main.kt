import solution.Day21
import solution.Day22
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day21.solve()
    println("Run time: ${startTime.elapsedNow()}")
}