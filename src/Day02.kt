fun main() {
    val scores = hashMapOf(
        "A" to 1,
        "X" to 1,
        "B" to 2,
        "Y" to 2,
        "C" to 3,
        "Z" to 3
    )

    fun calculateScore(line: String): Int {
        val them = scores[line.substring(0,1)]!!
        val us = scores[line.substring(2,3)]!!
        return when {
            them == us -> 3 + us
            them == 3 && us == 1 -> 6 + us
            them == 1 && us == 3 -> 0 + us
            them + 1 == us -> 6 + us
            them - 1 == us -> 0 + us
            else -> 0
        }
    }

    fun calculateScoreFromShape(line: String): Int {
        val them = scores[line.substring(0,1)]!!
        val result = line.substring(2,3)
        return when {
            result == "Y" -> them + 3
            result == "X" && them == 1 -> 0 + 3
            result == "Z" && them == 3 -> 6 + 1
            result == "X" -> 0 + them - 1
            result == "Z" -> 6 + them + 1
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map { calculateScore(it) }
            .fold(0) { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { calculateScoreFromShape(it) }
            .fold(0) { acc, i -> acc + i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 12)
    println(part2(input))
}
