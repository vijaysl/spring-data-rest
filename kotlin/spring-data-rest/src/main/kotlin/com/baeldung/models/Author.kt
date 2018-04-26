package com.baeldung.models

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var name: String? = null

    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(name = "book_author", joinColumns = arrayOf(JoinColumn(name = "book_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "author_id", referencedColumnName = "id")))
    var books: List<Book>? = null

    constructor() {}

    constructor(name: String) : super() {
        this.name = name
    }
}
