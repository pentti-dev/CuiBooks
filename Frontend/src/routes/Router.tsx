import React, { useState, useEffect } from "react";
import { Routes, Route, useRoutes } from "react-router-dom";
import HomeLayout from "../layout/HomeLayout/HomeLayout";
import ProductDetail from "../pages/ProductDetail/ProductDetail";
import Login from "../pages/login/Login";
import Register from "../pages/register/Register";
import Tracking from "../pages/Tracking/Tracking";
import Account from "../pages/Account/Account";
import Product from "../pages/product/Product";
import Products from "../pages/Admin/Products/Products";
import Cart from "../pages/cart/Cart";
import Order from "../pages/order/Order";
import OrderDetails from "../pages/order/OrderDetails";
import Home from "../pages/Home/Home";
import OrderHistoryPage from "../pages/history/OrderHistory";
import withAuthGuard from "../guards/AuthGuard";
import AdminLayout from "../layout/AdminLayout/AdminLayout";
import Addnew from "../pages/Admin/Products/AddNew/AddNew";
import Users from "../pages/Admin/Users/Users";
import Orders from "../pages/Admin/Orders/Orders";
import Adduser from "../pages/Admin/Users/AddNew/AddUser";
import Edit from "../pages/Admin/Products/Edit/Edit";
import StatisticsPage from "../pages/Admin/Statistics/Statistics";
import Statistics from "../pages/Admin/Statistics/Statistics";
import EditUser from "../pages/Admin/Users/EditUser/EditUser";
import EditOrder from "../pages/Admin/Orders/EditOrder/EditOrder";

export default function Router() {
  const [isCartVisible, setIsCartVisible] = useState(false);

  useEffect(() => {
    const pathname = window.location.pathname;
    if (pathname === "/cart") {
      setIsCartVisible(true);
    } else {
      setIsCartVisible(false);
    }

    // eslint-disable-next-line no-restricted-globals
  }, [location.pathname]);
  useRoutes([
    {
      path: "/",
      element: <HomeLayout />,
      children: [
        {
          path: "/",
          element: <Home />,
        },
        {
          path: "/product-detail/:productId",
          element: <ProductDetail />,
        },
      ],
    },
    {
      path: "/login",
      element: <Login />,
    },
    {
      path: "/register",
      element: <Register />,
    },
    {
      path: "/",
      element: <HomeLayout />,
      children: [
        {
          path: "/login",
          element: <Login />,
        },
        {
          path: "/register",
          element: <Register />,
        },
        {
          path: "/account",
          element: <Account />,
        },
        {
          path: "/order-history",
          element: <OrderHistoryPage />,
        },
        {
          path: "/product",
          element: <Product />,
        },
        {
          path: "/cart",
          element: <Cart />,
        },
        {
          path: "/order",
          element: <Order />,
        },

        {
          path: "/orderDetail",
          element: <OrderDetails />,
        },

        {
          path: "/",
          element: <HomeLayout />,
          children: [
            {
              path: "/tracking",
              element: <Tracking />,
            },
          ],
        },
      ],
    },
  ]);
  return (
    <Routes>
      <Route path="/" element={<HomeLayout />}>
        <Route path="/" element={<Home />} />
        <Route
          path="/product-detail/:productId"
          element={React.createElement(withAuthGuard(ProductDetail))}
        />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/cart"
          element={React.createElement(withAuthGuard(Cart))}
        />
        <Route
          path="/order"
          element={React.createElement(withAuthGuard(Order))}
        />
        <Route
          path="/orderDetail"
          element={React.createElement(withAuthGuard(OrderDetails))}
        />
        <Route
          path="/account"
          element={React.createElement(withAuthGuard(Account))}
        />
        <Route
          path="/order-history"
          element={React.createElement(withAuthGuard(OrderHistoryPage))}
        />
        <Route
          path="/product"
          element={React.createElement(withAuthGuard(Product))}
        />
      </Route>
      <Route path="/admin" element={<AdminLayout />}>
        <Route path="/admin/products" element={<Products />} />
        <Route path="/admin/users" element={<Users />} />
        <Route path="/admin/orders" element={<Orders />} />
        <Route path="/admin/products/addnew" element={<Addnew />} />
        <Route path="/admin/users/adduser" element={<Adduser />} />
        <Route path="/admin/users/edituser/:id" element={<EditUser />} />
        <Route path="/admin/editProduct/:id" element={<Edit />} />
        <Route path="/admin/statistics" element={<StatisticsPage />} />
        <Route path="/admin/orders/orderEdit/:id" element={<EditOrder />} />
      </Route>
    </Routes>
  );
}
