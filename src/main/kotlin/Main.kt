import solution.Day16
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day16.solve()
    println("Run time: ${startTime.elapsedNow()}")
}