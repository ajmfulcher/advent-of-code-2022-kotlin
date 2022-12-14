data class Instruction(
    val direction: String,
    val moves: Int
)

fun main() {

    fun instructions(input: List<String>): ArrayDeque<Instruction> {
        return ArrayDeque<Instruction>(input.map { 
            val i = it.split(" ")
            Instruction(i[0], i[1].toInt())
        })
    }

    fun Pair<Int, Int>.moveHead(direction: String): Pair<Int, Int> {
        var x = 0
        var y = 0
        when (direction) {
            "L" -> x = -1
            "R" -> x = 1
            "U" -> y = -1
            "D" -> y = 1
        }
        return Pair(first + x, second + y)
    }

    fun Pair<Int, Int>.moveTail(h: Pair<Int, Int>): Pair<Int, Int> {
        val x = h.first - first
        val y = h.second - second
        return when {
            Math.abs(x) <= 1 && Math.abs(y) <= 1 -> this
            Math.abs(x) <= 1 -> Pair(h.first, second + y / 2)
            Math.abs(y) <= 1 -> Pair(first + x / 2, h.second)
            Math.abs(x) == 2 && Math.abs(y) == 2 -> Pair(first + x / 2, second + y / 2)
            else -> {
                throw Error()
            }
        }
    }

    fun performInstruction(
        instructions: ArrayDeque<Instruction>,
        tailPos: MutableSet<Pair<Int,Int>>,
        h: Pair<Int, Int> = Pair(0,0),
        t: Pair<Int,Int> = Pair(0,0)
    ) {
        if (instructions.isNotEmpty()) {
            val instruction = instructions.removeFirst()
            var head = h
            var tail = t
            (1..instruction.moves).forEach { _ ->
                head = head.moveHead(instruction.direction)
                tail = tail.moveTail(head)
                tailPos.add(tail)
            }
            performInstruction(instructions, tailPos, head, tail)
        }
    }
    
    fun part1(input: List<String>): Int {
        val tailPos = hashSetOf<Pair<Int,Int>>()
        val instructions = instructions(input)
        performInstruction(instructions, tailPos)
        return tailPos.size
    }

    fun moveTails(knots: Array<Pair<Int,Int>>, idx: Int = 1) {
        if (idx > knots.lastIndex) return
        val moved = knots[idx].moveTail(knots[idx - 1])
        if(knots[idx] != moved) {
            knots[idx] = moved
            moveTails(knots, idx + 1)
        }
    }

    fun performInstructionBrokenRope(
        instructions: ArrayDeque<Instruction>,
        tailPos: MutableSet<Pair<Int,Int>>,
        knots: Array<Pair<Int,Int>> = Array(10) { Pair(0,0) }
    ) {
if (instructions.isNotEmpty()) {
            val instruction = instructions.removeFirst()
            (1..instruction.moves).forEach { _ ->
                knots[0] = knots[0].moveHead(instruction.direction)
                moveTails(knots)
                tailPos.add(knots.last())
            }
            performInstructionBrokenRope(instructions, tailPos, knots)
        }
    }

    fun part2(input: List<String>): Int {
        val tailPos = hashSetOf<Pair<Int,Int>>()
        val instructions = instructions(input)
        performInstructionBrokenRope(instructions, tailPos)
        return tailPos.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part1(testInput))
    check(part1(testInput) == 13)

    val input = readInput("Day09")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1)
    println(part2(input))
}
