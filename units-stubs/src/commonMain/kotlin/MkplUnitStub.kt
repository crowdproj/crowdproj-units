import com.crowdproj.units.common.models.*

object MkplUnitStub {
    fun get() = MkplUnit(
        id = MkplUnitId("123"),
        name = "Ampere",
        alias = "A",
        description = "unit of electric current in the International System of Units",
        status = MkplUnitStatus.CONFIRMED,
        systemUnitId = MkplSystemUnitId.NONE
    )

    fun prepareResult(block: MkplUnit.() -> Unit): MkplUnit = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        mkplUnitSearch("1", filter),
        mkplUnitSearch("2", filter),
        mkplUnitSearch("3", filter),
        mkplUnitSearch("4", filter),
    )

    private fun mkplUnitSearch(id: String, filter: String) =
        mkplUnit(get(), id, filter)

    private fun mkplUnit(base: MkplUnit, id: String, filter: String) = base.copy(
        id = MkplUnitId(id),
        name = "$filter $id",
        description = "desc $filter $id",
    )
}