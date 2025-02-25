import * as Yup from "yup";
import React, { useState, useEffect } from "react";
import { Form, Input, InputNumber, Select, Switch } from "antd";
import { FormikProps, useFormik } from "formik";

import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { productService } from "../../../../services/product";
import { listProduct } from "../../../../interfaces/product";

type SizeType = Parameters<typeof Form>[0]["size"];

const Edit: React.FC = () => {
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
  const [productDetail, setproductDetail] = useState<listProduct>();
  console.log(productDetail);

  useEffect(() => {
    fetchDetail();
  }, []);
  const fetchDetail = async () => {
    try {
      const result = await productService.fetchProductDetailApi(id);
      console.log(result.data);
      setproductDetail(result.data);
    } catch (errors) {
      console.log("errors", errors);
    }
  };
  const editRoomValidate = Yup.object().shape({
    id: Yup.number()
      .required("Vui lòng nhập ID!")
      .min(1, "Vui lòng nhập ID!")
      .max(10000, "Vui lòng nhập đúng yêu cầu(1-10000)"),
    name: Yup.string()
      .required("Tên sản phẩm không được để trống!")
      .matches(
        /^[ aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẽéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9]+$/,
        "Vui lòng nhập đúng định dạng!"
      ),
    price: Yup.number()
      .required("Vui lòng nhập giá tiền!")
      .min(1, "Vui lòng nhập giá tiền!")
      .max(1000000, "Vui lòng nhập đúng yêu cầu(1-1000000)"),
    type: Yup.string().required("Loại san phẩm không được để trống!"),
    quantity: Yup.number()
      .required("Vui lòng nhập số lượng!")
      .min(1, "Số lượng phải lớn hơn 0!")
      .max(1000, "Vui lòng nhập đúng yêu cầu(1-1000)"),
  });

  const formik: FormikProps<listProduct> = useFormik<listProduct>({
    enableReinitialize: true,
    initialValues: {
      id: productDetail?.id ?? 0,
      name: productDetail?.name || "",
      price: productDetail?.price ?? 0,
      image: productDetail?.image || "",
      type: productDetail?.type || "",
      comments: productDetail?.comments || [],
      quantity: productDetail?.quantity || 0,
    },
    validationSchema: editRoomValidate,
    onSubmit: async (values: any) => {
      console.log(values);

      await dispatch(updateProduct(values));
    },
  });
  const updateProduct = async (formData: any) => {
    try {
      const result = await productService.fetchUpdatelApi(formData);
      console.log(result.data.content);
      alert("Cập nhật thành công!");
      navigate("/admin/products");
    } catch (erors) {
      console.log(erors);
    }
  };

  const handleChangeInputNumber = (name: string) => {
    return (value: number | null) => {
      formik.setFieldValue(name, value);
    };
  };
  const handleChangeSelect = (name: string) => {
    return (value: string | any) => {
      formik.setFieldValue(name, value); // Cập nhật giá trị của trường `name` trong Formik
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
      <h3 style={{ marginBottom: "20px" }}>Cập nhật phòng</h3>

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
      <Form.Item label="Tên sản phẩm">
        <Input
          name="name"
          onChange={formik.handleChange}
          value={formik.values.name}
        />
        {formik.errors.name && formik.touched.name && (
          <span className="form-label text-danger" style={{ display: "block" }}>
            {formik.errors.name}
          </span>
        )}
      </Form.Item>

      <Form.Item label="Giá tiền">
        <InputNumber
          onChange={handleChangeInputNumber("price")}
          min={1}
          max={1000000}
          value={formik.values.price}
        />
        {formik.errors.price && formik.touched.price && (
          <span
            className="form-label text-danger"
            style={{ marginLeft: "10px" }}
          >
            {formik.errors.price}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Loại sản phẩm">
        <Select
          defaultValue="Chọn loại sản phẩm"
          style={{ width: 120 }}
          onChange={handleChangeSelect("type")}
          options={[
            { value: "Combo", label: "Combo" },
            { value: "Cơm", label: "Cơm" },
            { value: "Mì", label: "Mì" },
            { value: "Miến", label: "Miến" },
            { value: "Phá lấu", label: "Phá lấu" },
            { value: "Nước giải khát", label: "Nước giải khát" },
            { value: "Các món khác", label: "Các món khác" },
          ]}
          value={formik.values.type}
        />
        {formik.errors.type && formik.touched.type && (
          <span className="form-label text-danger">{formik.errors.type}</span>
        )}
      </Form.Item>
      <Form.Item label="Số lượng">
        <InputNumber
          onChange={handleChangeInputNumber("quantity")}
          min={1}
          max={1000}
          value={formik.values.quantity}
        />
        {formik.errors.quantity && formik.touched.quantity && (
          <span
            className="form-label text-danger"
            style={{ marginLeft: "10px" }}
          >
            {formik.errors.quantity}
          </span>
        )}
      </Form.Item>
      <Form.Item label="Tác vụ">
        <button type="submit" className="btn btn-primary">
          Cập nhật sản phẩm
        </button>
      </Form.Item>
    </Form>
  );
};

export default Edit;
