fun main() {
    val list = listOf("a","b","c","d")
    println(concat(list))
    println(concat(list, "-"))
}

fun concat(strings:List<String>, separator:String = ",") = strings.joinToString(separator)