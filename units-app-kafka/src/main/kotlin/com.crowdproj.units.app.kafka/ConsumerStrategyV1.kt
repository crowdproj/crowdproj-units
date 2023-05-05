package com.crowdproj.units.app.kafka

import com.crowdproj.units.api.v1.apiV1RequestDeserialize
import com.crowdproj.units.api.v1.apiV1ResponseSerialize
import com.crowdproj.units.api.v1.models.IUnitRequest
import com.crowdproj.units.api.v1.models.IUnitResponse
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.mappers.fromTransport
import com.crowdproj.units.mappers.toTransportUnit

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: MkplContext): String {
        val response: IUnitResponse = source.toTransportUnit()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: MkplContext) {
        val request: IUnitRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}