package com.crowdproj.marketplace.units.mappers.exceptions

import com.crowdproj.marketplace.units.common.models.UntsCommand

class UnknownUntsCommand(command: UntsCommand) : Throwable("Wrong command $command at mapping toTransport stage")
