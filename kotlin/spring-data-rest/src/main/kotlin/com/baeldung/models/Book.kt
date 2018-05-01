package com.baeldung.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class Book @JvmOverloads constructor(

    @Column(nullable = false)
    val title: String?=null,

    @ManyToOne
    @JoinColumn(name = "library_id")
    val library: Library?=null,

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    val authors: List<Author>? = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=-1
)
