package br.ufs.uolchallenge.data.tests

import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Created by bira on 11/3/17.
 */

object FileReader {

    fun readFile(fileName: String): String {

        val result = StringBuilder()
        val classLoader = FileReader::class.java.classLoader
        val file = File(classLoader.getResource(fileName).file)

        try {
            val scanner = Scanner(file)
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                result.append(line).append("\n")
            }

            scanner.close()
            return result.toString()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        throw RuntimeException("Cannot read file" + fileName)
    }

}