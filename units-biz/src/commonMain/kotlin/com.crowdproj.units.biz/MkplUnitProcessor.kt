package com.crowdproj.units.biz

import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.units.biz.general.operation
import com.crowdproj.units.biz.general.prepareResult
import com.crowdproj.units.biz.general.stubs
import com.crowdproj.units.biz.repo.*
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.MkplCommand
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.biz.workers.*
import com.crowdproj.units.biz.validation.*
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.MkplState
import com.crowdproj.units.common.models.MkplUnitLock

class MkplUnitProcessor(private val settings: MkplCorSettings = MkplCorSettings()) {
    suspend fun exec(ctx: MkplContext) = BusinessChain.exec(ctx.apply { settings =  this@MkplUnitProcessor.settings })

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Status initialization")
            initRepo("Repository initialization")

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
                chain {
                    title = "Saving unit to DB logic"
                    repoPrepareCreate("Preparing object to save")
                    repoCreate("Creating unit in DB")
                }
                prepareResult("Preparing response")
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
                chain {
                    title = "Reading unit from DB logic"
                    repoRead("Reading unit from DB")
                    worker {
                        title = "Preparing Read response"
                        on { state == MkplState.RUNNING }
                        handle { unitRepoDone = unitRepoRead }
                    }
                }
                prepareResult("Preparing response")
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
                    worker("Clearing lock field") { unitValidating.lock = MkplUnitLock(unitValidating.lock.asString().trim()) }

                    validateIdNotEmpty("Validating ID is not empty")
                    validateIdProperFormat("Validating ID format")
                    validateNameNotEmpty("Validating name is not empty")
                    validateNameHasContent("Validating chars")
                    validateAliasNotEmpty("Validating alias is not empty")
                    validateAliasHasContent("Validating chars")

                    validateLockNotEmpty("Validation lock is not empty")
                    validateLockProperFormat("Validation lock format")

                    finishUnitValidation("Validation completed")
                }
                chain {
                    title = "Saving unit to DB logic"
                    repoRead("Reading unit from DB")
                    repoPrepareUpdate("Preparing object to update")
                    repoUpdate("Updating unit from DB")
                }
                prepareResult("Preparing response")
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
                    worker("Сlearing lock") { unitValidating.lock = MkplUnitLock(unitValidating.lock.asString().trim()) }

                    validateIdNotEmpty("Validating ID is not empty")
                    validateIdProperFormat("Validating ID format")

                    validateLockNotEmpty("Validation lock is not empty")
                    validateLockProperFormat("Validation lock format")

                    finishUnitValidation("Validation completed")
                }
                chain {
                    title = "Delete unit logic"
                    repoRead("Reading unit from DB")
                    repoPrepareDelete("Preparing object to delete")
                    repoDelete("Deleting unit from DB")
                }
                prepareResult("Preparing response")
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

                repoSearch("Searching unit(s) by filter")
                prepareResult("Preparing response")
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

                    finishUnitValidation("Validation completed")
                }
                repoPrepareSuggest("Preparing object to save")
                repoSuggest("Creating suggested unit in DB")
                prepareResult("Preparing response")
            }
        }.build()
    }

}