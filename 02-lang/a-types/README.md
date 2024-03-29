# Basics

## Variables (Mutable and Immutable)

- 2 keywords to declare a variable
  - val (from value) - Immutable reference that cannot be reassigned after it's initialized. (corressponds to `final in Java`).
  - var (from variable) - Mutable reference (corressponds to `non-final Java` variable)

## Types

- `Everything is an object in Kotlin` (i.e., we can call `member functions` and `properties` on any variable).
- Some types have special internal representation.
  - `numbers` , `characters` and `boolean` can be represented as primitive values `at run-time`.

## kotlin.Unit

- Special type `kotlin.Unit`
  - The type with `ONLY ONE VALUE` - the `Unit Object`.
  - corresponds to `void` type in Java.

## class Nothing

- Has `NO instances` => Used to represent `a value that never exist`.
- A function that has return type `Nothing` never actually returns any value. (It runs forever in a loop (or) always throws exception).
- Kotlin compiler complains of `unreachable code` if we put some code after invoking a `Nothing function`.
- Another use-case of Nothing is below.

  ```kotlin
    fun reportError(): Nothing {
        throw RuntimeException()
    }
    fun exampleThree(n: Int): String {
        if (n > 5) {
            return "Ok";
        }
        reportError(); // throws RuntimeException
        // Compiles! (Won't complain about missing return as reportError is marked to return Nothing and never returns!)
    }
  ```

### Numbers

- Built-In numbers.

  | Type     | Bit Width |
  | ---      | ---       |
  | Double   | 64        |
  | Float    | 32        |
  | Long     | 64        |
  | Int      | 32        |
  | Short    | 16        |
  | Byte     | 8         |

- Unsigned Integers (Experimental as of Kotlin 1.3)

  | Type | Bit Width | Range |
  | ---  | ---       | ---   |
  | kotlin.UByte| 8-bit | 0 to 255|
  | kotlin.UShort| 16-bit |  0 to 65535|
  | kotlin.UInt| 32-bit | 0 to 2^32 - 1|
  | kotlin.ULong| 64-bit | 0 to 2^64 - 1|

- Literal numbers.
  - Base
    - `123` is a Decimal.
    - `0x0F` is a Hexa-Decimal.
    - `0b00001011` is a Binary.
    - `Octal` literals are `NOT supported`.
  - Suffix
    - 123`L` is a Long
    - 123`.`5`e`10, 123`.`5 are Doubles by default.
    - 123.5`f` , 32.4`F` are Floats.
    - `u` and `U` tag literal as unsigned.
    - `uL` and `UL` explicitly tag literal as unsigned long.
  - Underscores
    - `val oneMillion = 1_000_000` is valid.
    - Other usages

      ```kotlin
      val oneMillion = 1_000_000
      val creditCardNumber = 1234_5678_9012_3456L
      val socialSecurityNumber = 999_99_9999L
      val hexBytes = 0xFF_EC_DE_5E
      val bytes = 0b11010010_01101001_10010100_10010010
      ```

- Representation in Platforms
  - `JVM`
    - by default stored as JVM primitive types.
    - When used in `nullable number reference (e.g. Int?)` or `generics`, they are `boxed`.
    - `Boxing` DOES NOT preserve `identity`/`referential equality` but preserves `equality`.

      ```kotlin
        val a: Int = 10000
        println(a === a) // Prints 'true' => Identity
        println(a == a) // Prints 'true' => Equality

        //Boxing a for 2 variables
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA) // !!!Prints 'false'!!! => Identity
        println(boxedA == anotherBoxedA) // Prints 'true' => Equality
      ```

  - Explicit Conversions
    - Due to different representations (Java, Javascript etc.,), smaller types are not sub-types of bigger ones. Hence, `smaller-types are NOT implicitly converted to bigger types`.
    - `Absence of implicit conversion` is rarely noticable as
      - types are inferred from context.
      - `arithmetic operators are overloaded` for appropriate conversions.

         ```kotlin
         val l = 1L + 3 // Long + Int => Long
         ```

- Arithmet Operations
  - Arithmetic `operators` over numbers are declared as `member of appropriate classes`. See `Operator overloading` for more information.
  - There are no special characters for `bit-wise operations`. They do have `named functions that can be called in infix form`.

    ```kotlin
      val x = (1 shl 2) and 0x000FF000
      //shl(bits) – signed shift left (Java's <<)
      //shr(bits) – signed shift right (Java's >>)
      //ushr(bits) – unsigned shift right (Java's >>>)
      //and(bits) – bitwise and
      //or(bits) – bitwise or
      //xor(bits) – bitwise xor
      //inv() – bitwise inversion  
    ```

- Floating Point Number Comparisons
  - Equity Checks :  `a == b` and `a != b`.
  - Comparison    : `a < b` , `a > b` , `a <= b` , `a >= b`.
  - Range Instantiation : `a..b`.
  - Range Checks : `x in a..b` , `x !in a..b`.
  - Rules
    - When statically known as `Float` (or) `Double` (Type is declared, inferred or result of smartcast):
      - They follow IEEE 754 Standard for Floating-Point Arithmetic.
    - When `NOT` statically known as `Float` (or) `Double` (e.g., Any, Comparable<...>, a type parameter):
      - Use `equals` and `compareTo` implementations for `Float` and `Double`.
      - Exceptions : (As the equals are compareTo disagree with standard)
        - `NaN` is considered equal to itself.
        - `NaN` is considered greather than `any` including `POSITIVE_INFINITY`.
        - `-0.0` is considered less than `0.0`.

### Characters

