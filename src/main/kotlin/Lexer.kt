class Lexer {

    val tokensTree: MutableList<Token> = mutableListOf();
    fun tokenizeLine(line: String) {
        line.split(" ").forEach {
            getToken(it, tokensTree)
        }
        tokensTree.add(Token(EOL, TokenType.LITERAL))

    }

    private fun getToken(token: String, tokensTree: MutableList<Token>) {
        when {
            token.matches(DATE_REGEX) -> tokensTree.add(Token(token, TokenType.LITERAL, SubType.DATE))
            token.contains(START_OF_FILE) -> tokensTree.add(Token(token, TokenType.LITERAL))
            KEYWORDS.any { it in token } -> tokensTree.add(Token(token, TokenType.KEYWORD))
            token == "" -> {}
            else -> tokensTree.add(Token(token, TokenType.IDENTIFIER))
        }
    }

    fun composeTokens(tokensTree: MutableList<Token>): List<Token> {
        val tokens = mutableListOf<Token>()
        val iterator = tokensTree.iterator()
        var previous: Token? = null;
        while (iterator.hasNext()) {
            val token = iterator.next()
            if (TokenType.IDENTIFIER == token.type && iterator.hasNext()) {
                if (";" == token.value) {
                    val nextToken = iterator.next()
                    if (nextToken.value == "-*-") {
                        tokens.add(Token(START_OF_FILE, TokenType.LITERAL))
                        iterator.next()
                        iterator.next()
                    } else if (TokenType.IDENTIFIER == nextToken.type) {
                        tokens.add(token)
                        tokens.add(concatTokens(nextToken, iterator, SubType.ITEM_NAME))
                        tokens.add(Token(EOL, TokenType.LITERAL))
                    } else {
                        tokens.add(token)
                        tokens.add(Token(EOL, TokenType.LITERAL))
                    }
                } else if ("*" == token.value && SubType.DATE == previous?.subType) {
                    tokens.add(Token(token.value, TokenType.IDENTIFIER, SubType.PENDING))
                    tokens.add(concatTokens(iterator.next(), iterator, SubType.TRANSACTION_NAME))
                } else if (SubType.DATE == previous?.subType) {
                    tokens.add(concatTokens(token, iterator, SubType.TRANSACTION_NAME))
                } else {
                    tokens.add(token)
                }
            } else {
                tokens.add(token)
            }
            previous = token
        }

        deleteEmptyLines(tokens)
        return tokens.toList()
    }

    private fun concatTokens(token: Token, iterator: MutableIterator<Token>, subType: SubType?): Token {
        lateinit var nextToken: Token
        val buf = mutableListOf<String>(token.value)
        while (iterator.hasNext() && iterator.next().also { nextToken = it }.value != EOL) {
            buf.add(nextToken.value)
        }
        return if (subType != null) {
            Token(buf.joinToString(" "), TokenType.IDENTIFIER, subType)
        } else {
            Token(buf.joinToString(" "), TokenType.IDENTIFIER)
        }
    }

    private fun deleteEmptyLines(tokensTree: MutableList<Token>): List<Token> {
        val tokens = tokensTree.iterator()
        while (tokens.hasNext()) {
            val current = tokens.next()
            if (EOL == current.value && tokens.hasNext()) {
                var next = tokens.next()
                if (current.value == next.value) {
                    tokens.remove()
                }
            }
        }
        return tokens.asSequence().toList()
    }

}