package com.crowdproj.units.mappers.exceptions

import com.crowdproj.units.common.models.MkplCommand

class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
