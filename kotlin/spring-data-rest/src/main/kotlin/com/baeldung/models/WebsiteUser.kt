package com.baeldung.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class WebsiteUser @JvmOverloads constructor(

    val name: String?=null,
    val email: String?=null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?=-1
)
