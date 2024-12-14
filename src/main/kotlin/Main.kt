import solution.Day14
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day14.solve()
    println("Run time: ${startTime.elapsedNow()}")
}