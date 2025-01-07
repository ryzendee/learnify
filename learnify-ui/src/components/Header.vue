<template>
  <v-app-bar app color="#1a1a1a" dark>
    <v-btn icon>
      <v-icon color="#979797">mdi-lightbulb-on</v-icon>
    </v-btn>
    <v-toolbar-title class="ml-0" style="color: #e1e1e1; margin-left: 0;">
      <v-icon class="mr-2" color="#42b883">mdi-school</v-icon>Learning Modules
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-menu offset-y :close-on-content-click="false" v-model="menu">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props" class="menu-btn" elevation="2">
          <v-icon class="mr-2" color="#979797">mdi-account</v-icon>
          {{ userProfile.name }}
        </v-btn>
      </template>
      <v-list class="menu-list">
        <v-list-item @click="goToProfile">
          <v-list-item-title>Profile</v-list-item-title>
        </v-list-item>
        <v-list-item @click="logoutUser">
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { logout } from '../services/keycloak';
import { useRouter } from 'vue-router';

export default {
  name: 'Header',
  props: {
    userProfile: Object,
  },
  data() {
    return {
      menu: false,
    };
  },
  methods: {
    logoutUser() {
      logout();
    },
    goToProfile() {
      this.$router.push('/profile');
    },
  },
};
</script>

<style scoped>
/* Стили для хедера */
.menu-btn {
  background-color: #242424;
  color: #e1e1e1;
  border-radius: 6px;
}

.menu-list {
  background-color: #242424;
  color: #242424;
}

.menu-list .v-list-item:hover {
  background-color: #3a3a3a;
  color: #42b883;
}
</style>
