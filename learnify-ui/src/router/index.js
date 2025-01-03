import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import { isAuthenticated, updateToken } from '../services/keycloak';
import ProfileView from '../views/ProfileView.vue';

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView,
        beforeEnter: (to, from, next) => {
            if (!isAuthenticated()) {
                next('/');
            } else {
                updateToken()
                    .then(() => next())
                    .catch(() => next('/'));
            }
        }
    },
    {
        path: '/profile',
        name: 'profile',
        component: ProfileView,
        beforeEnter: (to, from, next) => {
            if (!isAuthenticated()) {
                next('/');
            } else {
                next();
            }
        }
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;
