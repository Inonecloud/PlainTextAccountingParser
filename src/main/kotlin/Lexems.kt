
val DATE_REGEX = "[0-9]{4}\\/[0-9]{1,2}\\/[0-9]{1,2}".toRegex()
val KEYWORDS = listOf<String>("Expenses", "Assets", "Equity")
const val START_OF_FILE  = "; -*- ledger -*-"
const val EOL = "\n"