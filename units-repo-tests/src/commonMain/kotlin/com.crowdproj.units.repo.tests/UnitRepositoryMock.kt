package com.crowdproj.units.repo.tests

import com.crowdproj.units.common.repo.*

class UnitRepositoryMock(
    private val invokeCreateUnit: (DbUnitRequest) -> DbUnitResponse = { DbUnitResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadUnit: (DbUnitIdRequest) -> DbUnitResponse = { DbUnitResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateUnit: (DbUnitRequest) -> DbUnitResponse = { DbUnitResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteUnit: (DbUnitIdRequest) -> DbUnitResponse = { DbUnitResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchUnit: (DbUnitFilterRequest) -> DbUnitsResponse = { DbUnitsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSuggestUnit: (DbUnitRequest) -> DbUnitResponse = { DbUnitResponse.MOCK_SUCCESS_EMPTY }
): IUnitRepository {
    override suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse {
        return invokeCreateUnit(rq)
    }

    override suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse {
        return invokeReadUnit(rq)
    }

    override suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse {
        return invokeUpdateUnit(rq)
    }

    override suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse {
        return invokeDeleteUnit(rq)
    }

    override suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse {
        return invokeSearchUnit(rq)
    }

    override suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse {
        return invokeSuggestUnit(rq)
    }
}