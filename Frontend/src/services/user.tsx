import axios, { AxiosResponse } from "axios";
import { listUser } from "../interfaces/user";

// Define the service functions as async functions
export const userService = {
  fetchUserListApi: async (): Promise<AxiosResponse<listUser[]>> => {
    const response = await axios.get(`http://localhost:5003/api/user`);
    return response;
  },
  fetchUserDetailApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.get(`http://localhost:5003/api/user/${id}`);
    return response;
  },
  fetchUserDeleteApi: async (id: any): Promise<AxiosResponse<any>> => {
    const response = await axios.delete(`http://localhost:5003/api/user/${id}`);
    return response;
  },
  fetchUserAddApi: async (formData: any): Promise<AxiosResponse<any>> => {
    const response = await axios.post(
      `http://localhost:5003/api/user`,
      formData
    );
    return response;
  },
  fetchUserUpdateApi: async (formData: any): Promise<AxiosResponse<any>> => {
    const response = await axios.put(
      `http://localhost:5003/api/user`,
      formData
    );
    return response;
  },
};
