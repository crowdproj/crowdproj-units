package com.crowdproj.units.repo.stubs

import com.crowdproj.units.common.repo.*

class UnitRepoStub : IUnitRepository {
    override suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse {
        return DbUnitResponse(
            data = MkplUnitStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse {
        return DbUnitResponse(
            data = MkplUnitStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse {
        return DbUnitResponse(
            data = MkplUnitStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse {
        return DbUnitResponse(
            data = MkplUnitStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse {
        return DbUnitsResponse(
            data = MkplUnitStub.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }

    override suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse {
        return DbUnitResponse(
            data = MkplUnitStub.prepareResult {  },
            isSuccess = true,
        )
    }

}