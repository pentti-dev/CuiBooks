import React, { Fragment, useEffect, useState } from "react";
import { Table, Input } from "antd";
import type { ColumnsType, TableProps } from "antd/es/table";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { useDispatch } from "react-redux";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import { FormikProps, useFormik } from "formik";
import { productService } from "../../../services/product";
import { listProduct } from "../../../interfaces/product";
const { Search } = Input;

const Product: React.FC = () => {
  const dispatch: any = useDispatch();

  const [productList, setProductList] = useState<listProduct[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    getProductListApi();
  }, []);
  const getProductListApi = async () => {
    try {
      const result = await productService.fetchProductApi();
      setProductList(result.data);
    } catch (err) {
      console.log(err);
    }
  };
  const uploadImg = async (id: any, formFile: any) => {
    try {
      const result = await productService.fetchUploadImg(id, formFile);
      alert("Upload thành công!");
      window.location.reload();
    } catch (errors) {
      console.log(errors);
    }
  };
  const formik: FormikProps<any> = useFormik<any>({
    initialValues: {
      id: "",
      image: {},
    },
    onSubmit: async (values: any) => {
      let formFile = new FormData();
      formFile.append("formFile", values.image, values.image.name);
      console.log(values.image);
      console.log(values.id);
      await dispatch(uploadImg(values.id, formFile));
    },
  });
  const [imgSrc, setImgSrc] = useState<any | null>("");

  const handleChangeFile = (e: any) => {
    if (
      e.target.files[0].type === "image/jpeg" ||
      e.target.files[0].type === "image/jpg" ||
      e.target.files[0].type === "image/gif" ||
      e.target.files[0].type === "image/png"
    ) {
      let reader = new FileReader();
      if (e.target.files[0]) {
        reader.readAsDataURL(e.target.files[0]);
        reader.onload = (e) => {
          console.log(e.target);

          setImgSrc(e.target?.result);
        };
      }
      const id = e.target.getAttribute("data-product-id"); // Lấy mã phòng từ thuộc tính data-room-id
      console.log("Id:", id);

      formik.setFieldValue("Id", id);
      formik.setFieldValue("image", e.target.files[0]);
    }
  };
  const fetchDelete = async (id: any) => {
    try {
      const result = await productService.fetchDeletelApi(id);
      alert("Xoá thành công!");
      dispatch(getProductListApi());
    } catch (errors: any) {
      console.log("errors", errors.response?.data);
    }
  };
  //   const onSearch = async (keyword: any) => {};
  const columns: ColumnsType<listProduct> = [
    {
      title: "ID",
      dataIndex: "id",
      sorter: (a, b) => a.id - b.id,
      sortDirections: ["descend"],
      width: 50,
    },
    {
      title: "Hình ảnh",
      dataIndex: "hinhAnh",
      render: (text, products) => {
        return (
          <Fragment>
            <img src={products.image} width={50} height={50} />
          </Fragment>
        );
      },
      width: 60,
    },
    {
      title: "Tên món",
      dataIndex: "name",
      sorter: (a, b) => {
        let tenMonA = a.name.toLowerCase().trim();
        let tenMonB = b.name.toLowerCase().trim();
        if (tenMonA > tenMonB) {
          return 1;
        }
        return -1;
      },
      width: 120,
    },
    {
      title: "Loại",
      dataIndex: "type",
      width: 50,
    },

    {
      title: "Giá tiền",
      dataIndex: "price",
      sorter: (a, b) => a.price - b.price,
      sortDirections: ["descend"],
      width: 70,
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      width: 70,
    },
    {
      title: "Hành động",
      dataIndex: "id",
      render: (text, product) => {
        return (
          <Fragment>
            <NavLink
              key={1}
              to={`/admin/editProduct/${product.id}`}
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
                if (window.confirm("Bạn có chắc muốn xóa phòng này không?")) {
                  fetchDelete(product.id);
                }
              }}
            >
              <DeleteOutlined />
            </span>
            <input
              type="file"
              onChange={handleChangeFile}
              data-product-id={product.id}
              accept="image/jpeg, image/jpg, image/gif, image/png"
            />
          </Fragment>
        );
      },
      width: 180,
    },
  ];
  const handleSubmit = () => {
    formik.handleSubmit();
  };

  const data = productList;

  const onChange: TableProps<listProduct>["onChange"] = (
    pagination,
    filters,
    sorter,
    extra
  ) => {
    console.log(pagination, filters, sorter, extra);
  };

  return (
    <div>
      <h1 style={{ marginBottom: "20px", fontSize: "2rem" }}>
        Quản lí sản phẩm
      </h1>
      <button
        type="button"
        onClick={() => navigate(`/admin/products/addnew`)}
        className="btn btn-outline-secondary "
        style={{ marginBottom: "20px" }}
      >
        Thêm sản phẩm
      </button>
      <img src="" alt="" />
      <br></br>
      <button
        type="button"
        onClick={handleSubmit}
        className="btn btn-primary"
        style={{ marginBottom: "20px" }}
      >
        Upload hình ảnh
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
      <Table
        columns={columns}
        dataSource={data}
        onChange={onChange}
        scroll={{ x: "100%", y: 1200 }}
        sticky={true}
      />
    </div>
  );
};

export default Product;
