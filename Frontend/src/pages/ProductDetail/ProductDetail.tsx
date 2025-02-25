import { Link, useParams } from "react-router-dom";
import { ChangeEvent, useEffect, useState } from "react";
import "./ProductDetail.scss";
import { FaHome, FaRegUserCircle, FaStar } from "react-icons/fa";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import ShareIcon from "@mui/icons-material/Share";
import { TbTruckDelivery } from "react-icons/tb";
import { MdOutlinePayment } from "react-icons/md";
import { FaHandHoldingUsd } from "react-icons/fa";
import { FaGift } from "react-icons/fa6";
import { Avatar, Form, Button, List, Input, message, Spin } from "antd";
import moment from "moment";

import { useCart } from "./CartContext";
import { Comment, listProduct, NewComment } from "../../interfaces/product";
import { HeartOutlined, UndoOutlined, UserOutlined } from "@ant-design/icons";
import { productService } from "../../services/product";
import axios, { AxiosResponse } from "axios";

export default function ProductDetail() {
  const [data, setData] = useState<listProduct | null>(null);
  const [comments, setComments] = useState<any[]>([]);
  const [others, setOthers] = useState<listProduct[]>([]);
  const [newCommentText, setNewCommentText] = useState<string>("");
  const [initialized, setInitialized] = useState(false);
  const [amount, setAmount] = useState<number>(1);
  const [isPopupVisible, setPopupVisible] = useState<boolean>(false);
  const [likedComments, setLikedComments] = useState<{
    [key: number]: boolean;
  }>({});
  const { addToCart } = useCart();
  const { productId } = useParams<{ productId: string }>();
  const [loadingOthers, setLoadingOthers] = useState<boolean>(true);
  const [loadingProduct, setLoadingProduct] = useState<boolean>(true);
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const author = user.fullname || "Unknown";
  const formatPrice = (price: number): string => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(price);
  };

  useEffect(() => {
    const fetchProductDetail = async () => {
      try {
        setLoadingProduct(true);
        const result = await productService.fetchProductDetailApi(
          Number(productId)
        );
        setData(result.data);
      } catch (error) {
        console.error("Error fetching product details:", error);
      } finally {
        setLoadingProduct(false);
      }
    };

    const fetchAllProducts = async () => {
      try {
        setLoadingOthers(true);
        const result = await productService.fetchProductApi();
        const allProducts = result.data;
        const otherProducts = allProducts.filter(
          (product) => product.id !== Number(productId)
        );
        setOthers(otherProducts.slice(0, 5)); // Show only 5 other products
      } catch (error) {
        console.error("Error fetching products:", error);
      } finally {
        setLoadingOthers(false);
      }
    };

    fetchProductDetail();
    fetchAllProducts();
  }, [productId]);

  useEffect(() => {
    console.log("Fetched comments:", data?.comments);
  }, [data]);

  useEffect(() => {
    if (data && !initialized) {
      const storedComments = data.comments; // Access comments directly
      console.log("Stored comments:", storedComments);
      if (storedComments) {
        setComments(storedComments);
      }
      setInitialized(true);
    }
  }, [data, initialized]);

  useEffect(() => {
    const initialLikedComments: { [key: number]: boolean } = {};
    comments.forEach((comment: any) => {
      initialLikedComments[comment.id] = false; // Default to not liked
    });
    setLikedComments(initialLikedComments);
  }, [comments]);

  useEffect(() => {
    const adjustMessageZIndex = () => {
      const messageContainer = document.querySelector(
        ".ant-message"
      ) as HTMLElement;
      if (messageContainer) {
        messageContainer.style.zIndex = "100001";
        messageContainer.style.position = "fixed";
      }
    };

    message.config({
      top: 100,
      getContainer: () => {
        adjustMessageZIndex();
        return document.body;
      },
    });
  }, []);

  const handleAddToCart = () => {
    if (data) {
      const productToAdd = {
        id: data.id.toString(),
        name: data.name,
        price: data.price,
        quantity: amount,
        image: data.image,
      };
      addToCart(productToAdd);
      message.success("Thêm sản phẩm vào giỏ hàng thành công");
    }
  };

  const handleBuyNow = () => {
    if (data) {
      const productToAdd = {
        id: data.id.toString(),
        name: data.name,
        price: data.price,
        quantity: data.quantity,
        image: data.image,
      };
      addToCart(productToAdd);
      window.location.href = `/order`;
    }
  };
  const handleAddComment = async () => {
    if (newCommentText.trim() === "") return;

    const newComment: NewComment = {
      content: newCommentText,
      userId: user.id,
    };

    try {
      // Verify the URL and make sure it's correct
      const response = await axios.post(
        `http://localhost:5003/api/product/${productId}/comment`, // Double-check this endpoint
        newComment
      );

      // Check if the request was successful (status code 2xx)
      if (response.status >= 200 && response.status < 300) {
        console.log("Comment added successfully");

        const updatedComments = [...comments, newComment];
        setComments(updatedComments);
        setNewCommentText("");
      } else {
        // Handle other API errors (e.g., 500 Internal Server Error)
        console.error("API Error:", response.status, response.data);
        // Optionally, display an error message to the user
      }
    } catch (error) {
      // Handle network errors or unexpected errors
      console.error("Error adding comment:", error);
      // Optionally, display an error message to the user
    }
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewCommentText(e.target.value);
  };

  const handleLikeClick = (commentId: number) => {
    setLikedComments((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  };

  const handleRevokeClick = (commentId: number) => {
    const updatedComments = comments.filter(
      (comment) => comment.id !== commentId
    );
    setComments(updatedComments);
    sessionStorage.setItem(
      `comments-${productId}`,
      JSON.stringify(updatedComments)
    );
  };
  const handleProductClick = (productId: number) => {
    window.location.href = `/product-detail/${productId}`;
  };
  const renderOthers = (others: listProduct[]) => {
    return others.map((element) => (
      <div key={element.id} className="px-5 py-5 mx-5 others">
        <div
          onClick={() => handleProductClick(element.id)}
          style={{ cursor: "pointer" }}
        >
          <div>
            <img
              src={element.image}
              alt={element.name}
              className="w-full h-auto"
            />
          </div>
          <div>
            <h3 className="text-lg font-semibold text-color1 py-2">
              {element.name}
            </h3>
            <p className="py-1 font-semibold">{formatPrice(element.price)}</p>
          </div>
        </div>
      </div>
    ));
  };

  const renderComments = (comments: any[]) => (
    <ul className="px-20">
      {comments.map((comment) => (
        <li
          key={comment.id}
          style={{
            marginBottom: 20,
            padding: 10,
            borderRadius: "5px",
            background: "#fff",
          }}
        >
          <div className="flex items-center mb-3">
            <UserOutlined
              style={{
                fontWeight: 400,
                marginRight: 20,
                marginLeft: 20,
                fontSize: 30,
                padding: 3,
                border: "2px solid",
                borderRadius: "50%",
                color: "#0000009e",
              }}
            />
            <div>
              <p
                style={{
                  fontSize: 18,
                  fontWeight: 500,
                  marginBottom: 0,
                  color: "#385898",
                }}
              >
                {comment.fullName}
              </p>
              <p style={{ fontSize: 12, color: "#999", marginBottom: 0 }}>
                {moment(comment.createdAt).fromNow()}
              </p>
            </div>
          </div>
          <div className="line_1" style={{ marginBottom: 10 }}></div>
          <p style={{ marginBottom: 30, fontSize: 20, marginLeft: 20 }}>
            {comment.content}
          </p>
          <div className="line_1" style={{ marginBottom: 10 }}></div>
          <div className="flex items-center mx-3 py-2">
            <div
              className="flex items-center mr-4"
              style={{ cursor: "pointer" }}
              onClick={() => handleLikeClick(comment.id)}
            >
              <HeartOutlined
                style={{
                  color: likedComments[comment.id] ? "red" : "inherit",
                }}
              />
              <span
                style={{
                  marginLeft: 5,
                  color: likedComments[comment.id] ? "red" : "inherit",
                }}
              >
                Thích
              </span>
            </div>
            <div
              className="flex items-center ml-4"
              style={{ cursor: "pointer" }}
              onClick={() => handleRevokeClick(comment.id)}
            >
              <UndoOutlined />
              <span style={{ marginLeft: 5 }}>Thu hồi</span>
            </div>
          </div>
        </li>
      ))}
    </ul>
  );

  if (loadingProduct || loadingOthers) {
    return <Spin tip="Đang tải dữ liệu..." />;
  }

  if (!data) {
    return <div>Không tìm thấy sản phẩm.</div>;
  }
  return (
    <div style={{ paddingTop: 80 }}>
      <div>
        <div className="path flex items-center">
          <span className="mx-2">
            <FaHome />
          </span>
          <span style={{ fontWeight: 400 }}>Home / Shop / Detail</span>
        </div>

        <div className="px-10 py-5">
          <div
            className="detail_container mx-auto py-10 product-details flex justify-center	"
            style={{ width: 1280, background: "#fff" }}
          >
            <div className="product_img">
              <img src={data.image} style={{ width: 500, height: 530 }} />
              <div className="flex flex-wrap justify-center items-center py-10">
                <button className="px-2 py-1 flex justify-center items-center font-semibold text-sm text-gray-700">
                  <ShareIcon />
                  <span className="ml-2">Chia sẻ</span>
                </button>
                <button className="px-2 py-1 flex justify-center items-center font-semibold text-sm text-gray-700">
                  <FavoriteBorderIcon />
                  <span className="ml-2">Yêu thích</span>
                </button>
              </div>
            </div>
            <div>
              <p className="mb-2">
                <button className="mr-3"></button>
                <span className="font-semibold text-xl sm:text-3xl tracking-widest leading-relaxed text-gray-900">
                  {data.name}
                </span>
              </p>

              {/*<div>*/}
              {/*  <p className="text-base font-normal tracking-widest mx-2">*/}
              {/*    Mã sản phẩm: {detail.id}*/}
              {/*  </p>*/}
              {/*</div>*/}
              <div className="py-3">
                <span className="text-lg font-semibold tracking-widest mx-2 product_price">
                  {formatPrice(data.price)}
                </span>
              </div>
              <div
                className="my-3"
                style={{ padding: 10, background: "#f8f8f8", borderRadius: 10 }}
              >
                <div
                  style={{
                    background: "#f33828",
                    borderRadius: 5,
                    display: "flex",
                    alignItems: "center",
                  }}
                >
                  <FaGift style={{ color: "#fff", marginLeft: 10 }} />
                  {/* <p
                    style={{
                      background: "#f33828",
                      width: "70%",
                      borderRadius: 5,
                      display: "flex",
                      alignItems: "center",
                    }}
                > */}
                  <FaGift style={{ color: "#fff", marginLeft: 10 }} />
                  <p
                    style={{
                      padding: "5px 10px",
                      color: "#fff",
                      fontWeight: 500,
                      margin: 0,
                    }}
                  >
                    Thân chúc quý khách dùng ngon miệng
                  </p>
                </div>

                <ul
                  className="promotion_box"
                  style={{
                    padding: 10,
                    background: "#fff",
                    marginTop: 10,
                    listStyleType: "disc",
                  }}
                >
                  <li>Freeship với đơn hàng 5 phần trở lên</li>
                  <li>Tặng kèm trái cây tráng miệng mỗi ngày</li>
                  <li>Cuối tuần tặng kèm nước uống</li>
                </ul>
              </div>
              <div className="flex items-center pt-3">
                <div className="flex" style={{ marginRight: 20 }}>
                  <button
                    className="decrease"
                    onClick={() =>
                      setAmount((prev) => (prev > 1 ? prev - 1 : prev))
                    }
                  >
                    -
                  </button>
                  <span className="amount">{amount}</span>
                  <button
                    className="increase"
                    onClick={() => setAmount((prev) => prev + 1)}
                  >
                    +
                  </button>
                </div>
                <div>
                  <button className="addToCart" onClick={handleAddToCart}>
                    Thêm vào giỏ hàng
                  </button>
                </div>
              </div>
              <div onClick={handleBuyNow}>
                <button className="buyNow">Mua ngay</button>
              </div>

              <p
                className="text-center"
                style={{ padding: "20px 10px", width: 480 }}
              >
                Gọi đặt mua{" "}
                <span style={{ color: "#87c84a", fontWeight: 500 }}>
                  0902.504.708
                </span>{" "}
                (7:30 - 12:00)
              </p>
              <div style={{ width: 480 }}>
                <ul className="product-policises list-unstyled py-3 px-3 m-0">
                  <li>
                    <div>
                      <TbTruckDelivery
                        style={{
                          width: 45,
                          fontSize: 30,
                          color: "rgb(75 128 26)",
                        }}
                      />
                    </div>
                    <div>Giao hàng siêu tốc trong 1h</div>
                  </li>
                  <li>
                    <div>
                      <FaHandHoldingUsd
                        style={{
                          width: 45,
                          fontSize: 30,
                          color: "rgb(75 128 26)",
                        }}
                      />
                    </div>
                    <div>Combo 2 món đa dạng mỗi ngày</div>
                  </li>
                  <li>
                    <div>
                      <MdOutlinePayment
                        style={{
                          width: 45,
                          fontSize: 25,
                          color: "rgb(75 128 26)",
                        }}
                      />
                    </div>
                    <div>Thanh toán đa nền tảng</div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div className="line"></div>
        <div className="comment">
          <h2 className="text-2xl">Hỏi đáp - Bình luận</h2>
          <p className="text-base font-medium" style={{ marginBottom: 50 }}>
            {comments.length} bình luận
          </p>
          <div>{renderComments(comments)}</div>
          <div>
            <h4 style={{ marginTop: 50 }}>Thêm bình luận</h4>
            <input
              className="setNewComment"
              placeholder="Bình luận..."
              value={newCommentText}
              onChange={handleInputChange}
            />
            <button className="submit_comment" onClick={handleAddComment}>
              Thêm
            </button>
          </div>
        </div>
        <div>
          <div className="py-1 other_products"></div>
          <div className="px-20 py-3 other_products">
            <h3 className="text-2xl font-semibold text-white">Sản phẩm khác</h3>
          </div>
          <div className="flex flex-wrap py-5">{renderOthers(others)}</div>
        </div>
      </div>
    </div>
  );
}
