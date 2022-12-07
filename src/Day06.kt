fun main() {

    fun findUniqueBlockEnd(input: String, size: Int): Int {
        val line = input.toCharArray()
        val set = HashSet<Char>()
        var idx = 0
        while (set.size != size) {
            set.clear()
            set.addAll(line.copyOfRange(idx, idx + size).toList())
            idx += 1
        }
        return idx + size - 1
    }

    fun part1(input: List<String>): Int {
        return findUniqueBlockEnd(input[0], 4)
    }

    fun part2(input: List<String>): Int {
        return findUniqueBlockEnd(input[0], 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part1(testInput))
    check(part1(testInput) == 7)

    val input = readInput("Day06")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 19)
    println(part2(input))
}
