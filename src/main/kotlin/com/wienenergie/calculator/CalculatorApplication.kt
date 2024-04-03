package com.wienenergie.calculator

import com.wienenergie.calculator.config.CalculatorProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(CalculatorProperties::class)
class CalculatorApplication

fun main(args: Array<String>) {
	runApplication<CalculatorApplication>(*args)
}
