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
data class Library(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var name: String? = null,

    @OneToOne
    @JoinColumn(name = "address_id")
    @RestResource(path = "libraryAddress")
    var address: Address? = null,

    @OneToMany(mappedBy = "library")
    var books: List<Book>? = null
)
