package com.baeldung.models

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class Author @JvmOverloads constructor(

    @Column(nullable = false)
    val name: String?=null,

    @ManyToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = arrayOf(JoinColumn(name = "book_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "author_id", referencedColumnName = "id")))
    val books: List<Book>? = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=-1

)
