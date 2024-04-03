package com.wienenergie.calculator

import com.wienenergie.calculator.service.PriceCalculationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PriceCalculationServiceTest {

    @Test
    fun testCalculateEnergyCost() {
        val service = PriceCalculationService()

        val result1 = service.calculateEnergyCost(1000.0, 12.5)
        assertEquals("125,00", result1)

        val result2 = service.calculateEnergyCost(0.0, 15.0)
        assertEquals("0,00", result2)

        val result3 = service.calculateEnergyCost(2000.0, 0.0)
        assertEquals("0,00", result3)
    }
}