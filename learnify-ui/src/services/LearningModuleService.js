import api from './api';

const LEARNING_MODULES_API = '/api/v1/learning-modules';

export const createLearningModule = async (moduleData) => {
    try {
        const response = await api.post(LEARNING_MODULES_API, moduleData);
        return response.data;
    } catch (error) {
        console.error('Error creating learning module:', error);
        throw error;
    }
};

export const fetchLearningModules = async (page, size) => {
    try {
        const response = await api.get(LEARNING_MODULES_API, {
            params: {
                page: page - 1, // API использует нумерацию с 0
                size,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching learning modules:', error);
        throw error;
    }
};