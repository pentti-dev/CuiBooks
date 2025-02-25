import * as Yup from "yup";
import React, { useState, useEffect } from "react";
import { Form, Input, InputNumber, Select, Switch } from "antd";
import { FormikProps, useFormik } from "formik";

import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";

import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { orderService } from "../../../../services/order";
import { Order } from "../../../../interfaces/order";
dayjs.extend(customParseFormat);

type SizeType = Parameters<typeof Form>[0]["size"];

const EditOrder: React.FC = () => {
  const [componentSize, setComponentSize] = useState<SizeType | "default">(
    "default"
  );

  const onFormLayoutChange = ({ size }: { size: SizeType }) => {
    setComponentSize(size);
  };
  // const [imgSrc, setImgSrc] = useState<any | null>("");
  const { id } = useParams<any>();
  const navigate = useNavigate();
  const dispatch: any = useDispatch();
  const [orderDetail, setOrderDetail] = useState<Order>();
  console.log(orderDetail);

  useEffect(() => {
    fetchDetail();
  }, []);
  const fetchDetail = async () => {
    try {
      const result = await orderService.fetchOrderDetailApi(id);
      console.log(result.data);
      setOrderDetail(result.data);
    } catch (errors) {
      console.log("errors", errors);
    }
  };
  const editOrderValidate = Yup.object().shape({
    id: Yup.number()
      .required("Vui lòng nhập ID!")
      .min(1, "Vui lòng nhập ID!")
      .max(10000, "Vui lòng nhập đúng yêu cầu(1-10000)"),
    status: Yup.string()
      .required("Trạng thái không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    sonha: Yup.string()
      .required("Số nhà không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    huyen: Yup.string()
      .required("Huyện không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    xa: Yup.string()
      .required("Xã không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    phone: Yup.string()
      .required("Số điện thoại không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    name: Yup.string()
      .required("Tên không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    tinh: Yup.string()
      .required("Tỉnh không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),

    phiship: Yup.number()
      .required("Vui lòng nhập phí ship!")
      .min(1, "Vui lòng nhập phí ship!")
      .max(1000000, "Vui lòng nhập đúng yêu cầu(1-1000000)"),
    thoigian: Yup.string().required("Thời gian không được để trống!"),
  });

  const formik: FormikProps<Order> = useFormik<Order>({
    enableReinitialize: true,
    initialValues: {
      id: orderDetail?.id ?? 0,
      products: orderDetail?.products ?? [],
      totalamount: orderDetail?.totalamount ?? 0,
      status: orderDetail?.status || "",
      address: {
        sonha: orderDetail?.address.sonha || "",
        huyen: orderDetail?.address.huyen || "",
        xa: orderDetail?.address.xa || "",
        phone: orderDetail?.address.phone || "",
        name: orderDetail?.address.name || "",
        tinh: orderDetail?.address.tinh || "",
      },
      phiship: orderDetail?.phiship ?? 0,
      thoigian: orderDetail?.thoigian || "",
      userid: orderDetail?.userid ?? 0,
    },

    validationSchema: editOrderValidate,
    onSubmit: async (values: any) => {
      await dispatch(updateOrder(values));
    },
  });
  const updateOrder = async (formData: any) => {
    try {
      const result = await orderService.fetchOrderUpdatelApi(formData);
      alert("Cập nhật thành công!");
      navigate("/admin/orders");
    } catch (erors) {
      console.log(erors);
    }
  };

  const handleChangeSelect = (name: string) => {
    return (value: string | any) => {
      formik.setFieldValue(name, value); // Cập nhật giá trị của trường `name` trong Formik
    };
  };
  const handleChangeInputNumber = (name: string) => {
    return (value: number | null) => {
      formik.setFieldValue(name, value);
    };
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
          value={formik.values.id}
          disabled={true}
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
      <Form.Item label="Trạng thái">
        <Select
          style={{ width: 120 }}
          onChange={handleChangeSelect("status")}
          options={[
            { value: "Đang xử lí", label: "Đang xử lí" },
            { value: "Hoàn thành", label: "Hoàn thành" },
          ]}
          value={formik.values.status}
        />
        {formik.errors.status && formik.touched.status && (
          <span className="form-label text-danger">{formik.errors.status}</span>
        )}
      </Form.Item>
      <Form.Item label="Số nhà">
        <Input
          name="sonha"
          onChange={(e) =>
            formik.setFieldValue("address.sonha", e.target.value)
          }
          value={formik.values.address.sonha}
        />
        {formik.errors.address?.sonha && formik.touched.address?.sonha && (
          <span className="form-label text-danger">
            {formik.errors.address?.sonha}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Huyện">
        <Input
          name="huyen"
          onChange={(e) =>
            formik.setFieldValue("address.huyen", e.target.value)
          }
          value={formik.values.address.huyen}
        />
        {formik.errors.address?.huyen && formik.touched.address?.huyen && (
          <span className="form-label text-danger">
            {formik.errors.address?.huyen}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Xã">
        <Input
          name="xa"
          onChange={(e) => formik.setFieldValue("address.xa", e.target.value)}
          value={formik.values.address.xa}
        />
        {formik.errors.address?.xa && formik.touched.address?.xa && (
          <span className="form-label text-danger">
            {formik.errors.address?.xa}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Số điện thoại">
        <Input
          name="phone"
          onChange={(e) =>
            formik.setFieldValue("address.phone", e.target.value)
          }
          value={formik.values.address.phone}
        />
        {formik.errors.address?.phone && formik.touched.address?.phone && (
          <span className="form-label text-danger">
            {formik.errors.address?.phone}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tên">
        <Input
          name="name"
          onChange={(e) => formik.setFieldValue("address.name", e.target.value)}
          value={formik.values.address.name}
        />
        {formik.errors.address?.name && formik.touched.address?.name && (
          <span className="form-label text-danger">
            {formik.errors.address?.name}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tỉnh">
        <Input
          name="tinh"
          onChange={(e) => formik.setFieldValue("address.tinh", e.target.value)}
          value={formik.values.address.tinh}
        />
        {formik.errors.address?.tinh && formik.touched.address?.tinh && (
          <span className="form-label text-danger">
            {formik.errors.address?.tinh}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Phí ship">
        <InputNumber
          onChange={handleChangeInputNumber("phiship")}
          min={1}
          max={1000000}
          value={formik.values.phiship}
        />
        {formik.errors.phiship && formik.touched.phiship && (
          <span
            className="form-label text-danger"
            style={{ marginLeft: "10px" }}
          >
            {formik.errors.phiship}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Thời gian">
        <Input
          name="thoigian"
          onChange={formik.handleChange}
          value={formik.values.thoigian}
        />
        {formik.errors.thoigian && formik.touched.thoigian && (
          <span
            className="form-label text-danger"
            style={{ marginLeft: "10px" }}
          >
            {formik.errors.thoigian}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tác vụ">
        <button type="submit" className="btn btn-primary">
          Cập nhật đơn hàng
        </button>
      </Form.Item>
    </Form>
  );
};

export default EditOrder;
