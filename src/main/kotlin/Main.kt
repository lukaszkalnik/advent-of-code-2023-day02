import java.io.File

val gameRegex = """Game (\d+)""".toRegex()

val redRegex = """ (\d+) red""".toRegex()
val greenRegex = """ (\d+) green""".toRegex()
val blueRegex = """ (\d+) blue""".toRegex()

fun main() {
    val input = File("input.txt").readLines()

    val result = input.map {
        val minSubset = it.parseGame().subsets.minimumCommonSubset()
        minSubset.red * minSubset.green * minSubset.blue
    }.sum()
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
)

fun String.parseGame(): Game {
    val gameTokens = split(":")
    val gameToken = gameTokens.first()
    val id = gameRegex.find(gameToken)!!.groupValues[1].toInt()

    val subsetTokens = gameTokens[1].split(";")
    val subsets = subsetTokens.map { it.parseSubset() }

    return Game(id, subsets)
}

fun String.parseSubset(): Subset {
    val colorTokens = split(",").asSequence()

    val redNumber = colorTokens.findNumber(redRegex)
    val greenNumber = colorTokens.findNumber(greenRegex)
    val blueNumber = colorTokens.findNumber(blueRegex)

    return Subset(
        red = redNumber,
        green = greenNumber,
        blue = blueNumber,
    )
}

private fun Sequence<String>.findNumber(regex: Regex): Int =
    map { regex.find(it)?.groupValues?.get(1)?.toInt() }
        .find { it != null }
        ?: 0

fun List<Subset>.minimumCommonSubset(): Subset {
    val red = maxOf { it.red }
    val green = maxOf { it.green }
    val blue = maxOf { it.blue }
    return Subset(
        red = red,
        green = green,
        blue = blue,
    )
}
