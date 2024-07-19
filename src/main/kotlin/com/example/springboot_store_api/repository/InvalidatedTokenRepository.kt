package com.example.springboot_store_api.repository

import com.example.springboot_store_api.models.InvalidatedToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface InvalidatedTokenRepository : JpaRepository<InvalidatedToken, Long> {
    fun findByToken(token: String): InvalidatedToken?
    fun deleteByExpirationTimeBefore(now: LocalDateTime)
}