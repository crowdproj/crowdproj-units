package com.crowdproj.marketplace.units.mappers.exceptions

import com.crowdproj.marketplace.units.common.models.UntsUnitStatus

class UnknownUntsUnitStatus(status: UntsUnitStatus) : Throwable("Wrong status $status at mapping toTransport stage")
