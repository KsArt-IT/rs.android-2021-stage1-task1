package subtask3

class StringParser {

    fun getResult(inputString: String): Array<String> {
        if (inputString.isBlank() || !inputString.contains("""[<(\[]""".toRegex())) return emptyArray()
        val result = mutableListOf<String>()
        var sNext = inputString
        do {
            val entry = mapOf(
                sNext.indexOf('<') to sNext.lastIndexOf('>'),
                sNext.indexOf('(') to sNext.lastIndexOf(')'),
                sNext.indexOf('[') to sNext.lastIndexOf(']')
            ).filter {
                it.key in 0 until it.value
            }.minBy {
                it.key
            }
            entry?.let { elem ->
                val bracketStart = sNext[elem.key]
                val bracketEnd = sNext[elem.value]
                var bracketEndIndex = elem.key
                var bracketEndCount = 0
                var s = ""
                do {
                    bracketEndIndex = sNext.indexOf(bracketEnd, bracketEndIndex + 1)
                    s = if (bracketEndIndex > 0) {
                        bracketEndCount++
                        sNext.substring(elem.key + 1, bracketEndIndex)
                    } else ""
                } while (bracketEndIndex > 0 && s.count { it == bracketStart } + 1 > bracketEndCount)

                if (s.isNotBlank()) result.add(s)
                sNext = if (elem.value < sNext.length) {
                    sNext.substring(elem.key + 1)
                } else ""
            }
        } while (entry != null && sNext != "")
        return result.toTypedArray()
    }
}
