package com.wienenergie.calculator.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.util.*

fun LocalDateTime.format(): String = this.format(englishDateFormatter)

private val englishDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("yyyy-MM-dd")
		.toFormatter(Locale.GERMAN)
fun String.toHref() = lowercase(Locale.getDefault())
	.replace("\n", " ")
	.replace("[^a-z\\d\\s]".toRegex(), " ")
	.split(" ")
		.joinToString("-")
		.replace("-+".toRegex(), "-")
