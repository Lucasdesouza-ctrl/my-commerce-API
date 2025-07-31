package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.ProductAdmDTO;
import me.study.my.ecommerce.dto.ProductDTO;
import me.study.my.ecommerce.dto.ProductMapper;
import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ProductEntity;
import me.study.my.ecommerce.repository.CartItemRepository;
import me.study.my.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CartItemRepository cartItemRepository;

    public ProductDTO createProduct(ProductDTO productDTO){

        ProductEntity product = productMapper.toEntity(productDTO);

        productRepository.save(product);


        return productDTO;
    }

    public ProductAdmDTO deleteProduct(UUID productId){
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found :" + productId));

       productRepository.delete(product);


       return productMapper.productAdmtoDTO(product);
    }

    public List<ProductDTO> findAll(){
        return productRepository.findAll().stream()
                .map(productMapper::toDTO).toList();
    }
    public List<ProductAdmDTO> findAllAdm(){
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::productAdmtoDTO)
                .toList();
    }



}
