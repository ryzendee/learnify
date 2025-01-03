import Keycloak from 'keycloak-js';

let keycloak = null;

const initKeycloak = () => {
    keycloak = new Keycloak({
        url: import.meta.env.VITE_KEYCLOAK_URL,
        realm: import.meta.env.VITE_KEYCLOAK_REALM,
        clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID
    });

    return new Promise((resolve, reject) => {
        keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })
            .then(authenticated => {
                if (authenticated) {
                    console.log('Keycloak authenticated');
                    resolve(keycloak);
                } else {
                    reject('Keycloak authentication failed');
                }
            })
            .catch(error => reject(error));
    });
};

const loadUserProfile = () => {
    return new Promise((resolve, reject) => {
        if (keycloak) {
            keycloak.loadUserProfile()
                .then(profile => {
                    resolve(profile);
                })
                .catch(error => reject(error));
        }
    });
};

const updateToken = () => {
    return new Promise((resolve, reject) => {
        keycloak.updateToken(30)
            .then(refreshed => {
                if (refreshed) {
                    console.log('Token has been refreshed');
                }
                resolve();
            })
            .catch(() => reject('Failed to refresh token'));
    });
};

const getKeycloak = () => keycloak;

const login = () => {
    if (keycloak) {
        keycloak.login();
    }
};

const logout = () => {
    if (keycloak) {
        keycloak.logout();
    }
};

const isAuthenticated = () => keycloak?.authenticated;

const getToken = () => keycloak?.token;

export { initKeycloak, getKeycloak, login, logout, isAuthenticated, getToken, updateToken, loadUserProfile };
