import solution.Day15
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day15.solve()
    println("Run time: ${startTime.elapsedNow()}")
}