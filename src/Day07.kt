sealed class Node {
    var size = 0
}

data class Directory(
    val name: String
): Node() {
    val nodes: MutableList<Node> = mutableListOf<Node>()
    var parent: Directory? = null
}

data class File(
    val name: String,
): Node()

// Test output
// $ cd /
// $ ls
// dir a
// 14848514 b.txt
// 8504156 c.dat
// dir d
// $ cd a
// $ ls
// dir e
// 29116 f
// 2557 g
// 62596 h.lst
// $ cd e
// $ ls
// 584 i
// $ cd ..
// $ cd ..
// $ cd d
// $ ls
// 4060174 j
// 8033020 d.log
// 5626152 d.ext
// 7214296 k

fun main() {
    fun Directory.findDirectory(name: String): Directory {
        return nodes.first { it is Directory && it.name == name } as Directory
    }

    fun Directory.directories() = nodes.mapNotNull { it as? Directory }
    fun Directory.totalSize() = nodes.map { it.size }.fold(0) { acc, i -> acc + i }

    fun create(input: List<String>): Directory {
        val root = Directory("/")
        val dummy = Directory("")
        dummy.nodes.add(root)
        root.parent = dummy
        var current = dummy

        input.forEach { line -> 
            when {
                line == "$ cd .." -> current = current.parent ?: throw Error()
                line.startsWith("$ cd") -> current = current.findDirectory(line.split(" ")[2])
                line.startsWith("dir") -> current.nodes.add(
                    Directory(name = line.split(" ")[1]).apply { parent = current }
                    )
                line.matches(Regex("^\\d+.*")) -> {
                    val e = line.split(" ")
                    current.nodes.add(
                        File(name = e[1]).apply { size = e[0].toInt() }
                    )
                }
            }
        }
        return root
    }

    fun computeSizes(dir: Directory, sizes: MutableList<Int>) {
        val directories = dir.directories()
        directories.forEach { d -> computeSizes(d, sizes) }
        dir.size = dir.totalSize()
        sizes.add(dir.size)
    }

    fun part1(input: List<String>): Int {
        val fs = create(input)
        val sizes = mutableListOf<Int>()
        computeSizes(fs, sizes)
        return sizes
            .filter{ it <= 100000 }
            .fold(0) { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        val fs = create(input)
        val sizes = mutableListOf<Int>()
        computeSizes(fs, sizes)
        val unused = 70000000 - fs.size
        return sizes
            .filter { it >= 30000000 - unused }
            .sorted()
            .first()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 95437)

    val input = readInput("Day07")
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 24933642)
    println(part2(input))
}
