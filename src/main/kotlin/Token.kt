data class Token(
    val value: String,
    val type: TokenType,
) {
    var subType: SubType? = null

    constructor(value: String, type: TokenType, subType: SubType) : this(value, type){
        this.subType = subType
    }
}

enum class TokenType {
    KEYWORD,
    LITERAL,
    IDENTIFIER
}

enum class SubType {
    TRANSACTION_NAME,
    DATE,
    END_OF_LINE,
    COMMENT,
    AMOUNT,
    CURRENCY,
    TRANSACTION_TYPE,
    CATEGORY,
    PENDING,
    ITEM_NAME
}