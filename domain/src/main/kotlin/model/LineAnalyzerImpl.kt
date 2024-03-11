package model

class LineAnalyzerImpl(
    line: String
) : LineAnalyzer(
    line = line
) {
    override fun isHeader(): Boolean {
        val regex = Regex("#+ .+")
        return regex.matches(line)
    }

    override fun headerLevel(): Int {
        val regex = Regex("^#+")
        val matchedElements = regex.find(line)
        return matchedElements?.value?.length ?: 0
    }

    override fun getHeader(): String {
        val regex = Regex("\\w.*")
        return regex.find(line)?.value ?: ""
    }
}