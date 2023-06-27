package com.crowdproj.units.common.repo

interface IUnitRepository {
    suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse
    suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse
    suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse
    suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse
    suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse
    suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse

    companion object {
        val NONE = object: IUnitRepository {
            override suspend fun createUnit(rq: DbUnitRequest): DbUnitResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readUnit(rq: DbUnitIdRequest): DbUnitResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateUnit(rq: DbUnitRequest): DbUnitResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteUnit(rq: DbUnitIdRequest): DbUnitResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchUnit(rq: DbUnitFilterRequest): DbUnitsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun suggestUnit(rq: DbUnitRequest): DbUnitResponse {
                TODO("Not yet implemented")
            }

        }
    }
}