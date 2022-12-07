fun main() {

    fun createStacks(input: List<String>): List<ArrayDeque<String>> {
        var stackIndex = 0
        while (input[stackIndex].substring(1,2) != "1") {
            stackIndex += 1
        }
        val stacks = input[stackIndex]
            .trim()
            .split("\\s+".toRegex())
            .map { ArrayDeque<String>() }
        (stackIndex - 1 downTo 0).forEach { idx ->
            input[idx]
                .toCharArray()
                .forEachIndexed { i, c -> 
                    if(i % 4 == 1 && c.toString().isNotBlank()) {
                        val index = i / 4
                        val letter = c.toString()
                        stacks[index].addLast(letter)
                    }
                }
        }
        return stacks
    }

    fun parseInstruction(instruction: String): IntArray {
        // move 1 from 2 to 1
        val e = instruction.split(" ")
        return intArrayOf(e[1].toInt(), e[3].toInt() - 1, e[5].toInt() - 1)
    }

    fun applyInstruction(stacks: List<ArrayDeque<String>>, instruction: String) {
        val e = parseInstruction(instruction)
        (1..e[0]).forEach { _ ->
            stacks[e[2]].addLast(stacks[e[1]].removeLast())
        }
    }

    fun applyInstructionBulk(stacks: List<ArrayDeque<String>>, instruction: String) {
        val e = parseInstruction(instruction)
        val holder = ArrayDeque<String>()
        (1..e[0]).forEach { _ ->
            holder.addLast(stacks[e[1]].removeLast())
        }
        while(holder.isNotEmpty()) {
            stacks[e[2]].addLast(holder.removeLast())
        }
    }

    fun instructionStartIndex(input: List<String>): Int {
        var idx = 0
        while (input[idx].isNotEmpty()) {
            idx += 1
        }
        return idx + 1
    }

    fun part1(input: List<String>): String {
        val stacks = createStacks(input)
        (instructionStartIndex(input)..input.lastIndex).forEach { idx ->
            applyInstruction(stacks, input[idx])
        }
        return stacks.map { it.lastOrNull() ?: "" }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stacks = createStacks(input)
        (instructionStartIndex(input)..input.lastIndex).forEach { idx ->
            applyInstructionBulk(stacks, input[idx])
        }
        return stacks.map { it.lastOrNull() ?: "" }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput))
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}
