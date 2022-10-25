import axios from "axios";
const api =import.meta.env.VITE_API_URL
const service = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/exchange`,     
	// headers: { "Access-Control-Allow-Origin": "*" },
	// timeout: 20000,
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
