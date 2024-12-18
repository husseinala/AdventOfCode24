import solution.Day18
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day18.solve()
    println("Run time: ${startTime.elapsedNow()}")
}