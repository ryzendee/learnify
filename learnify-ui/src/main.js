import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { initKeycloak } from './services/keycloak';
import { createVuetify } from 'vuetify';
import 'vuetify/styles';

import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'


initKeycloak()
    .then(() => {
        const vuetify = createVuetify({
            components,
            directives
        });

        createApp(App)
            .use(router)
            .use(vuetify)
            .mount('#app');
    })
    .catch(err => {
        console.error('Keycloak initialization failed', err);
    });
