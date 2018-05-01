package com.baeldung.models

import org.springframework.data.rest.core.annotation.RestResource
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class Library @JvmOverloads constructor(
    @Column
    val name: String?=null,

    @OneToOne
    @JoinColumn(name = "address_id")
    @RestResource(path = "libraryAddress")
    val address: Address?=null,

    @OneToMany(mappedBy = "library")
    val books: List<Book>?=listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=-1)
