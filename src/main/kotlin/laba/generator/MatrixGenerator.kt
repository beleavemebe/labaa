package laba.generator
enum class MatrixGenerator(
    val tag: String,
    val getSearchTarget: GetSearchTarget
) {
    GENERATOR_A("Generator A", GetSearchTarget.STRATEGY_2N_PLUS_1) {
        override fun calcElement(cols: Int, rows: Int, row: Int, col: Int): Long {
            return 2L * (cols / rows * row + col)
        }
    },
    GENERATOR_B("Generator B", GetSearchTarget.STRATEGY_16N_PLUS_1) {
        override fun calcElement(cols: Int, rows: Int, row: Int, col: Int): Long {
            return 2L * (cols / rows * row * col)
        }
    };

    protected abstract fun calcElement(cols: Int, rows: Int, row: Int, col: Int): Long

    fun generateMatrix(
        rows: Int,
        cols: Int,
    ): Array<LongArray> {
        return Array(rows) { row ->
            LongArray(cols) { col ->
                calcElement(cols, rows, row, col)
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
