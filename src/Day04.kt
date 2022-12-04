fun main() {

    fun parse(line: String): List<List<Int>> = line
        .split(",")
        .map { it.split("-").map { it.toInt() } }

    fun fullyContains(pair1: List<Int>, pair2: List<Int>): Boolean {
        if (pair1[0] == pair2[0] || pair1[1] == pair2[1]) return true
        if (pair1[0] > pair2[0]) return fullyContains(pair2, pair1)
        return pair1[1] > pair2[1]
    }

    fun overlaps(pair1: List<Int>, pair2: List<Int>): Boolean {
        if (pair1[0] > pair2[0]) return overlaps(pair2, pair1)
        return pair1[1] >= pair2[0]
    }

    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val p = parse(line)
            if (fullyContains(p[0], p[1])) total += 1
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val p = parse(line)
            if (overlaps(p[0], p[1])) total += 1
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 4)
    println(part2(input))
}
