import java.math.BigInteger

class Operation(private val op: (BigInteger, BigInteger) -> BigInteger) {
    fun apply(operand: BigInteger, i: BigInteger) = op(operand, i)
}

fun operation(sign: String): Operation = when(sign) {
    "+" -> Operation { a, b -> a.plus(b) }
    "-" -> Operation { a, b -> a.minus(b) }
    "/" -> Operation { a, b -> a.div(b) }
    "*" -> Operation { a, b -> a.times(b) }
    else -> throw Error()
}

typealias ThrowTo = (Int, BigInteger) -> Unit
typealias ManageWorries = (BigInteger) -> BigInteger

class Monkey(
    private val operation: Operation,
    val divisor: BigInteger,
    private val trueIdx: Int,
    private val falseIdx: Int,
    startItems: List<BigInteger>,
    private val operand: BigInteger?
) {
    private val items = ArrayDeque<BigInteger>(startItems)
    var inspections = 0.toBigInteger()

    fun takeTurn(manageWorries: ManageWorries, throwTo: ThrowTo) {
        while (items.isNotEmpty()) {
            val item = items.removeFirst()
            val o = operand ?: item
            val worryLevel = manageWorries(operation.apply(o, item))
            if (worryLevel.rem(divisor) == 0.toBigInteger()) throwTo(trueIdx, worryLevel) else throwTo(falseIdx, worryLevel)
            inspections += 1.toBigInteger()
        }
    }

    fun catch(item: BigInteger) {
        items += item
    }
}

fun main() {

    fun createMonkey(
        block: List<String>
    ): Monkey {
        val startItems = block[1].split(":")[1].trim().split(", ").map { BigInteger(it) }
        val op = block[2].split("=")[1].trim().split(" ")
        val sign = op[1]
        val operand = op[2]
        val divisor = BigInteger(block[3].split(" ").last())
        val trueIdx = block[4].split(" ").last().toInt()
        val falseIdx = block[5].split(" ").last().toInt()
        return Monkey(
            operation = operation(sign),
            divisor = divisor,
            trueIdx = trueIdx,
            falseIdx = falseIdx,
            startItems = startItems,
            operand = if (operand == "old") null else BigInteger(operand)
        )
    }

    fun createMonkeys(input: List<String>): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        (0..input.lastIndex step 7).forEach { idx -> 
            monkeys.add(createMonkey(input.subList(idx, input.size)))
        }
        return monkeys
    }

    fun List<Monkey>.productOfTwoLargest() = map { monkey -> monkey.inspections }
            .sortedDescending()
            .take(2)
            .reduce { a, b -> a.times(b) }
    
    fun part1(input: List<String>): BigInteger {
        val monkeys = createMonkeys(input)

        val throwTo: ThrowTo = { idx, item -> monkeys[idx].catch(item) }
        val manageWorries: ManageWorries = { i -> i.div(3.toBigInteger()) }

        (1..20).forEach { _ ->
            monkeys.forEach { monkey -> monkey.takeTurn(manageWorries, throwTo) }
        }
        return monkeys.productOfTwoLargest()
    }

    fun part2(input: List<String>): BigInteger {
        val monkeys = createMonkeys(input)
        val modulo = monkeys.map { it.divisor }.reduce { acc, i -> acc.times(i) }

        val throwTo: ThrowTo = { idx, item -> monkeys[idx].catch(item) }
        val manageWorries: ManageWorries = { i -> i.rem(modulo) }
        
        (1..10000).forEach { round ->
            monkeys.forEach { monkey -> monkey.takeTurn(manageWorries, throwTo) }
        }
        return monkeys.productOfTwoLargest()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    println(part1(testInput))
    check(part1(testInput) == 10605.toBigInteger())

    val input = readInput("Day11")
    println(part1(input))
    check(part1(input) == 101436.toBigInteger())

    println(part2(testInput))
    check(part2(testInput) == 2713310158.toBigInteger())
    println(part2(input))
}
