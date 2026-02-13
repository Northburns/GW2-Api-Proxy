package northburns.gw2.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

typealias JsonNumber = @Serializable(with = JsonNumberSerializer::class) Number

@Serializable
data class DataClass(
    val id: JsonNumber?,
)

class JsonNumberSerializer : JsonContentPolymorphicSerializer<Number>(Number::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Number> {
        require(element is JsonPrimitive)
        require(!element.isString)
        val c = element.content
        // XXX Just to choose the serializer, we parse the value entirely - up to three times!
        return when {
            c.toIntOrNull() != null -> Int.serializer()
            c.toLongOrNull() != null -> Long.serializer()
            c.toDoubleOrNull() != null -> Double.serializer()
            else -> throw UnsupportedOperationException("Cannot deserialize '$c'")
        }
    }
}

class JsonNumberTest {

    @Test
    fun test() {

        val numbers = listOf(
            null,
            0,
            Int.MIN_VALUE,
            Int.MAX_VALUE,
            Long.MIN_VALUE,
            Long.MAX_VALUE,
            Double.MIN_VALUE,
            Double.MAX_VALUE,
        )
        val json = Json.encodeToString(numbers.map(::DataClass))

        val l = Json.decodeFromString<List<DataClass>>(json)

        assertNull(l[0].id)
        assertIs<Int>(l[1].id)
        assertIs<Int>(l[2].id)
        assertIs<Int>(l[3].id)
        assertIs<Long>(l[4].id)
        assertIs<Long>(l[5].id)
        assertIs<Double>(l[6].id)
        assertIs<Double>(l[7].id)

        assertEquals(numbers[0], l[0].id)
        assertEquals(numbers[1], l[1].id)
        assertEquals(numbers[2], l[2].id)
        assertEquals(numbers[3], l[3].id)
        assertEquals(numbers[4], l[4].id)
        assertEquals(numbers[5], l[5].id)
        assertEquals(numbers[6] as Double, l[6].id as Double, absoluteTolerance = 1.0)
        assertEquals(numbers[7] as Double, l[7].id as Double, absoluteTolerance = 1.0)
    }


}


