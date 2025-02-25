USE mobile
SELECT * FROM categories

CREATE TABLE customers (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(200),
	PASSWORD VARCHAR(200),
	email VARCHAR(200),
	numer_phone VARCHAR(50),
	role INT
)
DELETE from customerscustomers
	customerscustomers
CREATE TABLE categories(
	id INT AUTO_INCREMENT PRIMARY KEY,
	NAME NVARCHAR(255),
	image VARCHAR(255)
)
SELECT * FROM customers
CREATE TABLE products (
	id INT AUTO_INCREMENT PRIMARY KEY,
	NAME NVARCHAR(200),
	price DOUBLE,
	image VARCHAR(255),
	category_id INT,
	FOREIGN KEY (category_id) REFERENCES categories(id)
)
CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
SELECT * FROM carts
SELECT * FROM cart_items
CREATE TABLE cart_items (
	id INT AUTO_INCREMENT PRIMARY KEY,
	cart_id INT,
	product_id INT,
	quantity INT,
	FOREIGN KEY (cart_id) REFERENCES carts(id),
	FOREIGN KEY (product_id) REFERENCES products(id)
)
DROP TABLE carts

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE,
    status_id INT ,
    address NVARCHAR(250),
    number_phone NVARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
  
);
SHOW CREATE TABLE orders;
ALTER TABLE orders DROP FOREIGN KEY FKf5464gxwc32ongdvka2rtvw96;
ALTER TABLE orders DROP COLUMN address_id;


DROP TABLE orders
SELECT * FROM orders

SELECT * FROM customers


INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP Pavilion 14-DV2073TU 7C0P2PA', 17190000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_12__1_16.png', '- Card đồ họa tích hợp Intel Iris Xe Graphics cho khả năng chỉnh sửa hình ảnh cơ bản, làm poster trên Lightroom, Photoshop, Illustrator,... một cách trơn tru. 16GB RAM DDR4 đáp ứng tốt khả năng đa nhiệm.Cùng ổ cứng SSD 512GB NVMe PCIe cho không gian lưu trữ các tài liệu học tập, làm việc. Đa dạng kết nối với các cổng như: USB Type-C, USB Type-A, HDMI 2.0, jack tai nghe/mic,...');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Máy tính xách tay HP Gaming Victus 15-FB1023AX 94F20PA', 17500000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_14__4_4_1.png', '- Sở hữu màn hình 15.6 inch với lớp vỏ đen thời thượng. CPU Intel Core R5-7535HS hỗ trợ mình giải quyết nhanh gọn hàng loạt tác vụ mà không lo giật lag. Card RTX 2050 giúp giải quyết được khối lượng công việc nặng. RAM 8GB cùng ổ cứng 512 GB SSD ch không gian lưu trữ đủ lớn, lưu trữ tài liệu, hình ảnh,...');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Máy tính xách tay HP Envy X360 BF0112TU 7C0N9PA', 22490000, 5, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/h/p/hp-envy-x360-13-bf0112tu-i5-7c0n9pa-3.jpg', '- Khối lượng nhẹ chỉ 1.34 kg dễ dàng mang theo nó bất cứ đâu. Màn hình cảm ứng nhỏ gọn 13.3 inch, độ phân giải 2.8K cho màu sắc chân thực, sống động.CPU Intel Core i5 - 1230U đi kèm RAM 16 GB và ổ cứng 512 GB> SSD xử lý trơn tru các tác vụ nặng từ làm office đến phần mềm đồ họa cơ bản. Card đồ họa Intel Iris Xe Graphics đáp ứng tốt các nhu cầu đồ họa, thiết kế đồ họa hay chơi game cấu hình nhẹ.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP Pavilion 15-EG2089TU 7C0R1PA', 20190000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_6__17.png', '- Sở hữu thiết kế tối giản thiết kế gọn nhẹ cùng khối lượng chỉ 1.75 kg có thể bỏ máy gọn gàng vào balo và mang theo đến bất kì nơi nào, từ lớp học tới cơ quan mà không gặp phải bất kì trở ngại nào.Màn hình 15.6 inch, độ phân giải Full HD, không chỉ mang đến chất lượng hình ảnh sắc nét, sống động với màn hình lớn mà còn đảm bảo những giây phút giải trí hoàn hảo.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP Envy X360 2IN1 14-ES0013', 17990000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_6_6.png', '- Sử dụng con chip i5 Gen 13 đem lại hiệu năng mạnh mẽ cho phép xử lý tốt các tác vụ văn phòng. RAM 8 GB cùng ổ cứng SSD 512 GB đem lại không gian lưu trữ lớn nhưng mở nhiều tab vẫn mượt mà . Màn hình 14 inch Full HD cho ra hình ảnh chân thật, sắc nét. Khả năng gập 360 độ đem lại sự thuận lợi khi chia sẻ công việc với người lân cận');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP Elitebook 630 G9 6M142PA', 16990000, 5, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_14__1_65.png', '- Sở hữu thiết kế thời thượng với các đường nét thiết kế mềm mại, sang trọng. Màn hình 13.3 inch Full HD mang đến cho bạn những khung hình sắc nét, sống động. CPU Intel Core i5-1235U cho bạn khả năng vận hành xử lý nhanh chóng, mượt mà. Chất lượng đồ họa đỉnh cao với chip đồ họa Intel Iris Xe Graphics');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP 245 G9 6L1N8PA', 9900000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_d_i_20.png', '- Phù hợp sinh viên - nhân viên văn phòng với thiết kế mỏng nhẹ, màn hình 14 inch Full HD. Cân mọi tác vụ học tập, văn phòng với CPU AMD Ryzen 5- 5625U. RAM 8 GB đa nhiệm hỗ trợ mở hàng chục tab duyệt web mà không lo lag, giật. Ổ cứng 256GB SSD thoải mái lưu trữ mọi file, dữ liệu học tập. Camera HD hỗ trợ hình ảnh sắc nét trong các buổi họp, call');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP 14-DQ2055WM 39K15UA - Nhập khẩu chính hãng', 10490000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/1/4/14z90r.png', '- Đáp ứng tốt cho các tác vụ văn phòng - Ram 4GB, Core i3 thế hệ 11 xử lý tốt các phần mềm như word, exel, power point hay lướt web trên trình duyệt. Bền bỉ, cứng cáp - Thiết kế vỏ nhôm chắc chắn.Cảm giác gõ thoải mái - bàn phím kích thước lớn, độ nảy tốt, cảm giác gõ êm tai.Bảo mật tốt - cảm biến vân tay hiện đại giúp mở máy chỉ với 1 cú chạm');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP Pavilion X360 14-DY0168TU 4Y1D3PA', 19990000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/l/a/laptop-hp-pavilion-x360-14-dy0168tu-4y1d3pa-1_1_1.jpg', '- Ấn tượng với màn hình 14 inch cùng khả năng xoay gập 360 độ thao tác dễ dàng, linh hoạt, thỏa sức sáng tạo. Bộ vi xử lý Intel Core i7-1165G7 xử lý tốt các tác vụ văn phòng, thiết kế mượt mà trên PTS, Canva,... RAM 8GB DDR4 đảm bảo laptop cân mọi tác vụ mượt mà, ổn định. Ổ cứng 512GB giúp mở máy nhanh, không gian lưu trữ lớn. Công nghê âm thanh của B&O mang đến âm thanh sống đông.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop HP 14S DQ2644TU', 8999000, 1, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/l/a/laptop-hp-14s-dq2644tu_3_.png', '- Sở hữu màn hình 14 inch nhỏ gọn dễ dàng cầm trên tay di chuyển bất cứ nơi đâu. CPU Intel Core i3 1115G4 cho tốc độ cực nhanh, tiết kiệm pin hiệu quả. RAM 8GB đa nhiệm đảm bảo ổn định khi duyệt web nhiều tab và chạy nhiều ứng dụng cùng lúc. Ổ cứng SSD 256GB M2.PCIe, cho tốc độ khởi động máy, mở ứng dụng và truyền dữ liệu nhanh vượt trội');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Aspire 3 A315-58-53S6 NX.AM0SV.005', 10790000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/1/4/14z90r.png', '- Sở hữu màn hình 14 inch nhỏ gọn dễ dàng cầm trên tay di chuyển bất cứ nơi đâu. CPU Intel Core i3 1115G4 cho tốc độ cực nhanh, tiết kiệm pin hiệu quả.RAM 8GB đa nhiệm đảm bảo ổn định khi duyệt web nhiều tab và chạy nhiều ứng dụng cùng lúc. Ổ cứng SSD 256GB M2.PCIe, cho tốc độ khởi động máy, mở ứng dụng và truyền dữ liệu nhanh vượt trội');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Gaming Acer Nitro 5 Tiger AN515 58 52SP', 20990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/s/v/svfer.jpg', '- Chip Core i5-12500H cùng card rời RTX 3050 cho khả năng chiến các tựa game nặng, chỉnh sửa hình ảnh trên PTS, Render video ngắn mượt mà.Ram 8GB, ổ cứng SSD 512GB mang đến tốc độ xử lý nhanh cùng đa nhiệm mượt mà. Màn hình 15.6 inch độ phân giải Full HD, mang lại chất lượng hiển thị sắc nét. Tích hợp webcam 720p cho phép đàm thoại thông qua video.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Gaming Aspire 7 A715-76-53PJ', 14999000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_6_18.png', '- CPU Intel Core i5-12450H dễ dàng xử lý mọi tác vụ làm việc học tập, làm việc thường ngày. RAM 16GB DDR4 cùng ổ cứng dung lượng 512GB PCIe NVMe SSD có tốc độ đọc ghi siêu cao, thời gian mở máy, truy cập tệp, copy tệp,... nhanh chóng. Màn hình 15.6 inch Full HD cho không gian trải nghiệm nội dung tương đối rộng rãi, màu sắc rõ nét.Công nghệ Acer ComfyView bảo vệ đôi mắt của bạn khỏi những.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Gaming Aspire 5 A515-58GM-53PZ', 15990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_1__4_31.png', '- Trang bị bộ xử lý Intel Core i5-13420H cân mọi tác vụ văn phòng, học tập. RAM 8GB DDR4 có thể nâng cấp tối đa lên 32GB, cho bạn thoải mái lướt web mà không lo lag giật. Ổ cứng 512GB rộng rãi, hỗ trợ lưu trữ tài liệu, tải game thoải mái. Card đồ họa RTX 2050 giúp chỉnh sửa ảnh, video hay chơi game với mức cấu hình cao.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Aspire 7 A715-76G-5806', 19690000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_14__3_3_1.png', '- Màn hình 15.6 inch Full HD cho không gian hiển thị tương đối rộng và rõ nét, màu sắc sống động. CPU Intel Core i5 12450H cùng card RTX 3050 cân mọi tác vụ văn phòng, thoả sức chiến các tựa game yêu thích.RAM 16 GB hỗ trợ nâng cấp tối đa 32 GB thoải mái thao tác với nhiều tabs Chrome mà không lo lag, giật. Ổ cứng SSD 512 GB rộng rãi giúp lưu trữ nhiều tệp tin, tài liệu nhanh chóng.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Aspire 3 A315-59-381E', 9790000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_7_47.png', '- CPU Intel Core i3-1215U cùng RAM 8 GB và ổ cứng 512 GB đem lại hiệu suất mạnh mẽ, phù hợp với nhiều nhu cầu từ làm việc, học tập đến giải trí nhẹ nhàng. Sản phẩm có màn hình 15.6 inch Full HD rộng lớn, cho hình ảnh sắc nét và rõ ràng. Với thiết kế gọn nhẹ và tinh tế, dễ dàng để bạn mang theo bên mình mọi lúc mọi nơi. Laptop cũng có thời lượng pin tốt, cho phép bạn làm việc và.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Gaming Acer Nitro V ANV15-51-58AN', 23990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/1/4/14z90r.png', '- CPU Intel Core i5-13420H cân mọi tựa game từ AAA đến game Esport. GPU GeForce RTX 2050 mới nhất cho đồ họa cực đỉnh, chiến mọi tựa game với mức cài đặt cao. RAM 8 GB DDR5 5200Mhz, khả năng xử lý đa nhiệm và đa tác vụ của máy càng được tăng tốc tối đa. Màn hình 15.6 inch Full HD, tần số quét chuẩn chiến game 144Hz..');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Acer Aspire 7 A715-76G-59MW', 16990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_14__3_3.png', '- Chip Intel Core i5 12450H cùng card RTX 2050 4 GB không chỉ mượt mà mọi tác vụ văn phòng, mà còn cho bạn thoả sức chiến các tựa game yêu thích. RAM 8 GB được hỗ trợ nâng cấp tối đa 32 GB thoải mái thao tác với nhiều tác vụ, chơi game. Ổ cứng SSD 512 GB cho phép lưu trữ nhiều tệp tin, tài liệu vừa tăng tốc độ khởi động hệ thống lên đáng kể. Màn hình 15.6 inch Full HD mang đến không gian hiển thị tương.');
INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Gaming Acer Nitro V ANV15-51-75GS', 28990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_25__1_12.png', '- Trang bị CPU Intel Core i7-13620H xử lý nhanh chóng mọi tác vụ văn phòng hay tận hưởng các tựa game từ AAA đến game Esport. Bộ nhớ RAM 16 GB DDR5 cho khả năng xử lý đa nhiệm và đa tác vụ của máy càng được tăng tốc tối đa. Card đồ họa RTX 4050 tối ưu hiệu suất và năng lượng, chiến mượt các tựa game AAA với mức đồ họa từ Medium/High trở lên..');

INSERT INTO `products` (`name`, `price`, `category_id`, `img`, `detail`) VALUES ('Laptop Gaming Acer Nitro V ANV15-51-57B2', 23990000, 6, 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_13__5_18.png', '- Trang bị CPU Intel Core i7-13620H xử lý nhanh chóng mọi tác vụ văn phòng hay tận hưởng các tựa game từ AAA đến game Esport. Bộ nhớ RAM 16 GB DDR5 cho khả năng xử lý đa nhiệm và đa tác vụ của máy càng được tăng tốc tối đa. Card đồ họa RTX 4050 tối ưu hiệu suất và năng lượng, chiến mượt các tựa game AAA với mức đồ họa từ Medium/High trở lên..');
