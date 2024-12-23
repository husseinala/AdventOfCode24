import solution.Day23
import kotlin.time.TimeSource

fun main() {
    val startTime = TimeSource.Monotonic.markNow()
    Day23.solve()
    println("Run time: ${startTime.elapsedNow()}")
}