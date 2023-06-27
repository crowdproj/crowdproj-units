package com.crowdproj.units.repo.gremlin

open class GremlinProperties (
    val hosts: String = "localhost",
    val port: Int = 8182,
    val enableSsl: Boolean = false,
    val user: String = "root",
    val pass: String = "root_root",
)

