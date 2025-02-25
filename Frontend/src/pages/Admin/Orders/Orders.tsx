import { Fragment, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { NavLink, useNavigate } from "react-router-dom";
import { Order } from "../../../interfaces/order";
import { orderService } from "../../../services/order";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import Table, { ColumnsType, TableProps } from "antd/es/table";
import { Input } from "antd";
const { Search } = Input;

const Orders: React.FC = () => {
  const dispatch: any = useDispatch();
  const navigate = useNavigate();

  const [ordersList, setOrdersList] = useState<Order[]>([]);
  console.log(ordersList);

  useEffect(() => {
    getOrdersListApi();
  }, []);
  const getOrdersListApi = async () => {
    try {
      const result = await orderService.fetchOrderListApi();
      setOrdersList(result.data);
    } catch (err) {
      console.log(err);
    }
  };
  const fetchDelete = async (id: any) => {
    try {
      const result = await orderService.fetchDeleteListApi(id);
      alert("Xoá thành công!");
      dispatch(getOrdersListApi());
    } catch (errors: any) {
      console.log("errors", errors.response?.data);
    }
  };
  const columns: ColumnsType<Order> = [
    {
      title: "ID",
      dataIndex: "id",
      width: 50,
    },
    {
      title: "User ID",
      dataIndex: "userid",
      width: 50,
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "products",
      render: (products: Order["products"]) =>
        products.map((product) => product.name).join(", "),
      width: 200,
    },
    {
      title: "Giá",
      dataIndex: "products",
      render: (products: Order["products"]) =>
        products.map((product) => `${product.price}đ`).join(", "),
      width: 150,
    },
    {
      title: "Phí ship",
      dataIndex: "phiship",
      width: 150,
    },
    {
      title: "Thời gian",
      dataIndex: "thoigian",
      width: 150,
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      width: 150,
    },
    {
      title: "Address",
      dataIndex: "address",
      render: (address: Order["address"]) =>
        `${address.sonha}, ${address.xa}, ${address.huyen}, ${
          address.tinh || ""
        } - ${address.phone}`,
      width: 250,
    },
    {
      title: "Hành động",
      dataIndex: "id",
      render: (text, order) => {
        return (
          <Fragment>
            <NavLink
              key={1}
              to={`/admin/orders/orderEdit/${order.id}`}
              style={{ marginRight: "20px", fontSize: "30px", color: "blue" }}
            >
              <EditOutlined />
            </NavLink>
            <span
              key={2}
              style={{
                marginRight: "20px",
                fontSize: "30px",
                color: "red",
                cursor: "pointer",
              }}
              onClick={() => {
                if (
                  window.confirm("Bạn có chắc muốn xóa người dùng này không?")
                ) {
                  fetchDelete(order.id);
                }
              }}
            >
              <DeleteOutlined />
            </span>
          </Fragment>
        );
      },
      width: 200,
    },
  ];
  const data: Order[] = ordersList;
  const onChange: TableProps<Order>["onChange"] = (
    pagination,
    filters,
    sorter,
    extra
  ) => {
    console.log("params", pagination, filters, sorter, extra);
  };
  return (
    <div>
      <h1 style={{ marginBottom: "20px", fontSize: "2rem" }}>
        Quản lí đơn hàng
      </h1>
      <Search
        style={{
          marginBottom: "20px",
          backgroundColor: "#4096ff",
          borderRadius: "5px",
          height: "40px",
        }}
        // onSearch={onSearch}
        placeholder="input search text"
        enterButton="Search"
        size="large"
      />
      <Table columns={columns} dataSource={data} onChange={onChange} />
    </div>
  );
};

export default Orders;
