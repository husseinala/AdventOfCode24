package utils

object GridDirections {
    val up = -1 to 0
    val right = 0 to 1
    val down = 1 to 0
    val left = 0 to -1

    // diagonal
    val upRight = -1 to 1
    val upLeft = -1 to -1
    val downRight = 1 to 1
    val downLeft = 1 to -1

    val nonDiagDirs = listOf(up, right, down, left)
    val dirs = listOf(
        up, right, down, left,
        upRight, upLeft, downRight, downLeft
    )
}

typealias Point = Pair<Int, Int>

val Point.i get() = first
val Point.j get() = second

val Point.y get() = i
val Point.x get() = j

object CharGridUtils {
    infix fun Point.liesOn(grid: List<CharArray>) = i in grid.indices && j in grid[i].indices
}

object ListGridUtils {
    infix fun <T : Collection<A>, A> Point.liesOn(grid: List<T>) = i in grid.indices && j in grid[i].indices
}

infix fun Point.liesOn(grid: Pair<Point, Point>) = i in grid.first.i..grid.second.i && j in grid.first.j..grid.second.j

operator fun Point.plus(other: Point): Point = i + other.i to j + other.j

operator fun Point.minus(other: Point): Point = i - other.i to j - other.j

operator fun List<CharArray>.get(p: Point) = this[p.i][p.j]
operator fun <T> List<List<T>>.get(p: Point) = this[p.i][p.j]

operator fun List<CharArray>.set(p: Point, value: Char) {
    this[p.i][p.j] = value
}