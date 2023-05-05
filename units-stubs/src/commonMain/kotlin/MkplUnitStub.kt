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

}