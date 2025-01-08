import axios from 'axios';
import { getToken } from './keycloak';

const baseURL = import.meta.env.VITE_MODULE_SERVICE_BASE_URL;

const api = axios.create({
    baseURL: baseURL
});

api.interceptors.request.use(
    (config) => {
        const token = getToken();
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;
