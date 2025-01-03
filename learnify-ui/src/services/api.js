import axios from 'axios';
import { getToken } from './keycloak';

const api = axios.create({
    baseURL: 'https://your-api-url.com',
});

api.interceptors.request.use(config => {
    const token = getToken();
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default api;
