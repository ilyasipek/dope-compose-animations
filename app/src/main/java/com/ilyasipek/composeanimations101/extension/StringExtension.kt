package com.ilyasipek.composeanimations101.extension

fun String.replaceIndex(index: Int, char: Char): String {
    return replaceRange(index, index + 1, char.toString())
}
