package laba.benchmark

import laba.generator.GetSearchTarget
import laba.generator.MatrixGenerator
import laba.search.MatrixSearch
import kotlin.system.measureNanoTime


fun main() {
    runBenchmark()
}

fun runBenchmark() = repeat(2) { run ->
    val isWarmupRun = run == 0
    val generators = MatrixGenerator.values()
    val matrixSearches = MatrixSearch.values()
    val rowsValues = listOf(2, 4, 8, 16, 32, 128, 256, 512, 1024, 2048, 4096, 8192)
    val cols = 8192

    for (generator in generators) {
        for (matrixSearch in matrixSearches) {
            for (rows in rowsValues) {
                val matrix = generator.generateMatrix(rows, cols)
                val searchTarget = generator.getSearchTarget(cols)
                measureAvgNanoTime {
                    matrixSearch.find(searchTarget, matrix)
                }.let { ns ->
                    if (!isWarmupRun) {
                        logMeasurement(generator, generator.getSearchTarget, matrixSearch, rows, ns)
                    }
                }
            }
        }
    }
}

private const val REPS_FOR_AVERAGE_MEASUREMENT = 1000

private fun measureAvgNanoTime(computation: () -> Unit): Int {
    val measurements = LongArray(REPS_FOR_AVERAGE_MEASUREMENT) {
        measureNanoTime(computation)
    }

    return measurements.average().toInt()
}

private fun logMeasurement(
    generator: MatrixGenerator,
    getSearchTarget: GetSearchTarget,
    matrixSearch: MatrixSearch,
    rows: Int,
    ns: Int
) {
    buildString {
        append("${generator.tag}\t")
        append("${getSearchTarget.tag}\t")
        append("${matrixSearch.tag}\t")
        append("$rows\t")
        append(ns)
    }.let(::println)
}
