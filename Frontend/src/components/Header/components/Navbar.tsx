import React from "react";
import "./navbar.scss";
import { Link } from "react-router-dom";
export default function Navbar() {
  const isAdmin = sessionStorage.getItem("role");
  console.log("admin " + isAdmin);
  return (
    <nav style={{ width: "40%" }}>
      <ul className="header_navbar">
        <li>
          <a href="/">
            <span>Trang chủ</span>
            <i className="fa fa-chevron-down" />
          </a>
        </li>
        <li>
          <a href="/product">
            <span>Thực đơn</span>
            <i className="fa fa-chevron-down" />
          </a>
        </li>
        <li>
          <Link to={"order-history"}>
            <a href="#">
              <span>Lịch sử mua hàng</span>
              <i className="fa fa-chevron-down" />
            </a>
          </Link>
        </li>
        <li>
          <a href="#">
            <span>Liên hệ</span>
            <i className="fa fa-chevron-down" />
          </a>
        </li>
        {isAdmin && ( // Sử dụng && để kiểm tra isAdmin
          <li>
            <a href="/admin">
              <span>Quản lý</span>
              <i className="fa fa-chevron-down" />
            </a>
          </li>
        )}
      </ul>
    </nav>
  );
}
