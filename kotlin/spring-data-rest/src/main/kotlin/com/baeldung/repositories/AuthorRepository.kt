package com.baeldung.repositories

import org.springframework.data.repository.CrudRepository

import com.baeldung.models.Author

interface AuthorRepository : CrudRepository<Author, Long>
