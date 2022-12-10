fun main() {
    
    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1)
    println(part2(input))
}
