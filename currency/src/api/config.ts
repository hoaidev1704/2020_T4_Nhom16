import axios from "axios";
const api =import.meta.env.VITE_API_URL ? import.meta.env.VITE_API_URL : "http://localhost:7070"
console.log(api)
const service = axios.create({
    baseURL: `${api}/exchange`,     
	headers: { "Access-Control-Allow-Origin": "*" },
	timeout: 20000,
});


service.interceptors.response.use(
	response => {
		return response.data;
	},
	error => {
		if (error.response && error.response.data) {
			return Promise.reject(error.response?.data);
		}
		return Promise.reject(error);
	}
);

export default service;
