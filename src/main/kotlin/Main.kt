import solution.Day13
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day13.solve()
    println("Run time: ${startTime.elapsedNow()}")
}