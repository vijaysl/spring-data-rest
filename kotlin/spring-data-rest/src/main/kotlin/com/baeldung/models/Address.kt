package com.baeldung.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var location: String? = null

    @OneToOne(mappedBy = "address")
    var library: Library? = null

    constructor() {}

    constructor(location: String) : super() {
        this.location = location
    }
}
