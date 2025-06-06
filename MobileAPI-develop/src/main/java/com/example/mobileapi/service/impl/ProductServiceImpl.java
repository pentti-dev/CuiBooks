package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.ProductFilter;
import com.example.mobileapi.dto.request.create.ProductCreateDTO;
import com.example.mobileapi.dto.request.update.ProductUpdateDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.StockAction;
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
    public ProductResponseDTO saveProduct(ProductCreateDTO dto) {
        return productMapper.toProductResponseDTO(productRepository.save(productMapper.toProduct(dto)));

    }

    public BigDecimal getPriceById(UUID id) {

        Product product = getById(id);
        if (product == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        double discountPercent = product.getDiscount();
        BigDecimal originalPrice = product.getPrice();

        return originalPrice.subtract(originalPrice.multiply(BigDecimal.valueOf(discountPercent)));
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductUpdateDTO dto) {
        Product product = productMapper.toProduct(dto);
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
        return productMapper.toProductResponseDTO(productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
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
    public List<ProductResponseDTO> filterProducts(ProductFilter filter) {
        Specification<Product> spec = Specification.where(null);
        if (StringUtils.hasText(filter.getName())) {
            spec = spec.and(ProductSpecifications.nameContains(filter.getName()));
        }
        if (filter.getCategoryId() != null) {
            spec = spec.and(ProductSpecifications.hasCategoryId(filter.getCategoryId()));
        }
        if (filter.getForm() != null) {
            spec = spec.and(ProductSpecifications.hasForm(filter.getForm()));
        }
        spec = spec.and(ProductSpecifications.priceBetween(filter.getMinPrice(), filter.getMaxPrice()));
        spec = spec.and(ProductSpecifications.discountBetween(filter.getMinDiscount(), filter.getMaxDiscount()));

        spec = spec.and(ProductSpecifications.publishYearBetween(filter.getMinYear(), filter.getMaxYear()));

        spec = spec.and(ProductSpecifications.weightBetween(filter.getMinWeight(), filter.getMaxWeight()));
        if (StringUtils.hasText(filter.getSize())) {
            spec = spec.and(ProductSpecifications.sizeContains(filter.getSize()));
        }

        spec = spec.and(ProductSpecifications.pageNumberBetween(filter.getMinPages(), filter.getMaxPages()));


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

    @Override
    public Integer getProductStock(UUID productId) {
        return getProductById(productId).getStock();
    }

    @Override
    @Transactional
    public void checkQuantityAvailability(UUID id, int quantity, StockAction action) {
        Integer stock = getProductStock(id);


        switch (action) {
            case CHECK:
                if (stock < quantity) {
                    throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
                }
                if (stock <= 0) {
                    throw new AppException(ErrorCode.OUT_OF_STOCK);
                }
                break;

            case DECREASE:
                if (stock <= 0) {
                    throw new AppException(ErrorCode.OUT_OF_STOCK);
                }
                if (stock < quantity) {
                    throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
                }
                int updatedRows = productRepository.reduceStock(id, quantity);
                if (updatedRows == 0) {
                    throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
                }
                break;

            case INCREASE:
                productRepository.increaseStock(id, quantity);
                break;

            default:
                throw new AppException(ErrorCode.STOCK_UNVAILABLE);
        }
    }


}