package com.crowdproj.units.springapp.controller

import com.crowdproj.units.api.v1.apiMapper
import com.crowdproj.units.api.v1.models.IRequest
import com.crowdproj.units.api.v1.models.IUnitResponse
import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.asMkplError
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.logging.common.IMpLogWrapper
import com.crowdproj.units.mappers.fromTransport
import com.crowdproj.units.mappers.toTransportUnit
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import toLog

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IUnitResponse> process(
    processor: MkplUnitProcessor,
    command: MkplCommand? = null,
    requestString: String,
    logger: IMpLogWrapper,
    logId: String,
): String {
    val ctx = MkplContext(
        timeStart = Clock.System.now()
    )
    return try {
        logger.doWithLogging(id = logId) {
            val request = apiMapper.decodeFromString<Q>(requestString)
            ctx.fromTransport(request)
            logger.info(
                msg = "got $command request" ,
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            apiMapper.encodeToString(ctx.toTransportUnit())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = MkplState.FAILING
            ctx.errors.add(e.asMkplError())
            processor.exec(ctx)
            apiMapper.encodeToString(ctx.toTransportUnit())
        }
    }
}