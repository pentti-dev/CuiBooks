import axios, { AxiosResponse } from "axios";
import { ProductResponse } from "../interfaces/product";

// Define the service functions as async functions
export const productService = {
  fetchProductApi: async (): Promise<AxiosResponse<ProductResponse>> => {
    const response = await axios.get("http://localhost:5003/api/product");
    return response;
  },

  fetchProductDetailApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.get(`http://localhost:5003/api/product/${id}`);
    return response;
  },
  fetchDeletelApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.delete(
      `http://localhost:5003/api/product/delete/${id}`
    );
    return response;
  },
  fetchAddProductlApi: async (formData: any): Promise<AxiosResponse<any>> => {
    const response = await axios.post(
      `http://localhost:5003/api/product/addProduct`,
      formData
    );
    return response;
  },
  fetchUpdatelApi: async (formData: any): Promise<AxiosResponse<any>> => {
    const response = await axios.put(
      `http://localhost:5003/api/product/updateProduct`,
      formData
    );
    return response;
  },
  fetchUploadImg: async (id:any, formFile: any): Promise<AxiosResponse<any>> => {
    const response = await axios.post(
      `http://localhost:5003/api/product/uploadImage/${id}`,
      formFile
    );
    return response;
  },
  fetchOrderQuantityApi: async (): Promise<AxiosResponse<ProductResponse>> => {
    const response = await axios.get(
      "http://localhost:5003/api/Order/quantity"
    );
    return response;
  },
  fetchProductQuantityApi: async (): Promise<
    AxiosResponse<ProductResponse>
  > => {
    const response = await axios.get(
      "http://localhost:5003/api/product/quantity"
    );
    return response;
  },
};
