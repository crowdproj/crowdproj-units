package com.crowdproj.units.common.exceptions

import com.crowdproj.units.common.models.MkplUnitLock

class RepoConcurrencyException(expectedLock: MkplUnitLock, actualLock: MkplUnitLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)