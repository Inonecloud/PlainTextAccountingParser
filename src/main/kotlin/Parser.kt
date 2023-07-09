import entities.Currency
import java.time.*
import java.time.format.DateTimeFormatter

class Parser {

    var previous: Token? = null
    lateinit var next: Token


    fun parse(tokens: List<Token>) {
        for (token in tokens) {
            if (TokenType.LITERAL == token.type) {
                when {
                    (SubType.DATE == token.subType) -> {
                        val localDateTime = LocalDateTime.of(
                            LocalDate.parse(token.value, DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                            LocalTime.MIDNIGHT
                        )
                        val date: Instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                    }

                    (SubType.DATE == previous?.subType) -> {
                        val name = token.value
                    }
                }
            }

            if (TokenType.IDENTIFIER == token.type) {
                when {
                    (SubType.DATE == previous?.subType) -> {
                        val name = token.value
                    }
//
//                    (Currency.values().contains(token.value)) -> {
//
//                    }

                }

            }

            if (TokenType.KEYWORD == token.type) {
                val splitedKeyword = token.value.split(":")
                when (splitedKeyword[0]) {
                    "Expenses" -> {
                        val category = splitedKeyword[1]
                    }

                    "Assets" -> {
                        splitedKeyword.size
                    }

                    "Equity" -> {

                    }
                }
            }


            previous = token;
        }
    }
}