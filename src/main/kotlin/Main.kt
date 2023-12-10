import java.io.File

val gameRegex = """Game (\d+)""".toRegex()

val redRegex = """ (\d+) red""".toRegex()
val greenRegex = """ (\d+) green""".toRegex()
val blueRegex = """ (\d+) blue""".toRegex()

val limit = Subset(
    red = 12,
    green = 13,
    blue = 14,
)

fun main() {
    val input = File("input.txt").readLines()

    val result = input.map { it.parseGame() }.filter { game -> game.subsets.none { it.exceeds(limit) } }.sumOf { it.id }
    println(result)
}

data class Game(
    val id: Int,
    val subsets: List<Subset>,
)

data class Subset(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    fun exceeds(other: Subset): Boolean = red > other.red || green > other.green || blue > other.blue
}

fun String.parseGame(): Game {
    val gameTokens = split(":")
    val gameToken = gameTokens.first()
    val id = gameRegex.find(gameToken)!!.groupValues[1].toInt()

    val subsetTokens = gameTokens[1].split(";")
    val subsets = subsetTokens.map { it.parseSubset() }

    return Game(id, subsets)
}

fun String.parseSubset(): Subset {
    val colorTokens = split(",")

    val (redToken, colorTokensNoRed) = colorTokens.partition { redRegex.matches(it) }
    val redNumber = redToken.firstOrNull()?.let { redRegex.find(it)!!.groupValues[1].toInt() } ?: 0

    val (greenToken, colorTokensNoRedOrGreen) = colorTokensNoRed.partition { greenRegex.matches(it) }
    val greenNumber = greenToken.firstOrNull()?.let { greenRegex.find(it)!!.groupValues[1].toInt() } ?: 0

    val blueNumber = colorTokensNoRedOrGreen.firstOrNull()?.let { blueRegex.find(it)!!.groupValues[1].toInt() } ?: 0

    return Subset(
        red = redNumber,
        green = greenNumber,
        blue = blueNumber,
    )
}
