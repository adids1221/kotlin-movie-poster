// Lambdas
val addTwo = { x: Int -> x + 2 }
val sub = { x: Int, y: Int -> x - y }

//High order functions
fun isValidAge(x: Int) = x >= 0

fun main() {
//    val list = (1..100).toList()
//    println(list.filter { it % 3 == 0 })

    val faultyData = mapOf(
            "eran" to listOf(4, 8, 10, -77),
            "dave" to listOf(-2, 4, 12, 77),
            "yoni" to listOf(5, 13, 89)
    )
//    val flatNested = faultyData.map { it.value.filter { it in 0..120 }.average() }
//    val flatNested = faultyData.map { it.value.filter(::isValidAge).average() }
//    val flatNested = faultyData.filter {
//        it.value.any { it !in 0..120 }
//    }.keys
//    val flatNested = faultyData.flatMap { it.value }.filter { it !in 0..120 }.size
//    val flatNested = faultyData.filter {
//        it.value.all(::isValidAge)
//    }.keys
//    println("flatNested $flatNested")

    val names = listOf("eran", "moshe", "elad", "a", "b", "c", "d")
    val numOfChildren = listOf(3, 2, 5, 3)

//    println(names.zip(names.map { it.contains("a") }))
    names.map { it.length }.filter { it > 3 }.let { println(it) }
}