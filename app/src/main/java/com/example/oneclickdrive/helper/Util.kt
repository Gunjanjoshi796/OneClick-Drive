package com.example.oneclickdrive.helper

import android.util.Log

class Util {

    fun calculateList(
        numberListOne: String,
        numberListTwo: String,
        numberListThree: String,
        calculateListener: CalculateListener
    ){
        val numbersFirst = parseInput(numberListOne)
        val numbersSecond = parseInput(numberListTwo)
        val numbersThree = parseInput(numberListThree)


        val intersection = numbersFirst.intersect(numbersSecond).intersect(numbersThree)
        val union = numbersFirst.union(numbersSecond).union(numbersThree)
        val highest = union.maxOrNull()
        if (intersection.joinToString(",").isEmpty()){
            calculateListener.getIntersectNumbers("There are no intersecting numbers here")
        } else {
            calculateListener.getIntersectNumbers(intersection.joinToString(","))
        }
        calculateListener.getUnionOfNumbers(union.toSet().joinToString(","))
        calculateListener.getLargestNumber(highest.toString())
    }

    fun getFiltereString(input: String): String {
        val filteredInput = StringBuilder()
        var lastCharWasComma = false

        for (char in input) {
            if (char.isDigit()) {
                filteredInput.append(char)
                lastCharWasComma = false
            } else if (char == ',' && !lastCharWasComma) {
                filteredInput.append(char)
                lastCharWasComma = true
            }
        }

        return filteredInput.toString()
    }

    private fun parseInput(input: String): List<Int> {
        return input.split(",").mapNotNull { it.trim().toIntOrNull() }
    }
}