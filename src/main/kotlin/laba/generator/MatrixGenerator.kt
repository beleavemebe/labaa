package laba.generator
enum class MatrixGenerator(
    val tag: String,
    val getSearchTarget: GetSearchTarget
) {
    GENERATOR_A("Generator A", GetSearchTarget.STRATEGY_2N_PLUS_1) {
        override fun calcElement(n: Int, m: Int, i: Int, j: Int): Long {
            return 2L * (n / m * i + j)
        }
    },
    GENERATOR_B("Generator B", GetSearchTarget.STRATEGY_16N_PLUS_1) {
        override fun calcElement(n: Int, m: Int, i: Int, j: Int): Long {
            return 2L * (n / m * i * j)
        }
    };

    abstract fun calcElement(n: Int, m: Int, i: Int, j: Int): Long

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

enum class GetSearchTarget(
    val tag: String
) {
    STRATEGY_2N_PLUS_1("2N + 1") {
        override operator fun invoke(n: Int): Long {
            return 2L * n + 1
        }
    },
    STRATEGY_16N_PLUS_1("16N + 1") {
        override operator fun invoke(n: Int): Long {
            return 16L * n + 1
        }
    };

    abstract operator fun invoke(n: Int): Long
}
