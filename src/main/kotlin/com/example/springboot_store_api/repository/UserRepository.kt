package com.example.springboot_store_api.repository

import org.springframework.data.jpa.repository.JpaRepository

import com.example.springboot_store_api.models.User

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}

