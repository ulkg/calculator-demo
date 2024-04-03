package com.wienenergie.calculator.config

import com.wienenergie.calculator.entities.Company
import com.wienenergie.calculator.entities.ElectricityTariff
import com.wienenergie.calculator.repositories.CompanyRepository
import com.wienenergie.calculator.repositories.ElectricityTariffRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseInitializer {

    @Bean
    fun dummyDataInserter(
        companyRepository: CompanyRepository,
        electricityTariffRepository: ElectricityTariffRepository
    ) = ApplicationRunner {
        val optimaEntspannt = electricityTariffRepository.save(
            ElectricityTariff(
                "Optima Entspannt",
                29.3724
            )
        )
        val optimaEntspanntBasis = electricityTariffRepository.save(
            ElectricityTariff(
                "Optima Entspannt Basis",
                29.1180
            )
        )
        val verbundStrom = electricityTariffRepository.save(
            ElectricityTariff(
                "VERBUND-Strom",
                26.40
            )
        )
        companyRepository.save(
            Company(
                "WienEnergie",
                "Wien Energie",
                "Als größter regionaler Energieversorger Österreichs sind wir Tag für Tag mit vollem Einsatz für die Energie von zwei Millionen Kund*innen in und um Wien da. Bis 2040 klimaneutral.",
                "https://www.wienenergie.at/",
                mutableListOf(optimaEntspannt, optimaEntspanntBasis)
            )
        )
        companyRepository.save(
            Company(
                "Verbund",
                "Verbund",
                "Wasser, Wind und Sonne für eine saubere Energiezukunft - Mit voller Kraft zur Energiewende",
                "https://www.verbund.com/de-at",
                mutableListOf(verbundStrom)
            )
        )
    }
}
