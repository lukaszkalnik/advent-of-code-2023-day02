import java.io.File

fun main(args: Array<String>) {
    val input = File("input.txt").readLines()

    input.forEach { println(it.parseGame()) }
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
    val id = """Game (\d+)""".toRegex()
        .find(gameToken)!!.groupValues[1].toInt()

    return Game(id, emptyList())
}

fun String.parseSubset(): Subset {
    TODO()
}
