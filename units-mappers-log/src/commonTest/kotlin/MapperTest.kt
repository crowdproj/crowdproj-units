import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            unitResponse = MkplUnit(
                name = "name",
                description = "desc",
                alias = "mm",
                status = MkplUnitStatus.CONFIRMED,
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "something went wrong",
                )
            ),
            state = MkplState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("crowdproj-unit", log.source)
        assertEquals("1234", log.unit?.requestId)
        assertEquals("CONFIRMED", log.unit?.responseUnit?.status)
        val error = log.errors?.firstOrNull()
        assertEquals("something went wrong", error?.message)
        assertEquals("ERROR", error?.level)
    }
}