package com.baeldung.repositories

import org.springframework.data.repository.CrudRepository

import com.baeldung.models.Library

interface LibraryRepository : CrudRepository<Library, Long>
