<template>
  <v-app dark>
    <Header :userProfile="userProfile"/>

    <v-container fluid class="learning-modules-container">
      <!-- Заголовок с кнопкой добавления -->
      <v-row justify="space-between" align="center" class="mb-6">
        <h1 class="title">Learning Modules</h1>
        <v-btn color="#242424" @click="openCreateLearningModuleDialog" outlined class="learning-module-button">
          + Add Module
        </v-btn>
      </v-row>

      <v-divider class="divider-line"></v-divider>

      <v-row v-if="!loading">
        <v-col cols="12" md="6" lg="4" v-for="learningModule in learningModules" :key="learningModule.id">
          <LearningModuleCard
              :module="learningModule"
              @open-module="openLearningModule"
              @edit-module="openEditLearningModuleDialog"
              @delete-module="deleteLearningModule"
          />
        </v-col>
      </v-row>

      <LoadingIndicator v-else/>

      <v-pagination
          v-model="page"
          :length="Math.ceil(totalElements / size)"
          color="#42b883"
          class="mt-4"
          @update:model-value="loadLearningModules"
      />

      <v-dialog v-model="showCreateLearningModuleDialog" max-width="600">
        <v-card>
          <v-card-title>Create New Learning Module</v-card-title>
          <v-card-text>
            <v-form v-model="valid" ref="form">
              <v-text-field
                  v-model="learningModuleForm.name"
                  label="Learning Module Name"
                  :rules="nameRule"
                  required
                  outlined
              />
              <v-textarea
                  v-model="learningModuleForm.description"
                  label="Learning Module Description"
                  :rules="descriptionRule"
                  rows="4"
                  outlined
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text style="color: #e1e1e1;" @click="showCreateLearningModuleDialog = false">Cancel</v-btn>
            <v-btn color="#42b883" class="create-learning-module-btn" @click="submitCreateLearningModuleForm"
                   :disabled="!valid">Create
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="showEditLearningModuleDialog" max-width="600">
        <v-card>
          <v-card-title>Edit Learning Module</v-card-title>
          <v-card-text>
            <v-form v-model="valid" ref="editForm">
              <v-text-field
                  v-model="editLearningModuleForm.name"
                  label="Learning Module Name"
                  :rules="nameRule"
                  required
                  outlined
              />
              <v-textarea
                  v-model="editLearningModuleForm.description"
                  label="Learning Module Description"
                  :rules="descriptionRule"
                  rows="4"
                  outlined
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text style="color: #e1e1e1;" @click="showEditLearningModuleDialog = false">Cancel</v-btn>
            <v-btn color="#42b883" class="create-learning-module-btn" @click="submitEditLearningModuleForm"
                   :disabled="!valid">Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>

    <LearningModuleViewDialog
        v-model:dialog="dialog"
        :selectedLearningModule="selectedLearningModule"
    />

  </v-app>
</template>

<script>
import Header from '../components/Header.vue';
import LearningModuleViewDialog from '../components/LearningModuleViewDialog.vue';
import LearningModuleCard from '../components/LearningModuleCard.vue';
import LoadingIndicator from '../components/LoadingIndicator.vue';
import {loadUserProfile, logout} from '../services/keycloak';
import {
  fetchLearningModules,
  createLearningModule,
  updateLearningModuleById,
  deleteLearningModuleById
} from '../services/LearningModuleService';
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';

