package com.wienenergie.calculator.repositories

import com.wienenergie.calculator.entities.Company
import com.wienenergie.calculator.entities.ElectricityTariff
import org.springframework.data.repository.CrudRepository

interface CompanyRepository : CrudRepository<Company, Long> {
	fun findByHref(href: String): Company?
	fun findAllByOrderByAddedAtDesc(): Iterable<Company>
}

interface ElectricityTariffRepository : CrudRepository<ElectricityTariff, Long> {
	fun findByName(name: String): ElectricityTariff?
}

