import axios from "axios";

// 后端接口前缀
export const API_BASE_URL = "http://localhost:8123/api";

const http = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000
});

http.interceptors.response.use(
  (resp) => resp,
  (error) => {
    console.error("HTTP 请求失败:", error);
    return Promise.reject(error);
  }
);

export default http;

