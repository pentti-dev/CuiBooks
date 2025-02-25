import React from "react";
import axios from "axios";
import { Form, Input, Button, DatePicker, Select, message } from "antd";
import "./register.scss";


const { Option } = Select;

const Register: React.FC = () => {
  const [form] = Form.useForm();
  const [tinhData, setTinhData] = React.useState<
    { id: string; full_name: string }[]
  >([]);
  const [quanData, setQuanData] = React.useState<
    { id: string; full_name: string }[]
  >([]);
  const [phuongData, setPhuongData] = React.useState<
    { id: string; full_name: string }[]
  >([]);
  const [selectedTinh, setSelectedTinh] = React.useState<string>("");
  const [selectedQuan, setSelectedQuan] = React.useState<string>("");
  const [selectedPhuong, setSelectedPhuong] = React.useState<string>("");

  React.useEffect(() => {
    // Fetch tinh data
    axios
      .get("https://esgoo.net/api-tinhthanh/1/0.htm")
      .then((response) => setTinhData(response.data.data))
      .catch((error) => console.error("Error fetching tinh data:", error));
  }, []);

  const handleTinhChange = (value: string) => {
    setSelectedTinh(value);
    axios
      .get(`https://esgoo.net/api-tinhthanh/2/${value}.htm`)
      .then((response) => {
        setQuanData(response.data.data);
        setSelectedQuan(""); // Reset quan when tinh changes
        setSelectedPhuong(""); // Clear phuong data
        setPhuongData([]); // Clear phuong data
        updateAddress(value, "", ""); // Update address field
      })
      .catch((error) => console.error("Error fetching quan data:", error));
  };

  const handleQuanChange = (value: string) => {
    setSelectedQuan(value);
    axios
      .get(`https://esgoo.net/api-tinhthanh/3/${value}.htm`)
      .then((response) => {
        setPhuongData(response.data.data);
        setSelectedPhuong(""); // Clear phuong data
        updateAddress(selectedTinh, value, ""); // Update address field
      })
      .catch((error) => console.error("Error fetching phuong data:", error));
  };

  const handlePhuongChange = (value: string) => {
    setSelectedPhuong(value);
    updateAddress(selectedTinh, selectedQuan, value); // Update address field
  };

  const updateAddress = (tinh: string, quan: string, phuong: string) => {
    const tinhName = tinhData.find((item) => item.id === tinh)?.full_name || "";
    const quanName = quanData.find((item) => item.id === quan)?.full_name || "";
    const phuongName =
      phuongData.find((item) => item.id === phuong)?.full_name || "";

    const address = `${phuongName}, ${quanName}, ${tinhName}`;
    form.setFieldsValue({ address });
  };

  const onFinish = (values: any) => {
    console.log("Received values of form: ", values);
  
    // Lọc chỉ các trường cần thiết để gửi
    const {
      address,
      'date-picker': datePicker,
      email,
      fullname,
      password,
      phone,
      username,
    } = values;
  
    const dataToSend = {
      address,
      birthDate: datePicker,
      email,
      fullname,
      password,
      phone,
      username,
    };
  
    // Gửi yêu cầu POST đến API để lưu thông tin đăng ký
    axios
      .post("http://localhost:5003/api/register", dataToSend)
      .then((response) => {
        console.log("Registration successful:", response.data);
        message.success(response.data.message || "Đăng ký thành công!");
      })
      .catch((error) => {
        console.error("There was an error!", error);
  
        // Hiển thị thông báo lỗi từ API
        if (error.response && error.response.data) {
          const { message: errorMessage } = error.response.data;
          message.error(errorMessage || "Có lỗi xảy ra trong quá trình đăng ký.");
        } else {
          message.error("Có lỗi xảy ra trong quá trình đăng ký.");
        }
      });
  };
  
  

  return (
    <Form
      className="register"
      form={form}
      name="register"
      onFinish={onFinish}
      labelCol={{ span: 6 }}
      wrapperCol={{ span: 16 }}
      initialValues={{ prefix: "86" }}
      style={{
        maxWidth: 600,
        margin: "0 auto",
        marginTop: "5%",
        paddingTop: "10px",
      }} // Căn giữa form
      scrollToFirstError
    >
      <Form.Item
        name="fullname"
        label="Họ và tên"
        rules={[{ required: true, message: "Hãy nhập đầy đủ họ và tên!" }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name="email"
        label="E-mail"
        rules={[
          { type: "email", message: "E-mail không hợp lệ!" },
          { required: true, message: "Hãy nhập địa chỉ email!" },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="password"
        label="Mật khẩu"
        rules={[{ required: true, message: "Hãy nhập mật khẩu!" }]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="username"
        label="Tên đăng nhập"
        rules={[
          {
            required: true,
            message: "Hãy nhập tên đăng nhập!",
            whitespace: true,
          },
        ]}
      >
        <Input />
      </Form.Item>

      {/* Select Tỉnh Thành */}
      <Form.Item
        name="province"
        label="Tỉnh Thành"
        rules={[{ required: true, message: "Hãy chọn tỉnh thành!" }]}
      >
        <Select value={selectedTinh} onChange={handleTinhChange}>
          <Option value="">Chọn Tỉnh Thành</Option>
          {tinhData.map(({ full_name, id }) => (
            <Option key={id} value={id}>
              {full_name}
            </Option>
          ))}
        </Select>
      </Form.Item>

      {/* Select Quận Huyện */}
      <Form.Item
        name="quan"
        label="Quận Huyện"
        rules={[{ required: true, message: "Hãy chọn quận huyện!" }]}
      >
        <Select value={selectedQuan} onChange={handleQuanChange}>
          <Option value="">Chọn Quận Huyện</Option>
          {quanData.map(({ full_name, id }) => (
            <Option key={id} value={id}>
              {full_name}
            </Option>
          ))}
        </Select>
      </Form.Item>

      {/* Select Phường Xã */}
      <Form.Item
        name="phuong"
        label="Phường Xã"
        rules={[{ required: true, message: "Hãy chọn phường xã!" }]}
      >
        <Select value={selectedPhuong} onChange={handlePhuongChange}>
          <Option value="">Chọn Phường Xã</Option>
          {phuongData.map(({ full_name, id }) => (
            <Option key={id} value={id}>
              {full_name}
            </Option>
          ))}
        </Select>
      </Form.Item>

      {/* Hidden address input */}
      <Form.Item
        hidden
        name="address"
        label="Địa chỉ"
        rules={[{ required: true, message: "Hãy nhập địa chỉ!" }]}
      >
        <Input readOnly />
      </Form.Item>

      <Form.Item
        name="phone"
        label="Số điện thoại"
        rules={[{ required: true, message: "Hãy nhập số điện thoại!" }]}
      >
        <Input style={{ width: "100%" }} />
      </Form.Item>

      <Form.Item
        name="date-picker"
        label="Ngày sinh"
        rules={[{ required: true, message: "Hãy chọn ngày sinh!" }]}
      >
        <DatePicker />
      </Form.Item>

      <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
        <Button className="submit" type="primary" htmlType="submit">
          Đăng ký
        </Button>
      </Form.Item>
    </Form>
  );
};

export default Register;
