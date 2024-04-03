package com.wienenergie.calculator.service;

import org.springframework.stereotype.Service;

@Service
class PriceCalculationService {
    fun calculateEnergyCost(
        yearlyPowerConsumption: Double,
        price: Double
    ): String {
        val totalCost = yearlyPowerConsumption * (price / 100)
        return String.format("%.2f", totalCost)
    }
}
