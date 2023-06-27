package com.crowdproj.units.repo.gremlin.mappers

import com.crowdproj.units.common.models.MkplUnit
import com.crowdproj.units.common.models.MkplUnitId
import com.crowdproj.units.common.models.MkplUnitLock
import com.crowdproj.units.common.models.MkplUnitStatus
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_NAME
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_DESCRIPTION
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_ALIAS
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_ID
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_LOCK
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_STATUS
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.units.repo.gremlin.UnitGremlinConst.RESULT_SUCCESS
import com.crowdproj.units.repo.gremlin.exceptions.WrongEnumException
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty

fun GraphTraversal<Vertex, Vertex>.addMkplUnit(unit: MkplUnit): GraphTraversal<Vertex, Vertex> =
    this
        .property(VertexProperty.Cardinality.single, FIELD_NAME, unit.name.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_DESCRIPTION, unit.description.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_ALIAS, unit.alias.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_LOCK, unit.lock.takeIf { it != MkplUnitLock.NONE }?.asString())
        .property(
            VertexProperty.Cardinality.single,
            FIELD_STATUS,
            unit.status.takeIf { it != MkplUnitStatus.NONE }?.name
        )

fun GraphTraversal<Vertex, Vertex>.listMkplUnit(result: String = RESULT_SUCCESS): GraphTraversal<Vertex, MutableMap<String, Any>> =
    project<Any?>(
        FIELD_ID,
        FIELD_LOCK,
        FIELD_NAME,
        FIELD_DESCRIPTION,
        FIELD_ALIAS,
        FIELD_STATUS,
        FIELD_TMP_RESULT,
    )
        .by(gr.id<Vertex>())
        .by(FIELD_LOCK)
        .by(FIELD_NAME)
        .by(FIELD_DESCRIPTION)
        .by(FIELD_ALIAS)
        .by(FIELD_STATUS)
        .by(gr.constant(result))
//        .by(elementMap<Vertex, Map<Any?, Any?>>())

fun Map<String, Any?>.toMkplUnit(): MkplUnit = MkplUnit(
    id = (this[FIELD_ID] as? String)?.let { MkplUnitId(it) } ?: MkplUnitId.NONE,
    lock = (this[FIELD_LOCK] as? String)?.let { MkplUnitLock(it) } ?: MkplUnitLock.NONE,
    name = (this[FIELD_NAME] as? String) ?: "",
    description = (this[FIELD_DESCRIPTION] as? String) ?: "",
    alias = (this[FIELD_ALIAS] as? String) ?: "",
    status = when (val value = this[FIELD_STATUS] as? String) {
        MkplUnitStatus.CONFIRMED.name -> MkplUnitStatus.CONFIRMED
        MkplUnitStatus.SUGGESTED.name -> MkplUnitStatus.SUGGESTED
        MkplUnitStatus.DEPRECATED.name -> MkplUnitStatus.DEPRECATED
        MkplUnitStatus.DELETED.name -> MkplUnitStatus.DELETED
        null -> MkplUnitStatus.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "unitType = '$value' cannot be converted to ${MkplUnitStatus::class}"
        )
    },
)
