package com.baeldung.repositories

import org.springframework.data.repository.CrudRepository

import com.baeldung.models.Address

interface AddressRepository : CrudRepository<Address, Long>
