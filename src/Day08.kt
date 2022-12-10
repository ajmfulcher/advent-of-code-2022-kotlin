fun main() {
    fun makeGrid(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.toCharArray().map { it.digitToInt() }
        }
    }

    fun List<List<Int>>.row(idx: Int) = this[idx]
    fun List<List<Int>>.col(idx: Int) = map { it[idx] }

    fun findVisible(row: List<Int>, isVisible: (Int) -> Unit) {
        var leftMax = Int.MIN_VALUE
        var rightMax = Int.MIN_VALUE
        var left = 0
        var right = row.lastIndex
        while (right >= 0) {
            if (row[left] > leftMax) {
                isVisible(left)
                leftMax = row[left]
            }
            if (row[right] > rightMax) {
                isVisible(right)
                rightMax = row[right]
            }
            left += 1
            right -= 1
        }
    }

    fun part1(input: List<String>): Int {
        val grid = makeGrid(input)
        val lastRow = grid[0].lastIndex
        val lastCol = grid.lastIndex
        val visibility = Array(lastCol + 1) { IntArray(lastRow + 1) { 0 } }
        (0..lastRow).forEach { idx -> 
            findVisible(grid.row(idx)) { col -> visibility[idx][col] = 1 }
        }
        (1..lastCol - 1).forEach { idx -> 
            findVisible(grid.col(idx)) { row -> visibility[row][idx] = 1 }
        }
        return visibility
            .map { it.fold(0) { acc, i -> acc + i } }
            .fold(0) { acc, i -> acc + i }
    }

    fun scoreLinear(line: List<Int>, height: Int): Int {
        var score = 0
        (0..line.lastIndex).forEach { idx ->
            score += 1
            if (line[idx] >= height) return score
        }
        return score
    }

    fun score(line: List<Int>, idx: Int): Int {
        if (idx == 0 || idx == line.lastIndex) return 0
        return scoreLinear(line.subList(idx + 1, line.size), line[idx]) * scoreLinear(line.subList(0, idx).reversed(), line[idx])
    }

    fun viewingScore(grid: List<List<Int>>, row: Int, col: Int): Int {
        return score(grid.row(col), row) * score(grid.col(row), col)
    }

    fun part2(input: List<String>): Int {
        val grid = makeGrid(input)
        val lastRow = grid[0].lastIndex
        val lastCol = grid.lastIndex
        var maxScore = 0
        (0..lastRow).forEach { row ->
            (0..lastCol).forEach { col ->
                val score = viewingScore(grid, row, col)
                if (score > maxScore) maxScore = score
            }
        }
        return maxScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println(part1(testInput))
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 8)
    println(part2(input))
}