export default {
  name: 'HomeView',
  components: {
    Header,
    LearningModuleViewDialog,
    LearningModuleCard,
    LoadingIndicator
  },
  setup() {
    const isLoggedIn = ref(false);
    const userProfile = ref({});
    const learningModules = ref([]);
    const totalElements = ref(0);
    const page = ref(1);
    const size = ref(9);
    const loading = ref(false);
    const dialog = ref(false);
    const selectedLearningModule = ref({});
    const showCreateLearningModuleDialog = ref(false);
    const showEditLearningModuleDialog = ref(false);
    const learningModuleForm = ref({name: '', description: ''});
    const editLearningModuleForm = ref({name: '', description: ''});
    const additionalFields = ref([]);
    const valid = ref(false);
    const menu = ref(false);
    const router = useRouter();

    const nameRule = [
      (v) => !!v || 'Name is required',
      (v) => v.length <= 1000 || 'Name should be less than 1000 characters',
    ];

    const descriptionRule = [
      (v) => v.length <= 1000 || 'Description should be less than 1000 characters',
    ];

    const openCreateLearningModuleDialog = () => {
      learningModuleForm.value = {name: '', description: ''};
      showCreateLearningModuleDialog.value = true;
    };

    const loadLearningModules = async () => {
      loading.value = true;
      try {
        console.log('Loading data for page:', page.value);
        const data = await fetchLearningModules(page.value, size.value);
        console.log('Fetched Data:', data);
        learningModules.value = data.content;
        totalElements.value = data.page.totalElements;
        console.log('Total Elements:', totalElements.value);
      } catch (error) {
        console.error('Failed to load learning modules:', error);
      } finally {
        loading.value = false;
      }
    };

    const openLearningModule = (learningModule) => {
      selectedLearningModule.value = learningModule;
      dialog.value = true;
    };

    const openEditLearningModuleDialog = (learningModule) => {
      editLearningModuleForm.value = {...learningModule};
      showEditLearningModuleDialog.value = true;
    };

    const submitEditLearningModuleForm = async () => {
      const updatedLearningModule = {
        ...editLearningModuleForm.value,
      };
      try {
        await updateLearningModuleById(updatedLearningModule.id, updatedLearningModule);
        showEditLearningModuleDialog.value = false;
        await loadLearningModules();
      } catch (error) {
        console.error('Failed to update learning module:', error);
      }
    };

    const truncateText = (text, length) => {
      return text.length > length ? text.substring(0, length) + '...' : text;
    };

    const logoutUser = () => {
      logout();
      isLoggedIn.value = false;
    };

    const goToProfile = () => {
      router.push('/profile');
    };

    const addField = () => {
      additionalFields.value.push('');
    };

    const submitCreateLearningModuleForm = async () => {
      const newLearningModule = {
        ...learningModuleForm.value,
        additionalFields: additionalFields.value,
      };
      try {
        await createLearningModule(newLearningModule);
        showCreateLearningModuleDialog.value = false;
        await loadLearningModules();
      } catch (error) {
        console.error('Failed to create learning module:', error);
      }
    };

    onMounted(async () => {
      try {
        userProfile.value = await loadUserProfile();
        isLoggedIn.value = true;
        await loadLearningModules();
      } catch (error) {
        isLoggedIn.value = false;
      }
    });

    const deleteLearningModule = async (learningModule) => {
      try {
        await deleteLearningModuleById(learningModule.id);
        await loadLearningModules();
      } catch (error) {
        console.error('Failed to delete learning module:', error);
      }
    };

    return {
      isLoggedIn,
      userProfile,
      learningModules,
      totalElements,
      page,
      size,
      loading,
      dialog,
      nameRule,
      descriptionRule,
      selectedLearningModule,
      showCreateLearningModuleDialog,
      editLearningModuleForm,
      showEditLearningModuleDialog,
      openEditLearningModuleDialog,
      submitEditLearningModuleForm,
      learningModuleForm,
      additionalFields,
      valid,
      truncateText,
      logoutUser,
      goToProfile,
      openLearningModule,
      addField,
      loadLearningModules,
      openCreateLearningModuleDialog,
      submitCreateLearningModuleForm,
      menu,
      deleteLearningModule
    };
  },
};
</script>

<style scoped>
.learning-modules-container {
  background-color: #1a1a1a;
  color: #e1e1e1;
  min-height: 100vh;
  padding: 40px 20px;
}

.title {
  font-size: 2rem;
  font-weight: 600;
  color: #e1e1e1;
  margin-left: 0;
}

.divider-line {
  border-color: #6a6a6a;
  width: 75%;
  margin: 20px auto 30px;
  height: 1px;
}

.learning-module-button {
  font-size: 1rem;
  font-weight: 500;
  color: #e1e1e1;
  background-color: #242424;
  border-radius: 6px;
  border: none;
  font-family: 'Roboto', sans-serif;
}

.learning-module-card {
  background-color: #242424;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.learning-module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.2);
}

.learning-module-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #e1e1e1;
}

.learning-module-description {
  font-size: 0.9rem;
  color: #b0b0b0;
}

.learning-module-divider {
  border-color: #6a6a6a;
  margin: 10px auto;
  height: 1px;
  width: 50%;
}

.v-dialog .v-card {
  background-color: #1a1a1a;
  color: #e1e1e1;
  border-radius: 12px;
}

.v-icon {
  color: #979797;
  font-size: 24px;
}

.learning-module-card:hover .learning-module-title,
.learning-module-card:hover .learning-module-description {
  color: #42b883;
}

.v-pagination {
  justify-content: center;
  color: #42b883;
}

.v-btn {
  background-color: #242424;
  color: #e1e1e1;
}

.v-progress-circular {
  color: #42b883;
}

.v-dialog .v-card-actions .v-btn {
  color: #e1e1e1;
}

.v-btn:hover {
  background-color: #242424;
  color: #e1e1e1;
}

.menu-list {
  background-color: #242424;
  color: #242424;
}

.menu-list .v-list-item {
  color: #242424;
}

.menu-list .v-list-item:hover {
  background-color: #3a3a3a;
  color: #42b883;
}

.menu-btn {
  background-color: #242424;
  color: #e1e1e1;
  border-radius: 6px;
}

.validation-alert {
  color: #ff7a7a;
  font-size: 0.85rem;
  padding: 4px 0;
  margin-bottom: 8px;
  font-family: 'Roboto', sans-serif;
}

.create-learning-module-btn {
  font-size: 1rem;
  font-weight: 500;
  color: #e1e1e1;
  background-color: #242424;
  border-radius: 6px;
  border: none;
  font-family: 'Roboto', sans-serif;
}
</style>
