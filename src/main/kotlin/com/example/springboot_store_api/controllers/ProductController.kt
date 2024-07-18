package com.example.springboot_store_api.controllers

import com.example.springboot_store_api.models.Product
import com.example.springboot_store_api.services.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "APIs for managing products")
class ProductController (private val productService: ProductService){

    // ฟังก์ชันสำหรับการดึง product ทั้งหมด
    // GET /api/v1/products

    @Operation(summary = "Get all products", description = "Get all products from database")
    @GetMapping
    fun getAllProducts() = productService.getAllProducts()

    // ฟังก์ชันสำหรับการดึง product จาก id
    // GET /api/v1/products/{id}
    @Operation(summary = "Get product by ID", description = "Get product by ID from database")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id:Int): ResponseEntity<Product>{
        val product = productService.getProductById(id)
        return product.map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    // ฟังก์ชันสำหรับการสร้าง product
    // POST /api/v1/products
    @Operation(summary = "Create new product", description = "Create new product to database")
    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    // ฟังก์ชันสำหรับการเเก้ไข product
    // PUT /api/v1/products/{id}
    @Operation(summary = "Update product by ID", description = "Update product by ID from database")
    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Int, @RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(id, product)
        return ResponseEntity.ok(updatedProduct)
    }

    // ฟังก์ชันสำหรับการลบ product
    // DELETE /api/v1/products/{id}
    @Operation(summary = "Delete product by ID", description = "Delete product by ID from database")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Int): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}