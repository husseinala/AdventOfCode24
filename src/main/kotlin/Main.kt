import solution.Day17
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day17.solve()
    println("Run time: ${startTime.elapsedNow()}")
}