package com.wienenergie.calculator

import com.ninjasquad.springmockk.MockkBean
import com.wienenergie.calculator.entities.Company
import com.wienenergie.calculator.entities.ElectricityTariff
import com.wienenergie.calculator.repositories.CompanyRepository
import com.wienenergie.calculator.repositories.ElectricityTariffRepository
import com.wienenergie.calculator.service.PriceCalculationService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
// test Spring MVC component
class RestControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var companyRepository: CompanyRepository

    @MockkBean
    lateinit var priceCalculationService: PriceCalculationService

    @MockkBean
    lateinit var electricityTariffRepository: ElectricityTariffRepository

    @Test
    fun `List companies`() {
        val tariff = ElectricityTariff(
            "test",
            29.5
        )
        val company1 = Company(
            "testCompany",
            "test company",
            "description",
            "https://www.google.at",
            mutableListOf(tariff)
        )
        val company2 = Company(
            "test2Company",
            "test2 company",
            "description2",
            "https://www.google2.at",
            mutableListOf()
        )
        every { companyRepository.findAllByOrderByAddedAtDesc() } returns listOf(company1, company2)
        mockMvc.perform(get("/api/company/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].name").value(company1.name))
            .andExpect(jsonPath("\$.[0].electricityTariffs.[0].name").value(tariff.name))
            .andExpect(jsonPath("\$.[1].name").value(company2.name))
            .andExpect(jsonPath("\$.[1].electricityTariffs").isEmpty)
    }

    @Test
    fun `List tariffs`() {
        val tariff1 = ElectricityTariff(
            "test1",
            29.5
        )
        val tariff2 = ElectricityTariff(
            "test2",
            24.3
        )
        every { electricityTariffRepository.findAll() } returns listOf(tariff1, tariff2)
        mockMvc.perform(get("/api/tariff/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].name").value(tariff1.name))
            .andExpect(jsonPath("\$.[1].name").value(tariff2.name))
    }
}
