package eco.usp.automate

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

// -------------------------------------------------------
//  Global serialization (using Jackson)
// -------------------------------------------------------
val mapper= jacksonObjectMapper()

fun Any.toJson(pretty: Boolean = false): String = if (pretty) mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this) else mapper.writeValueAsString(this)

inline fun <reified T : Any> String.fromJson(): T = mapper.readValue(this, T::class.java)

inline fun <reified T : Any> String.fromJsonList(): List<T>  = mapper.readValue(this, mapper.typeFactory.constructCollectionType(List::class.java, T::class.java))
