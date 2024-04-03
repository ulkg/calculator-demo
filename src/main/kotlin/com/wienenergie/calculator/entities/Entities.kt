package com.wienenergie.calculator.entities

import com.wienenergie.calculator.utils.toHref
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
class ElectricityTariff(
    var name: String,
    var pricePerKWh: Double,
    @Id @GeneratedValue var id: Long? = null
)
@Entity
class Company(
    var title: String,
    var name: String,
    var description: String,
    var website: String,
    @OneToMany
    var electricityTariffs: MutableList<ElectricityTariff>,
    var href: String = title.toHref(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null
)

