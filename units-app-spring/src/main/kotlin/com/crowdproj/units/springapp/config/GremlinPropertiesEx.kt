package com.crowdproj.units.springapp.config

import com.crowdproj.units.repo.gremlin.GremlinProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("gremlin")
class GremlinPropertiesEx
    @ConstructorBinding
    constructor(
        hosts: String,
        port: Int,
        enableSsl: Boolean,
        user: String,
        pass: String
    ) : GremlinProperties(hosts, port, enableSsl, user, pass)