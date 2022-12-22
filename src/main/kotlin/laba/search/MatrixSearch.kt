package laba.search

import kotlin.math.max

enum class MatrixSearch(
    val tag: String
) {
    LINEAR("Linear") {
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
    BINARY_ON_ROWS("Binary on rows") {
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
                    currCol = matrix[currRow].binarySearch(target, ceiling = currCol)
                }
            }
            return false
        }
    },
    EXPONENTIAL_ON_ROWS("Exponential on rows") {
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
                    currCol = matrix[currRow].exponentialSearch(currCol, target)
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

fun LongArray.binarySearch(
    target: Long,
    startAt: Int = 0,
    ceiling: Int = lastIndex
): Int {
    var left = startAt
    var right = ceiling
    while (right - left > 1) {
        val middle = (right + left) / 2
        val element = this[middle]
        if (element == target) {
            return middle
        } else if (element < target) {
            left = middle
        } else {
            right = middle
        }
    }

    return right - 1
}

fun LongArray.exponentialSearch(
    currCol: Int,
    target: Long
): Int {
    var shift = 1
    do {
        val i = max(currCol - shift, 0)
        val element = this[i]
        shift *= 2
    } while (element > target);
    shift /= 2

    return binarySearch(
        target,
        startAt = currCol - shift,
        ceiling = currCol - shift / 2
    )
}