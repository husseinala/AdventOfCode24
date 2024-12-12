import solution.Day12
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day12.solve()
    println("Run time: ${startTime.elapsedNow()}")
}