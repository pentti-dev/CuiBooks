import React from "react";
import axios, { AxiosError } from "axios"; // Import AxiosError for type checking
import { Button, Checkbox, Form, Input, message } from "antd";
import { NavLink, useNavigate } from "react-router-dom";

interface LoginFormValues {
  username: string;
  password: string;
  rememberMe: boolean;
}

const Login: React.FC = () => {
  const navigate = useNavigate(); // Hook để chuyển hướng

  const onFinish = async (values: LoginFormValues) => {
    try {
      const response = await axios.post(
        "http://localhost:5003/api/login/login",
        {
          username: values.username,
          password: values.password,
        }
      );

      const { message: successMessage, user } = response.data;

      if (successMessage === "Đăng nhập thành công.") {
        message.success(successMessage);
        const role = user.role;
        console.log(role);

        // Lưu thông tin người dùng vào localStorage hoặc sessionStorage tùy thuộc vào trường rememberMe
        localStorage.setItem("user", JSON.stringify(user));
        sessionStorage.setItem("user", JSON.stringify(user));
        sessionStorage.setItem("role", JSON.stringify(role));

        // Chuyển hướng đến trang chính
        navigate("/");
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        // Hiển thị thông báo lỗi từ backend
        if (error.response?.status === 400) {
          message.error(error.response.data.message);
        } else if (error.response?.status === 401) {
          message.error(error.response.data.message);
        } else {
          message.error("Lỗi hệ thống");
        }
      } else {
        // Handle other types of errors
        message.error("Lỗi hệ thống");
      }
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <Form
      className="mx-auto"
      name="basic"
      labelCol={{ span: 5 }}
      wrapperCol={{ span: 16 }}
      style={{ maxWidth: "550px", marginTop: "7rem", paddingTop: "40px" }}
      initialValues={{ rememberMe: true }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item
        label="Tên đăng nhập"
        name="username"
        rules={[{ required: true, message: "Hãy điền tên đăng nhập" }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Mật khẩu"
        name="password"
        rules={[{ required: true, message: "Hãy điền mật khẩu" }]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="rememberMe"
        valuePropName="checked"
        wrapperCol={{ offset: 8, span: 16 }}
      >
        <Checkbox>Lưu đăng nhập</Checkbox>
        <NavLink className="register" to="/register">
          Đăng kí
        </NavLink>
      </Form.Item>

      <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
        <Button className="btn" type="primary" htmlType="submit">
          Đăng nhập
        </Button>
      </Form.Item>
    </Form>
  );
};

export default Login;
