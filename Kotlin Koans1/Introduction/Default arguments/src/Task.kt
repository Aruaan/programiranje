fun foo(name: String, number: Int, toUpperCase: Boolean = false) =
        (if (toUpperCase) name.toUpperCase() else name) + number

fun useFoo() = listOf(
        foo("a", 42),
        foo("b", number = 1),
        foo("c", 42, toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
)
