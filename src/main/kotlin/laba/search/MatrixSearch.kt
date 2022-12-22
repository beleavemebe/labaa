package laba.search

enum class MatrixSearch(
    val searchName: String
) {
    LINEAR("Linear O(N+M)") {
        override fun find(
            target: Long,
            matrix: Array<LongArray>
        ): Boolean {
            var currRow = 0
            var currCol = matrix[0].size - 1
            while (currRow < matrix.size && currCol > -1) {
                val element = matrix[currRow][currCol]
                if (element == target) {
                    return true
                } else if (element < target) {
                    currRow++
                } else {
                    currCol--
                }
            }
            return false
        }
    },
    BINARY_ON_ROWS("Binary on rows O(Mlog(N))") {
        override fun find(
            target: Long,
            matrix: Array<LongArray>
        ): Boolean {
            var currRow = 0
            var currCol = matrix[0].size - 1
            while (currRow < matrix.size && currCol > -1) {
                if (matrix[currRow][currCol] == target) {
                    return true
                } else if (matrix[currRow][currCol] < target) {
                    currRow++
                } else {
                    currCol = matrix[currRow].findLastLeq(target, currCol)
                }
            }
            return false
        }
    },
    EXPONENTIAL_ON_ROWS("Exponential on rows O(M(log(N) + log(M) + 1))") {
        override fun find(
            target: Long,
            matrix: Array<LongArray>
        ): Boolean {
            var currRow = 0
            var currCol = matrix[0].size - 1
            while (currRow < matrix.size && currCol > -1) {
                if (matrix[currRow][currCol] == target) {
                    return true
                } else if (matrix[currRow][currCol] < target) {
                    currRow++
                } else {
                    var shift = 2
                    if (currCol <= 32) {
                        shift = 0
                    } else {
                        while (
                            currCol >= shift &&
                            matrix[currRow][currCol - shift] > target
                        ) {
                            shift *= 2
                        }
                        shift /= 2
                    }
                    currCol = matrix[currRow].findLastLeq(target, currCol - shift)
                }
            }
            return false
        }
    };

    abstract fun find(
        target: Long,
        matrix: Array<LongArray>
    ): Boolean
}

fun LongArray.findLastLeq(
    target: Long,
    ceiling: Int
): Int {
    var currLeft = -1
    var currRight = ceiling
    while (currRight - currLeft > 1) {
        val middle = (currRight + currLeft) / 2
        if (this[middle] > target) {
            currRight = middle
        } else {
            currLeft = middle
        }
    }
    return currRight - 1
}