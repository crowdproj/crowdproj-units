package com.crowdproj.units.api.v1

import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

interface UnitApiStrategy<K: Any> {
    val discriminator: String
    val clazz: KClass<out K>
    val serializer: KSerializer<out K>
    fun <T: K> fillDiscriminator(req: T): T
}
