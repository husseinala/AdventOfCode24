import solution.Day20
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day20.solve()
    println("Run time: ${startTime.elapsedNow()}")
}