package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.mapper.ProductMapper;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.ProductService;
import com.example.mobileapi.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    ProductMapper productMapper;

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO dto) {
        return productMapper.toProductResponseDTO(productRepository.save(productMapper.toProduct(dto)));

    }

    public BigDecimal getPriceById(UUID id) {
        Product product = getById(id);
        double discountPercent = product.getDiscount();
        BigDecimal originalPrice = product.getPrice();
        // Tính toán giá nếu có giảm giá thì nhân với giảm giá nến khôngkhông thì không
        // cần
        // nhân
        return discountPercent != 0.0 ? originalPrice.multiply(BigDecimal.valueOf(discountPercent)) : originalPrice;
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        product.setId(id);
        productRepository.save(product);
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productMapper.toProductResponseDTOList(productRepository.findAll());
    }

    @Override
    public ProductResponseDTO getProductById(UUID id) throws AppException {
        return productMapper.toProductResponseDTO(productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    @Override
    public List<ProductResponseDTO> findByCategoryId(UUID categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toProductResponseDTOList(products);

    }

    @Override
    public List<ProductResponseDTO> getProductByName(String nameProduct) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(nameProduct);
        return productMapper.toProductResponseDTOList(products);
    }

    @Override
    public List<ProductResponseDTO> findByNameContainingIgnoreCase(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toProductResponseDTOList(products);
    }

    @Override
    public List<ProductResponseDTO> filterProducts(String name, UUID categoryId, Language language, Integer minPrice,
            Integer maxPrice, BookForm form) {
        Specification<Product> spec = Specification.where(null);
        if (StringUtils.hasText(name)) {
            spec = spec.and(ProductSpecifications
                    .nameContains(name));
        }
        if (categoryId != null) {
            spec = spec.and(ProductSpecifications.hasCategoryId(categoryId));
        }
        if (language != null) {
            spec = spec.and(ProductSpecifications.hasLanguage(language));
        }
        if (form != null) {
            spec = spec.and(ProductSpecifications.hasForm(form));
        }
        if (minPrice != null && maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceBetween(minPrice, maxPrice));
        }
        return productMapper.toProductResponseDTOList(productRepository.findAll(spec));

    }

    public Product getById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public boolean existById(UUID id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional
    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    @Override
    public List<ProductResponseDTO> getProductSale() {
        // Giả sử discount là một trường trong thực thể Product và có giá trị > 0 khi có
        // giảm giá
        List<Product> productsOnSale = productRepository.findByDiscountGreaterThan(0);
        return productMapper.toProductResponseDTOList(productsOnSale);
    }
}
