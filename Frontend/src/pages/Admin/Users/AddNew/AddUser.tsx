import React, { useState } from "react";
import { DatePicker, Form, Input, InputNumber, Select, Switch } from "antd";
import { FormikProps, useFormik } from "formik";

import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import { User } from "../../../../interfaces/user";
import moment from "moment";
import { userService } from "../../../../services/user";

type SizeType = Parameters<typeof Form>[0]["size"];

const Adduser: React.FC = () => {
  const [componentSize, setComponentSize] = useState<SizeType | "default">(
    "default"
  );

  const onFormLayoutChange = ({ size }: { size: SizeType }) => {
    setComponentSize(size);
  };
  const navigate = useNavigate();
  const dispatch: any = useDispatch();
  const addUserValidate = Yup.object().shape({
    id: Yup.number()
      .required("Vui lòng nhập ID!")
      .min(1, "Vui lòng nhập ID!")
      .max(10000, "Vui lòng nhập đúng yêu cầu(1-10000)"),
    fullName: Yup.string()
      .required("Tên người dùng không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    email: Yup.string()
      .required("Email không được để trống!")
      .matches(
        /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    userName: Yup.string().required("Tài khoản không được để trống!"),
    password: Yup.string().required("Mật khẩu không được để trống!"),
    address: Yup.string().required("Địa chỉ không được để trống!"),
    phone: Yup.string()
      .required("Số điện thoại không được để trống!")
      .matches(/^[0-9]+$/, "Vui lòng nhậ đúng định dạng!"),
    birthDate: Yup.string().required("Ngày sinh không được để trống!"),
  });
  const formik: FormikProps<User> = useFormik<User>({
    enableReinitialize: true,
    initialValues: {
      id: 0,
      fullName: "",
      email: "",
      userName: "",
      password: "",
      phone: "",
      address: "",
      birthDate: "",
      role: false,
    },
    validationSchema: addUserValidate,
    onSubmit: async (values: any) => {
      await dispatch(addUser(values));
      console.log(values);
    },
  });
  const addUser = async (formData: any) => {
    try {
      const result = await userService.fetchUserAddApi(formData);
      alert("Thêm user thành công!");
      console.log(result);
      navigate("/admin/users");
    } catch (errors) {
      console.log(errors);
    }
  };

  const handleChangeSwitch = (name: string) => {
    return (value: boolean) => {
      formik.setFieldValue(name, value);
    };
  };
  const handleChangeInputNumber = (name: string) => {
    return (value: number | null) => {
      formik.setFieldValue(name, value);
    };
  };
  const handleChangeDatePicker = (value: any) => {
    let birthDate = value ? moment(value).format("DD/MM/YYYY") : "";
    formik.setFieldValue("birthDate", birthDate);
  };
  return (
    <Form
      onSubmitCapture={formik.handleSubmit}
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 14 }}
      layout="horizontal"
      initialValues={{ size: componentSize }}
      onValuesChange={onFormLayoutChange}
      size={componentSize as SizeType}
      style={{ maxWidth: 880 }}
    >
      <h3 style={{ marginBottom: "20px" }}>Thêm người dùng</h3>

      <Form.Item label="ID">
        <InputNumber
          onChange={handleChangeInputNumber("id")}
          min={1}
          max={10000}
        />
        {formik.errors.id && formik.touched.id && (
          <span
            className="form-label text-danger"
            style={{ marginLeft: "10px" }}
          >
            {formik.errors.id}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tên người dùng">
        <Input name="fullName" onChange={formik.handleChange} />
        {formik.errors.fullName && formik.touched.fullName && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.fullName}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Email">
        <Input name="email" onChange={formik.handleChange} />
        {formik.errors.email && formik.touched.email && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.email}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tài khoản">
        <Input name="userName" onChange={formik.handleChange} />
        {formik.errors.userName && formik.touched.userName && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.userName}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Password">
        <Input name="password" onChange={formik.handleChange} />
        {formik.errors.password && formik.touched.password && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.password}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Địa chỉ">
        <Input name="address" onChange={formik.handleChange} />
        {formik.errors.address && formik.touched.address && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.address}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Số ĐT">
        <Input name="phone" onChange={formik.handleChange} />
        {formik.errors.phone && formik.touched.phone && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.phone}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Ngày sinh">
        <DatePicker format={"DD/MM/YYYY"} onChange={handleChangeDatePicker} />
        {formik.errors.birthDate && formik.touched.birthDate && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.birthDate}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Role">
        <Switch onChange={handleChangeSwitch("role")} />
      </Form.Item>
      <Form.Item label="Tác vụ">
        <button type="submit" className="btn btn-primary">
          Cập nhật
        </button>
      </Form.Item>
    </Form>
  );
};

export default Adduser;
