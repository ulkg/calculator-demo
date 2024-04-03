package com.wienenergie.calculator.controller.rest

import com.wienenergie.calculator.repositories.CompanyRepository
import com.wienenergie.calculator.repositories.ElectricityTariffRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/company")
class CompanyController(private val repository: CompanyRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc()

	@GetMapping("/{href}")
	fun findOne(@PathVariable href: String) =
			repository.findByHref(href) ?: throw ResponseStatusException(NOT_FOUND, "This company does not exist")

}

@RestController
@RequestMapping("/api/tariff")
class TariffController(private val repository: ElectricityTariffRepository) {
	private val logger: Logger = LogManager.getLogger(TariffController::class.java)

	@GetMapping("/")
	fun findAll() = repository.findAll()

	@GetMapping("/{name}")
	fun findOne(@PathVariable name: String) = repository.findByName(name) ?: throw ResponseStatusException(NOT_FOUND, "This tariff does not exist")

	@GetMapping("/tariffDetails")
	fun findTariffDetails() : String {
		logger.error("Tariff details not available")
		throw ResponseStatusException(INTERNAL_SERVER_ERROR, "tariff details not available")
	}
}
