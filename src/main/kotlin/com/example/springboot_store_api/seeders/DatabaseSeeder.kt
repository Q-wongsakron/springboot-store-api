package com.example.springboot_store_api.seeders

import com.example.springboot_store_api.models.Role
import com.example.springboot_store_api.models.RoleName
import com.example.springboot_store_api.repository.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder(private val roleRepository: RoleRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // เช็คว่ามี Role อยู่ในฐานข้อมูลแล้วหรือไม่
        if (roleRepository.count() == 0L) {
            // สร้าง Role ใหม่
            val roles = listOf(
                Role(roleName = RoleName.USER),
                Role(roleName = RoleName.MANAGER),
                Role(roleName = RoleName.ADMIN)
            )
            // บันทึก Role ลงในฐานข้อมูล
            roleRepository.saveAll(roles)
        }
    }
}