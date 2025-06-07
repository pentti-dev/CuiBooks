package vn.edu.hcmuaf.fit.fahabook.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.ProductFilter;
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.ProductCreateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.ProductUpdateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ProductResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.StockAction;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductCreateDTO productCreateDTO);

    ProductResponseDTO updateProduct(UUID id, ProductUpdateDTO productCreateDTO);

    void deleteProduct(UUID id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(UUID id) throws AppException;

    List<ProductResponseDTO> findByCategoryId(UUID categoryId);

    List<ProductResponseDTO> getProductByName(String nameProduct);

    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);

    List<ProductResponseDTO> filterProducts(ProductFilter filterInput);

    void saveAll(List<Product> products);

    List<ProductResponseDTO> getProductSale();

    Integer getProductStock(UUID productId);

    void checkQuantityAvailability(UUID id, int inputQuantity, StockAction action);

    BigDecimal getPriceById(UUID productId);
}