- type `Char`
- cannot be treated directly as numbers.

  ```kotlin
    fun check(c: Char) {
        if (c == 1) { // ERROR: incompatible types
            // ...
        }
    }

    //Needs Explicit conversion to Int
    fun decimalDigitValue(c: Char): Int {
        if (c !in '0'..'9')
            throw IllegalArgumentException("Out of range")
        return c.toInt() - '0'.toInt() // Explicit conversions to numbers
    }
  ```

  - `identity` is not preserved by boxing operation.

### Booleans

- type `Boolean` represents `true` or `false`.
- `Built-in` operations:
  - || - lazy disjunction.
  - && - lazy conjuction.
  - ! - negation.

### Arrays

- type `Array` class.
- Member Functions :
  - `get` and `set` functions (that turn into [] by operator overloading conventions).
- Properties:
  - `size`.
- Instantiation variants:
  1. `val arr = arrayOf(1, 2, 3)`// => Creates an array [1, 2, 3] with size 3.
  2. `var arr = arrayOfNulls(5)` // => Creates an array of size 5 with all elements set to null
  3. `val asc = Array(5) { i -> (i * i).toString() }` //=> creates array of size 5 where each element is calculated by calling the init function `{ i -> (i * i).toString() }` with appropriate `index`.
- Specialized classes:
  - kotlin.UByteArray: an array of unsigned bytes
  - kotlin.UShortArray: an array of unsigned shorts
  - kotlin.UIntArray: an array of unsigned ints
  - kotlin.ULongArray: an array of unsigned longs

- Array in Kotlin is invariant
  - `Array<String> cannot be assigned to Array<Any>.
- Specialized Array classes for primitives (to eliminate boxing overhead)
  - Primitive Arrays equivalent Classes:
    - CharArray
    - ByteArray
    - ShortArray
    - IntArray
    - LongArray
    - FloatArray
    - DoubleArray
  - These classes have `NO inheritance relation` to `Array` class but have same set of
  methods and properties.

### Strings

- type `String` which are `immutable`.
- `Characters looping`

  ```kotlin
    val str = "something"
    for (c in str) {
        println(c)
    }
  ```

- `Concatenation`

  ```kotlin
    val s = "abc" + "def"
    val s1 = "abc" + 1
  ```

- `Literals`
  1. Escaped Strings => E.g. `val s = "Hello, world!\n"`  
  2. Raw String
  
    ```kotlin
      val text = """
      for (c in "foo")
        print(c)
      """
    //trimMargin => By Default "|" is used as margin prefix.
    val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
    ```

- Templates

  ```kotlin
    val i = 10
    println("i = $i") // prints "i = 10"
    val s = "abc"
    println("$s.length is ${s.length}") // prints "abc.length is 3"

    val price = """
    ${'\n'}${'$'}9.99
    """
    /**Prints"
    price is

    $9.99
    "*/
  ```

## Other Opeartions/Operators

### The !! Operator (not-null assertion opeartor)

- Checks any value for `null` and throws `NullPointerException` if null

  ```kotlin
    val l = b!!.length //throws NPE if b is null
    val anotherOb = b!! //throws NPE if b is null
  ```

### The ?. Operator (Safe Call Operator)

- Performs a null check on a value before acessing it's properties or members. If the object is null , it `returns null`.

  ```kotlin
    val a = "Kotlin"
    val b: String? = null
    println(b?.length) //This returns b.length if b is not null, and null otherwise
    println(a?.length) // Unnecessary safe call
  ```

- Very useful in chaining of calls.

  ```kotlin
    bob?.department?.head?.name
  ```

### The ?: Opeartor (Elvis Operator)

- Used to specify an `alternate value` if a `nullable reference is null`

  ```kotlin
    val l: Int = if (b != null) b.length else -1 //normal statement.
    //The above statement can be written using Elvis like below :
    val l = b?.length ?: -1
  ```

### The :: Operator (Member Reference and Class Reference Operator)

- Function references
  - Can  pass functions to another function by prefixing them with `::` operator.

    ```kotlin
      fun isOdd(x: Int) = x % 2 != 0
      val numbers = listOf(1, 2, 3)
      println(numbers.filter(::isOdd)) // isOdd function is passed by references
    ```

  - If we need to use a `member of a class`, or `an extension function`, it needs to be `qualified`

    ``` kotlin
    val toCharArray = String::toCharArray
    val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty //To have a function type with receiver instead, specify the type explicitly.
    ```

- Property References
  - Acess properties as `first-class objects`. Refer to `KProperty` class in Kotlin

    ```kotin
    val x = 1

    fun main() {
        println(::x.get()) //::x evaluates to KProperty<Int>
        println(::x.name)
    }
    ```

- Constructor References
  - Constructor can be references just like methods and properties.

- Class References (::class)
  - Access classes as `first-class objects` @runtime. Refer to `KClass` class in Kotlin.

    ```kotlin
      val c = MyClass::class // class literal syntax
      val widget: Widget = GoodWidget()
      println("widget classNam: ${widget::class.qualifiedName}") //Perform ::class on a object of a particular class.
    ```

## Constants

- Resolved at compile-time
  
  ```kotlin
     //Regular classes cannot have constants.
    // class Constants {
    //     const val CONSTANT = ""
    // }

    //Object class can have Constants (They are singleton)
    object Constants {
        const val CONSTANT = "SOMEVALUE"
    }

    //Companion object of a class can contain constants as well.
    class MyClass {
        companion object {
            const val CONSTANT = "SOMEVALUE"
        }
    }
  ```

## References

- [Keyword References](https://kotlinlang.org/docs/reference/keyword-reference.html)
