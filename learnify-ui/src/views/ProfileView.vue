<template>
  <v-container class="dashboard">
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-5" elevation="2">
          <v-card-title class="headline">Your Profile</v-card-title>
          <v-card-subtitle>
            Welcome, {{ userProfile.firstName }} {{ userProfile.lastName }}!
          </v-card-subtitle>
          <v-card-subtitle>
            Username: {{userProfile.username}}
          </v-card-subtitle>
          <v-card-subtitle>
            Email: {{ userProfile.email }}
          </v-card-subtitle>
          <v-btn color="primary" @click="logout" class="mt-3">Logout</v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { logout, loadUserProfile } from '../services/keycloak';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'DashboardView',
  setup() {
    const userProfile = ref({});
    const router = useRouter();

    loadUserProfile()
        .then(profile => {
          userProfile.value = profile;
        })
        .catch(error => {
          console.error('Error loading user profile', error);
        });

    const logoutUser = () => {
      logout();
    };

    const goToProfile = () => {
      router.push('/profile');
    };

    return {
      userProfile,
      logout: logoutUser,
      goToProfile
    };
  }
};
</script>

<style scoped>
.dashboard {
  background-color: #e0f7fa;
  min-height: 100vh;
}
</style>
