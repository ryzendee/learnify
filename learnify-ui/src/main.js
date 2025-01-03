import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { initKeycloak } from './services/keycloak';
import { createVuetify } from 'vuetify';
import 'vuetify/styles';
import { VContainer, VRow, VCol, VCard, VCardTitle, VCardSubtitle, VBtn } from 'vuetify/components';


initKeycloak()
    .then(() => {
        const vuetify = createVuetify({
            components: {
                VContainer,
                VRow,
                VCol,
                VCard,
                VCardTitle,
                VCardSubtitle,
                VBtn,
            }
        });

        createApp(App)
            .use(router)
            .use(vuetify)
            .mount('#app');
    })
    .catch(err => {
        console.error('Keycloak initialization failed', err);
    });
