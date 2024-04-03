package com.wienenergie.calculator

import com.wienenergie.calculator.entities.Company
import com.wienenergie.calculator.entities.ElectricityTariff
import com.wienenergie.calculator.repositories.CompanyRepository
import com.wienenergie.calculator.repositories.ElectricityTariffRepository
import com.wienenergie.calculator.utils.toHref
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
// set up an environment with an embedded database to test our database queries
class RepositoriesTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val electricityTariffRepository: ElectricityTariffRepository,
	val companyRepository: CompanyRepository
) {

	@Test
	fun `When findByHref then return Company`() {
		val tariff = ElectricityTariff(
            "test",
            29.5
        )
		entityManager.persist(tariff)
		val company = Company(
            "testCompany",
            "test company",
            "description",
            "https://www.google.at",
            mutableListOf(tariff)
        )
		entityManager.persist(company)
		entityManager.flush()
		val found = companyRepository.findByHref(company.title.toHref())
		assertThat(found).isEqualTo(company)
        assertThat(company.electricityTariffs[0]).isEqualTo(tariff)
	}


    @Test
    fun `When findByName then return ElectricityTariff`() {
        val tariff = ElectricityTariff(
            "test",
            29.5
        )
        entityManager.persist(tariff)
        entityManager.flush()
        val foundTariff = electricityTariffRepository.findByName(tariff.name)
        assertThat(foundTariff).isEqualTo(tariff)
    }
}
