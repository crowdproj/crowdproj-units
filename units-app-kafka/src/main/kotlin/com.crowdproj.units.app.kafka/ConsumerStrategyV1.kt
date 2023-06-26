package com.crowdproj.units.app.kafka

import com.crowdproj.units.api.v1.models.IUnitRequest
import com.crowdproj.units.api.v1.models.IUnitResponse
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.mappers.fromTransport
import com.crowdproj.units.mappers.toTransportUnit
import kotlinx.serialization.json.Json

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: MkplContext): String {
        val response: IUnitResponse = source.toTransportUnit()
        return Json.encodeToString(IUnitResponse.serializer(), response)
    }

    override fun deserialize(value: String, target: MkplContext) {
        val request: IUnitRequest = Json.decodeFromString(IUnitRequest.serializer(), value)
        target.fromTransport(request)
    }
}