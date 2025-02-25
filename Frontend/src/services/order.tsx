import axios, { AxiosResponse } from "axios";
import { Order } from "../interfaces/order";

// Define the service functions as async functions
export const orderService = {
  fetchOrderListApi: async (): Promise<AxiosResponse<Order[]>> => {
    const response = await axios.get("http://localhost:5003/api/Order");
    return response;
  },
  fetchDeleteListApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.delete(
      `http://localhost:5003/api/Order/${id}`
    );
    return response;
  },
  fetchOrderDetailApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.get(
      `http://localhost:5003/api/Order/orderDetail/${id}`
    );
    return response;
  },
  fetchOrderUpdatelApi: async (formData: any): Promise<AxiosResponse<any>> => {
    const response = await axios.post(
      `http://localhost:5003/api/Order`,formData
    );
    return response;
  },
};
