package com.wienenergie.calculator.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("calculator")
data class CalculatorProperties(var title: String, val banner: Banner?) {
	data class Banner(val title: String, val content: String)
}
