fun main() {
    fun makeGrid(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.toCharArray().map { it.digitToInt() }
        }
    }

    fun processRow(row: List<Int>, vis: IntArray) {
        var leftMax = Int.MIN_VALUE
        var rightMax = Int.MIN_VALUE
        var left = 0
        var right = row.lastIndex
        while (right >= 0) {
            if (row[left] > leftMax) {
                vis[left] = 1
                leftMax = row[left]
            }
            if (row[right] > rightMax) {
                vis[right] = 1
                rightMax = row[right]
            }
            left += 1
            right -= 1
        }
    }

    fun processColumn(grid: List<List<Int>>, vis: Array<IntArray>, idx: Int) {
        var topMax = Int.MIN_VALUE
        var bottomMax = Int.MIN_VALUE
        var top = 0
        var bottom = grid.lastIndex
        while (bottom >= 0) {
            if(grid[top][idx] > topMax) {
                vis[top][idx] = 1
                topMax = grid[top][idx]
            }
            if(grid[bottom][idx] > bottomMax) {
                vis[bottom][idx] = 1
                bottomMax = grid[bottom][idx]
            }
            top += 1
            bottom -= 1
        }
    }
    
    fun part1(input: List<String>): Int {
        val grid = makeGrid(input)
        val lastRow = grid[0].lastIndex
        val lastCol = grid.lastIndex
        val visibility = Array(lastCol + 1) { IntArray(lastRow + 1) { 0 } }
        (0..lastRow).forEach { idx -> processRow(grid[idx], visibility[idx]) }
        (1..lastCol - 1).forEach { idx -> processColumn(grid, visibility, idx) }
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
        return score(grid[col], row) * score(grid.map { it[row] }, col)
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
