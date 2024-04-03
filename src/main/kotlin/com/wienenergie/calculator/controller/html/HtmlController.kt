package com.wienenergie.calculator.controller.html

import com.wienenergie.calculator.config.CalculatorProperties
import com.wienenergie.calculator.entities.Company
import com.wienenergie.calculator.entities.ElectricityTariff
import com.wienenergie.calculator.repositories.CompanyRepository
import com.wienenergie.calculator.service.PriceCalculationService
import com.wienenergie.calculator.utils.format
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException

@Controller
class HtmlController(
    private val companyRepository: CompanyRepository,
    private val properties: CalculatorProperties,
    private val priceCalculationService: PriceCalculationService
) {

    @GetMapping("/")
    fun blog(model: Model): String {
        addPageInfos(model)
        return "calculator" // use HTML template "calculator.mustache"
    }

    @GetMapping("/about")
    fun about(model: Model): String {
        addPageInfos(model)
        return "about"
    }

    @GetMapping("/calculate")
    fun energyCostCalculation(
        @RequestParam("yearlyPowerConsumption") yearlyPowerConsumption: Double,
        @RequestParam("price") price: Double,
        model: Model
    ): String {
        val totalCost = priceCalculationService.calculateEnergyCost(yearlyPowerConsumption, price)
        model.addAttribute("totalCost", totalCost)
        addPageInfos(model)
        return "calculator"
    }

    @GetMapping("/company/{href}")
    fun company(@PathVariable href: String, model: Model): String {
        val company = companyRepository
            .findByHref(href)
            ?.render()
            ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["title"] = company.title
        model["company"] = company
        return "company"
    }

    private fun addPageInfos(model: Model) {
        model["title"] = properties.title
        model["banner"] = properties.banner
        model["companies"] = companyRepository.findAllByOrderByAddedAtDesc().map { it.render() }
    }

    fun Company.render() = RenderedCompany(
        title,
        href,
        name,
        description,
        website,
        electricityTariffs,
        addedAt.format()
    )

    data class RenderedCompany(
        val title: String,
        val href: String,
        val name: String,
        val description: String,
        val website: String,
        val electricityTariffs: MutableList<ElectricityTariff>,
        val addedAt: String
    )
}
