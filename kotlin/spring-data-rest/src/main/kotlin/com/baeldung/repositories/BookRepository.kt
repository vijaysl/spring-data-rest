package com.baeldung.repositories

import org.springframework.data.repository.CrudRepository

import com.baeldung.models.Book

interface BookRepository : CrudRepository<Book, Long>
