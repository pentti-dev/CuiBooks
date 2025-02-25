import { Fragment, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { NavLink, useNavigate } from "react-router-dom";
import { userService } from "../../../services/user";
import { listUser } from "../../../interfaces/user";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import type { ColumnsType, TableProps } from "antd/es/table";
import { Input, Table } from "antd";
const { Search } = Input;

const Users: React.FC = () => {
  const dispatch: any = useDispatch();
  const navigate = useNavigate();

  const [usersList, setUsersList] = useState<listUser[]>([]);
  console.log(usersList);

  useEffect(() => {
    getUsersListApi();
  }, []);
  const getUsersListApi = async () => {
    try {
      const result = await userService.fetchUserListApi();
      setUsersList(result.data);
    } catch (err) {
      console.log(err);
    }
  };
  const fetchDelete = async (id: any) => {
    try {
      const result = await userService.fetchUserDeleteApi(id);
      alert("Xoá thành công!");
      dispatch(getUsersListApi());
    } catch (errors: any) {
      console.log("errors", errors.response?.data);
    }
  };
  const columns: ColumnsType<listUser> = [
    {
      title: "ID",
      dataIndex: "id",
      sorter: (a, b) => a.id - b.id,
      sortDirections: ["descend"],
      width: 50,
    },
    {
      title: "Full Name",
      dataIndex: "fullName",
      sorter: (a, b) => {
        let fullNameA = a.fullName.toLowerCase().trim();
        let fullNameB = b.fullName.toLowerCase().trim();
        if (fullNameA > fullNameB) {
          return 1;
        }
        return -1;
      },
      width: 200,
    },
    {
      title: "Email",
      dataIndex: "email",
      width: 150,
    },
    {
      title: "Tài khoản",
      dataIndex: "userName",
      width: 150,
    },
    {
      title: "Mật khẩu",
      dataIndex: "password",
      width: 150,
    },
    {
      title: "Địa chỉ",
      dataIndex: "address",
      width: 150,
    },
    {
      title: "Số điện thoại",
      dataIndex: "phone",
      width: 150,
    },
    {
      title: "Ngày sinh",
      dataIndex: "birthDate",
      width: 150,
    },
    {
      title: "Hành động",
      dataIndex: "id",
      render: (text, user) => {
        return (
          <Fragment>
            <NavLink
              key={1}
              to={`/admin/users/edituser/${user.id}`}
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
                  fetchDelete(user.id);
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

  const data = usersList;
  const onChange: TableProps<listUser>["onChange"] = (
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
        Quản lí người dùng
      </h1>
      <button
        type="button"
        onClick={() => navigate(`/admin/users/adduser`)}
        className="btn btn-outline-secondary "
        style={{ marginBottom: "20px" }}
      >
        Thêm người dùng
      </button>
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
export default Users;
