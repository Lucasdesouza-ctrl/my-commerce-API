package me.study.my.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.ProductAdmDTO;
import me.study.my.ecommerce.dto.ProductDTO;
import me.study.my.ecommerce.dto.ProductMapper;
import me.study.my.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/catalogue")
    public ResponseEntity<List<ProductDTO>> showAll() {

        var response = productService.findAll();

        return ResponseEntity.ok(response);

    }


    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ProductDTO> createProduct (@RequestBody @Valid ProductDTO productDTO){

         ProductDTO response = productService.createProduct(productDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/adminP")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ProductAdmDTO>> findAllAdm(){
        var response = productService.findAllAdm();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ProductAdmDTO> deleteProduct( @PathVariable UUID productId){

       var product = productService.deleteProduct(productId);

        return ResponseEntity.ok(product);
    }

}
