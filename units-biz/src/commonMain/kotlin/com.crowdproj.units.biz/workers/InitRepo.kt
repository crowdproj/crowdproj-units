package com.crowdproj.units.biz.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.helpers.errorAdministration
import com.crowdproj.units.common.helpers.fail
import com.crowdproj.units.common.models.MkplWorkMode
import com.crowdproj.units.common.repo.IUnitRepository

fun ICorAddExecDsl<MkplContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Calculating main working repository depending on requested operating mode
    """.trimIndent()
    handle {
        unitRepository = when {
            workMode == MkplWorkMode.TEST -> settings.repoTest
            workMode == MkplWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != MkplWorkMode.STUB && unitRepository == IUnitRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "Database not configured for workmode $workMode. Please contact administrator."
            )
        )

    }
}