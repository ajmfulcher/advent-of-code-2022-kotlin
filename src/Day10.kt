import kotlin.properties.Delegates

fun main() {
    
    fun part1(input: List<String>): Int {
        val strength = mutableListOf<Int>()
        var xval = 1
        var cycle by Delegates.observable(0) { prop, old, new ->
            if (new % 40 == 20) strength += new * xval
        }
        input.forEach { entry ->
            when {
                entry == "noop" -> cycle += 1
                else -> {
                    cycle += 1
                    cycle += 1
                    xval += entry.split(" ")[1].toInt()
                }
            }
        }
        return strength.fold(0) { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        val crt = Array(7) { Array<String>(40) { "" } }
        var xval = 1
        var cycle by Delegates.observable(-1) { _, _, new ->
            val x = new % 40
            val y = new / 40
            crt[y][x] = if (Math.abs(xval - x) <= 1) "#" else "."
        }
        input.forEach { entry ->
            when {
                entry == "noop" -> cycle += 1
                else -> {
                    cycle += 1
                    cycle += 1
                    xval += entry.split(" ")[1].toInt()
                }
            }
        }
        crt.forEach { line -> println(line.joinToString("")) }
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println(part1(testInput))
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1)
    println(part2(input))
}
