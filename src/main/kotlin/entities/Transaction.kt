package entities

import java.time.Instant

data class Transaction(
    val date: Instant,
    val name: String,

    val type: TransactionType
    )

enum class TransactionType {
    EQUITY,
    EXPENSES
}