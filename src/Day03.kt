fun main() {

    val alphabetMap by lazy {
        val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val asMap = hashMapOf<Char, Int>()
        alphabet.forEachIndexed { idx, item ->
            asMap.put(item, idx + 1)
        }
        asMap
    }

    fun commonItems(sack: String): Set<Char> {
        val asArray = sack.toCharArray()
        val firstSack = asArray.copyOfRange(0, asArray.size / 2)
        val secondSack = asArray.copyOfRange(asArray.size / 2, asArray.size)
        return firstSack.intersect(secondSack.toList())
    }

    fun Char.priority(): Int {
        val score = alphabetMap[lowercaseChar()]!!
        return if (isLowerCase()) score else score + 26
    }

    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { sack ->
            total += commonItems(sack)
                .map { it.priority() }
                .reduce { acc, i -> acc + i }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        (0..input.lastIndex step 3).forEach { i ->
            total += input[i].toCharArray()
                .intersect(input[i + 1].toCharArray().toList())
                .intersect(input[i + 2].toCharArray().toList())
                .first()
                .priority()
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 70)
    println(part2(input))
}
