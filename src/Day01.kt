fun main() {
    fun part1(input: List<String>): Int {
        var curr = 0
        var largest = 0
        input.forEach { item ->
            when (item) {
                "" -> {
                    if (curr > largest) largest = curr
                    curr = 0
                }
                else -> curr += item.toInt()
            }
        }
        return largest
    }

    fun part2(input: List<String>): Int {
        val ec = mutableListOf<Int>(0)
        input.forEach { item ->
            when(item) {
                "" -> ec.add(0)
                else -> ec[ec.lastIndex] += item.toInt()
            }
        }
        return ec
            .sortedDescending()
            .take(3)
            .fold(0) { acc, i -> acc + i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}
