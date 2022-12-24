package laba.search

import kotlin.math.max

enum class MatrixSearch(
    val tag: String
) {
    LADDER("Ladder") {
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
            for (row in matrix) {
                val isFound = row.binarySearch(target) != -1
                if (isFound) {
                    return true
                }
            }
            return false
        }
    },
    EXPONENTIAL_LADDER("Exponential ladder") {
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
                    currCol = matrix[currRow].exponentialSearch(target, end = currCol)
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

tailrec fun LongArray.binarySearch(
    target: Long,
    left: Int = 0,
    right: Int = lastIndex,
    returnFinalIndex: Boolean = false,
): Int {
    val middle = (right + left) / 2
    val element = this[middle]
    return if (left > right) {
        if (returnFinalIndex) left - 1 else -1
    } else if (target == element) {
        middle
    } else if (target < element) {
        binarySearch(target, left, middle - 1, returnFinalIndex)
    } else {
        binarySearch(target, middle + 1, right, returnFinalIndex)
    }
}

tailrec fun LongArray.exponentialSearch(
    target: Long,
    start: Int = 0,
    end: Int = lastIndex,
    shift: Int = 2
): Int {
    val i = max(end - shift, start)
    val element = this[i]
    return if (element == target) {
        i
    } else if (element < target || i == start) {
        binarySearch(target, i, end, true)
    } else {
        exponentialSearch(target, start, i, shift * 2)
    }
}
