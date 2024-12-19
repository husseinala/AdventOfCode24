import solution.Day19
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day19.solve()
    println("Run time: ${startTime.elapsedNow()}")
}