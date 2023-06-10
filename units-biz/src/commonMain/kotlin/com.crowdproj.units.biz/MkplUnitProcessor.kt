package com.crowdproj.units.biz

import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.units.biz.groups.operation
import com.crowdproj.units.biz.groups.stubs
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.biz.workers.*
import com.crowdproj.units.biz.validation.*

class MkplUnitProcessor {
    suspend fun exec(ctx: MkplContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Status initialization")

            operation("Create a unit", MkplCommand.CREATE) {
                stubs("Processing stubs") {
                    stubCreateSuccess("Stub: created successfully")
                    stubValidationBadId("Stub: ID validation error")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }
                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }
                    worker("Clearing ID field") { unitValidating.id = MkplUnitId.NONE }
                    worker("Clearing name field") { unitValidating.name = unitValidating.name.trim() }
                    worker("Clearing description field") { unitValidating.description = unitValidating.description.trim() }
                    worker("Clearing alias field") { unitValidating.alias = unitValidating.alias.trim() }

                    validateNameNotEmpty("Validating unit name is not empty")
                    validateNameHasContent("Validating chars")
                    validateAliasNotEmpty("Validating alias is not empty")
                    validateAliasHasContent("Validating chars")

                    finishUnitValidation("Validation completed")
                }
            }

            operation("Get a unit", MkplCommand.READ) {
                stubs("Processing stubs") {
                    stubReadSuccess("Stub: read successfully")
                    stubValidationBadId("Stub: ID validation error")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }

                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }
                    worker("Clearing ID") { unitValidating.id = MkplUnitId(unitValidating.id.asString().trim()) }

                    validateIdNotEmpty("Validating ID is not empty")
                    validateIdProperFormat("Validating ID format")

                    finishUnitValidation("Validation completed")
                }
            }

            operation("Update a unit", MkplCommand.UPDATE) {
                stubs("Processing stubs") {
                    stubUpdateSuccess("Stub: updated successfully")
                    stubValidationBadId("Stub: ID validation error")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }

                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }
                    worker("Clearing ID") { unitValidating.id = MkplUnitId(unitValidating.id.asString().trim()) }
                    worker("Clearing name") { unitValidating.name = unitValidating.name.trim() }
                    worker("Clearing description") { unitValidating.description = unitValidating.description.trim() }
                    worker("Clearing alias field") { unitValidating.alias = unitValidating.alias.trim() }

                    validateIdNotEmpty("Validating ID is not empty")
                    validateIdProperFormat("Validating ID format")
                    validateNameNotEmpty("Validating name is not empty")
                    validateNameHasContent("Validating chars")
                    validateAliasNotEmpty("Validating alias is not empty")
                    validateAliasHasContent("Validating chars")

                    finishUnitValidation("Validation completed")
                }
            }

            operation("Delete a unit", MkplCommand.DELETE) {
                stubs("Processing stubs") {
                    stubDeleteSuccess("Stub: deleted successfully")
                    stubValidationBadId("Stub: ID validation error")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }

                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }
                    worker("Clearing ID") { unitValidating.id = MkplUnitId(unitValidating.id.asString().trim()) }

                    validateIdNotEmpty("Validating ID is not empty")
                    validateIdProperFormat("Validating ID format")

                    finishUnitValidation("Validation completed")
                }
            }

            operation("Search a unit", MkplCommand.SEARCH) {
                stubs("Processing stubs") {
                    stubSearchSuccess("Stub: search success")
                    stubValidationBadSearchString("Stub: bad search string")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }

                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }

                    finishUnitFilterValidation("Validation completed")
                }
            }

            operation("Suggest a unit", MkplCommand.SUGGEST) {
                stubs("Processing stubs") {
                    stubSuggestSuccess("Stub: suggest success")
                    stubDbError("Stub: DB error")
                    stubNoCase("Error: requested stub not allowed")
                }

                validation {
                    worker("Copying fields to unitValidating") { unitValidating = unitRequest.deepCopy() }
                    worker("Clearing name") { unitValidating.name = unitValidating.name.trim() }
                    worker("Clearing description") { unitValidating.description = unitValidating.description.trim() }
                    worker("Clearing alias field") { unitValidating.alias = unitValidating.alias.trim() }

                    validateNameNotEmpty("Validating name is not empty")
                    validateNameHasContent("Validating chars")
                    validateAliasNotEmpty("Validating alias is not empty")
                    validateAliasHasContent("Validating chars")

                    finishUnitFilterValidation("Validation completed")
                }
            }
        }.build()
    }

}