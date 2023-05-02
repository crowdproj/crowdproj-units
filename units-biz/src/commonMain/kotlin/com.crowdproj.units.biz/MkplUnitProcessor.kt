package com.crowdproj.units.biz

import com.crowdproj.units.common.MkplContext

class MkplUnitProcessor {
    suspend fun exec(ctx: MkplContext) {
        ctx.unitResponse = MkplUnitStub.get()
    }

}