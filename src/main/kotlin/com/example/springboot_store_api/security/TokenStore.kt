package com.example.springboot_store_api.security

import com.example.springboot_store_api.models.InvalidatedToken
import com.example.springboot_store_api.repository.InvalidatedTokenRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenStore(
    private val invalidatedTokenRepository: InvalidatedTokenRepository
) {

    //จัดการเวลาโทเค็นหมดอายุ
    fun invalidateToken(token: String, expirationTime: Long) {
        val expirationDateTime = LocalDateTime.now().plusSeconds(expirationTime / 1000)
        val invalidatedToken = InvalidatedToken(token = token, expirationTime = expirationDateTime)
        invalidatedTokenRepository.save(invalidatedToken)
    }

    //เช็คว่า ไม่อนุญาติให้ใช้จริงไหม
    fun isTokenInvalidated(token: String): Boolean {
        return invalidatedTokenRepository.findByToken(token) != null
    }
}