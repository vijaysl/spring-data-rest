package com.baeldung.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var title: String? = null

    @ManyToOne
    @JoinColumn(name = "library_id")
    var library: Library? = null

    @ManyToMany(mappedBy = "books")
    var authors: List<Author>? = null

    constructor() {}

    constructor(title: String) : super() {
        this.title = title
    }
}
