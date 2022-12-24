package laba.generator
enum class MatrixGenerator(
    val tag: String,
    val searchStrategyTag: String,
) {
    GENERATOR_A("Generator A", "2N + 1") {
        override fun calcElement(n: Int, m: Int, i: Int, j: Int): Long {
            return 2L * (n / m * i + j)
        }

        override fun getSearchTarget(n: Int): Long {
            return 2L * n + 1
        }
    },
    GENERATOR_B("Generator B", "16N + 1") {
        override fun calcElement(n: Int, m: Int, i: Int, j: Int): Long {
            return 2L * (n / m * i * j)
        }

        override fun getSearchTarget(n: Int): Long {
            return 16L * n + 1
        }
    };

    abstract fun calcElement(n: Int, m: Int, i: Int, j: Int): Long
    abstract fun getSearchTarget(n: Int): Long

    fun generateMatrix(
        rows: Int,
        cols: Int,
    ): Array<LongArray> {
        return Array(rows) { i ->
            LongArray(cols) { j ->
                calcElement(n = cols, m = rows, i, j)
            }
        }
    }
}
