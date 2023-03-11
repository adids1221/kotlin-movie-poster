class Student(var first: String, var age: Int)

fun main() {
    val s = Student("moshe", 32)
    println("name ${s.first} | age ${s.age}")
}