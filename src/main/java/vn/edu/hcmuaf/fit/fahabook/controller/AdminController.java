package vn.edu.hcmuaf.fit.fahabook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CategoryRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderEditRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.ProductUpdateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.*;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.helper.data.ImportDataHelper;
import vn.edu.hcmuaf.fit.fahabook.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('ADMIN')")
@Validated
public class AdminController {

    CustomerService customerService;

    @Operation(summary = "Lấy số lượng người dùng")
    @GetMapping("/customers")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder()
                .data(customerService.getAllCustomers())
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> getCustomer(@PathVariable UUID customerId) {
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(customerService.getCustomer(customerId))
                .build();
    }

    @Operation(summary = "Thêm người dùng")
    @PostMapping("/customer")
    public ApiResponse<CustomerResponseDTO> addCustomer(
            @RequestBody
            @Valid CreateCustomerDTO customer)
            throws AppException {

        return ApiResponse.<CustomerResponseDTO>builder()
                .data(customerService.saveCustomer(customer))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin người dùng")
    @PatchMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> updateCustomer(
            @PathVariable UUID customerId,
            @RequestBody
            @Valid UpdateCustomerDTO customer) throws AppException {
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(customerService.updateCustomer(customerId, customer))
                .build();
    }

    @Operation(summary = "Xóa người dùng")
    @DeleteMapping("/customer/{customerId}")
    public ApiResponse<Void> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ApiResponse.success("Xóa người dùng thành công");
    }

    OrderService orderService;

    @GetMapping("/order/revenue")
    @Operation(summary = "Lấy doanh thu theo tháng của năm hiện tại")
    public ApiResponse<List<RevenueResponse>> getOrderRevenueAtYear() {

        return ApiResponse.<List<RevenueResponse>>builder()
                .data(orderService.getMonthlyRevenue())
                .build();
    }

    @GetMapping("/order/revenue/{month}/{year}")
    @Operation(summary = "Lấy doanh thu theo tháng và nămi")
    public ApiResponse<RevenueResponse> getOrderRevenueAtYear(
            @PathVariable("month") int month, @PathVariable("year") int year) {

        return ApiResponse.<RevenueResponse>builder()
                .data(orderService.getRevenueByMonth(month, year))
                .build();
    }

    @GetMapping("/order/revenue/year/{year}")
    @Operation(summary = "Lấy doanh thu theo năm")
    public ApiResponse<RevenueResponse> getOrderRevenueAtYear(@PathVariable("year") int year) {

        return ApiResponse.<RevenueResponse>builder()
                .data(orderService.getRevenueByYear(year))
                .build();
    }

    @GetMapping("/order/revenue/date/{date}")
    @Operation(summary = "Lấy doanh thu theo ngày")
    public ApiResponse<RevenueResponse> getOrderRevenueAtYear(@PathVariable("date") LocalDate date) {

        return ApiResponse.<RevenueResponse>builder()
                .data(orderService.getRevenueByDate(date))
                .build();
    }

    @DeleteMapping("/order/{orderId}")
    @Operation(summary = "Hủy đơn hàng")
    public ApiResponse<Void> deleteOrder(@PathVariable("orderId") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.success("Hủy đơn hàng thành công");
    }

