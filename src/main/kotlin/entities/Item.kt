package entities

import java.math.BigDecimal

data class Item(
    val category: String,
    val amount: BigDecimal,
    val currency: Currency,
    val comment: String
)
