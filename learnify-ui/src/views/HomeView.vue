<template>
  <v-app>
    <v-container class="home" fluid>
      <v-row justify="center">
        <v-col cols="12" sm="8" md="6">
          <v-card class="pa-5" elevation="2" v-if="isLoggedIn">
            <v-card-title class="headline">Welcome to Learnify</v-card-title>
            <v-card-subtitle>
              You are logged in as {{ userProfile.name }}.
            </v-card-subtitle>
            <v-btn color="primary" @click="goToProfile" class="mt-3">Go to Profile</v-btn>
            <v-btn color="secondary" @click="logout" class="mt-3">Logout</v-btn>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import {loadUserProfile, login, logout} from '../services/keycloak';
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';

export default {
  name: 'HomeView',
  setup() {
    const isLoggedIn = ref(false);
    const userProfile = ref({});
    const router = useRouter();

    onMounted(async () => {
      try {
        userProfile.value = await loadUserProfile();
        isLoggedIn.value = true;
      } catch (error) {
        isLoggedIn.value = false;
      }
    });

    const loginUser = () => {
      login();
    };

    const logoutUser = () => {
      logout();
      isLoggedIn.value = false;
    };

    const goToProfile = () => {
      router.push('/profile');
    };

    return {
      isLoggedIn,
      userProfile,
      login: loginUser,
      logout: logoutUser,
      goToProfile
    };
  }
};
</script>

<style scoped>
.home {
  background-color: #f5f5f5;
  min-height: 100vh;
}
</style>