    @Operation(summary = "Lấy danh sách đơn hàng")
    @GetMapping("/order/list")
    public ApiResponse<List<OrderResponseDTO>> getAllOrders() {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getAllOrders())
                .build();
    }

    @Operation(summary = "Lấy đơn hàng theo ID")
    @PutMapping("/order/{orderId}")
    public ApiResponse<Void> editOrder(
            @RequestBody OrderEditRequestDTO orderRequestDTO, @PathVariable("orderId") UUID orderId)
            throws AppException {
        orderService.editOrder(orderId, orderRequestDTO);
        return ApiResponse.success("Cập nhật đơn hàng thành công");
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái")
    @GetMapping("/{status}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatus(@PathVariable OrderStatus status) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatus(status))
                .build();
    }

    @Operation(summary = "Cập nhật trạng thái đơn hàng")
    @PatchMapping("/status/{status}/{orderId}")
    public ApiResponse<Void> changeOrderStatus(
            @PathVariable("status") OrderStatus status, @PathVariable("orderId") UUID orderId) {
        try {
            orderService.changeOrderStatus(orderId, status);
            return ApiResponse.success("Cập nhật trạng thái đơn hàng thành công");
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Cập nhật trạng thái đơn hàng thất bại")
                    .build();
        }
    }

    @GetMapping("/order/{id}/next-status")
    @Operation(summary = "Lấy trạng thái tiếp theo của đơn hàng")
    public ApiResponse<Set<OrderStatus>> getNextOrderStatus(@PathVariable("id") UUID orderId) {
        OrderStatus status = orderService.getOrder(orderId).getStatus();
        return ApiResponse.<Set<OrderStatus>>builder().data(status.nextStatus()).build();
    }

    ProductService productService;
    ImportDataHelper importDataHelper;

    @Operation(summary = "Nhập sản phẩm với excel")
    @PostMapping(value = "/product/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ApiResponse<Void>> importProduct(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            return importDataHelper.importProducts(is)
                    .thenApply(products -> {
                        if (products.isEmpty()) {
                            return ApiResponse.<Void>builder()
                                    .code(HttpStatus.NO_CONTENT.value())
                                    .message("Không có sản phẩm nào được nhập")
                                    .build();
                        }
                        productService.saveAll(products);
                        return ApiResponse.success("Thêm thành công");
                    });
        }
    }


    @Operation(summary = "Cập nhật thông tin sản phẩm")
    @PutMapping("/product/{id}")
    public ApiResponse<ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductUpdateDTO dto) {

        return ApiResponse.<ProductResponseDTO>builder()
                .data(productService.updateProduct(id, dto))
                .build();
    }


    @Operation(summary = "Xóa sản phẩm")
    @DeleteMapping("/product/{productId}")
    public ApiResponse<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success("Xóa sản phẩm thành công");
    }


    CategoryService categoryService;

    @Operation(summary = "Nhập danh mục với excel")
    @PostMapping(value = "/category/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ApiResponse<Void>> importCategory(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            return importDataHelper.importCategories(is)
                    .thenApply(categories -> {
                        if (categories.isEmpty()) {
                            return ApiResponse.<Void>builder()
                                    .code(HttpStatus.NO_CONTENT.value())
                                    .message("Không có danh mục nào được nhập")
                                    .build();
                        }
                        categoryService.saveAllCategoryEntries(categories);
                        return ApiResponse.success("Thêm thành công " + categories.size() + " danh mục");
                    });
        }
    }

    @Operation(summary = "Thêm danh mục")
    @PostMapping("/category")
    public ApiResponse<CategoryResponseDTO> addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ApiResponse.<CategoryResponseDTO>builder()
                .data(categoryService.saveCategory(categoryRequestDTO))
                .build();
    }

    @Operation(summary = "Cập nhật danh mục")
    @PutMapping("/category")
    public ApiResponse<CategoryResponseDTO> updateCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return ApiResponse.<CategoryResponseDTO>builder()
                .data(categoryService.updateCategory(categoryRequestDTO))
                .build();
    }

    @Operation(summary = "Xóa danh mục")
    @DeleteMapping("/category/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("Xóa danh mục thành công");
    }

    DiscountService discountService;

    @Operation(summary = "Thêm mã giảm giá")
    @PostMapping(value = "/discount")
    public ApiResponse<DiscountResponseDTO> createDiscount(@Valid @RequestBody CreateDiscountDTO dto) {
        return ApiResponse.<DiscountResponseDTO>builder()
                .data(discountService.create(dto))
                .build();
    }

    @Operation(summary = "Sửa mã giảm giá")
    @PutMapping(value = "/discount")
    public ApiResponse<DiscountResponseDTO> updateDiscount(@Valid @RequestBody UpdateDiscountDTO dto) {
        return ApiResponse.<DiscountResponseDTO>builder()
                .data(discountService.update(dto))
                .build();
    }

    @Operation(summary = "Lấy tất cả mã giảm giá")
    @GetMapping(value = "/discount/all")
    public ApiResponse<List<DiscountResponseDTO>> getDiscount() {
        return ApiResponse.<List<DiscountResponseDTO>>builder()
                .data(discountService.getAllDiscount())
                .build();
    }

    @Operation(summary = "Lấy mã giảm giá theo code")
    @GetMapping(value = "/discount")
    public ApiResponse<DiscountResponseDTO> getAllDiscount(@RequestParam("code") String code) {
        return ApiResponse.<DiscountResponseDTO>builder()
                .data(discountService.getDiscount(code))
                .build();
    }

    @Operation(summary = "Xóa mã giảm giá")
    @DeleteMapping(value = "/discount/{code}")
    public ApiResponse<Void> deleteDiscount(@PathVariable String code) {
        discountService.delete(code);
        return ApiResponse.success();
    }
}
