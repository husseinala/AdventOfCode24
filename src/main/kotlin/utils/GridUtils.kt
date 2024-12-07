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

    val dirs = listOf(
        up, right, down, left,
        upRight, upLeft, downRight, downLeft
    )
}