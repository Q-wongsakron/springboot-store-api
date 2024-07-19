package com.example.springboot_store_api.services

import com.example.springboot_store_api.dto.ProductCategoryDTO
import com.example.springboot_store_api.repository.ProductRepository
import com.example.springboot_store_api.models.Product
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {  //เอา repo มาสร้าง obj ตรงนี้

    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

    // function write file
    @Throws(IOException::class)
    fun saveFile(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = Paths.get(uploadDir, fileName)

        // สร้างโฟลเดอร์หากไม่พบ
        if (!Files.exists(filePath.parent)) {
            Files.createDirectories(filePath.parent)
        }

        // บันทึกไฟล์
        FileOutputStream(filePath.toFile()).use { fos ->
            fos.write(file.bytes)
        }

        return fileName
    }


    //function delete file
    // ฟังก์ชันสำหรับลบไฟล์
    fun deleteFile(fileName: String) {
        if (fileName != "noimg.jpg") {
            val filePath = Paths.get(uploadDir, fileName)
            try {
                Files.deleteIfExists(filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    // Get all products with pagination and search
    fun getAllProducts(searchQuery: String?, selectedCategory: Int?, pageable: Pageable): Page<ProductCategoryDTO> {
        return productRepository.findBySearchQueryAndCategory(searchQuery, selectedCategory, pageable)
    }

    // Get product by id with category details
    fun getProductByIdWithCategory(id: Int): Optional<ProductCategoryDTO> = productRepository.findProductWithCategory(id)
//    // Create product
//    fun createProduct(product: Product): Product = productRepository.save(product)

    // Create product
    fun createProduct(product: Product, image: MultipartFile?): Product {
        if (image != null) {
            product.productPicture = saveFile(image)
        } else {
            product.productPicture = "noimg.jpg"
        }
        return productRepository.save(product)
    }

//    // Update Product
//    fun updateProduct(id: Int, updateProduct: Product): Product {
//        return if (productRepository.existsById(id)) {
//            updateProduct.id = id
//            productRepository.save(updateProduct)
//        } else {
//            throw RuntimeException("Product not found with id: $id")
//        }
//    }

    // Update Product
    fun updateProduct(id: Int, updateProduct: Product, image: MultipartFile?): Product {
        return if (productRepository.existsById(id)) {
            val existingProduct = productRepository.findById(id).get()

            existingProduct.productName = updateProduct.productName
            existingProduct.unitPrice = updateProduct.unitPrice
            existingProduct.unitInStock = updateProduct.unitInStock
            existingProduct.categoryId = updateProduct.categoryId
            existingProduct.modifiedDate = updateProduct.modifiedDate

            if (image != null) {
                val newFileName = saveFile(image)
                deleteFile(existingProduct.productPicture!!)
                existingProduct.productPicture = newFileName
            }

            productRepository.save(existingProduct)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

//    // Delete Product
//    fun deleteProduct(id: Int) {
//        if (productRepository.existsById(id)) {
//            productRepository.deleteById(id)
//        } else {
//            throw RuntimeException("Product not found with id: $id")
//        }
//    }

    // Delete Product
    fun deleteProduct(id: Int) {
        if (productRepository.existsById(id)) {
            val product = productRepository.findById(id).get()
            deleteFile(product.productPicture!!)
            productRepository.delete(product)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }
}