<template>
  <v-app dark>
    <!-- Верхняя панель -->
    <Header :userProfile="userProfile" />


    <!-- Основное содержимое -->
    <v-container fluid class="learning-modules-container">
      <!-- Заголовок с кнопкой добавления -->
      <v-row justify="space-between" align="center" class="mb-6">
        <h1 class="title">Learning Modules</h1>
        <v-btn color="#242424" @click="showCreateLearningModuleDialog = true" outlined class="learning-module-button">
          + Add Module
        </v-btn>
      </v-row>

      <!-- Линия под заголовком и кнопкой -->
      <v-divider class="divider-line"></v-divider>

      <!-- Список модулей -->
      <v-row v-if="!loading">
        <v-col cols="12" md="6" lg="4" v-for="learningModule in learningModules" :key="learningModule.id">
          <v-card elevation="2" class="learning-module-card" @click="openLearningModule(learningModule)">
            <v-card-title class="learning-module-title">{{ learningModule.name }}</v-card-title>
            <v-divider class="learning-module-divider"></v-divider>
            <v-card-text class="learning-module-description">
              {{ truncateText(learningModule.description, 100) }}
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Индикатор загрузки -->
      <LoadingIndicator v-else />

      <!-- Пагинация -->
      <v-pagination
          v-model="page"
          :length="Math.ceil(totalElements / size)"
          color="#42b883"
          class="mt-4"
          @input="loadLearningModules"
      />

      <!-- Попап для создания модуля -->
      <v-dialog v-model="showCreateLearningModuleDialog" max-width="600">
        <v-card>
          <v-card-title>Create New Learning Module</v-card-title>
          <v-card-text>
            <v-form v-model="valid" ref="form">
              <v-alert
                  v-if="!learningModuleForm.name"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Learning Module name is required
              </v-alert>
              <v-alert
                  v-if="learningModuleForm.name.length > 1000"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Learning Module name should be less than 1000 characters
              </v-alert>
              <v-text-field
                  v-model="learningModuleForm.name"
                  label="Learning Module Name"
                  :rules="[nameRule]"
                  required
                  outlined
              />

              <v-alert
                  v-if="learningModuleForm.description.length > 1000"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Description is too long (max 1000 characters)
              </v-alert>
              <v-textarea
                  v-model="learningModuleForm.description"
                  label="Learning Module Description"
                  :rules="[descriptionRule]"
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
            <v-form v-model="valid" ref="form">
              <v-alert
                  v-if="!learningModuleForm.name"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Learning Module name is required
              </v-alert>
              <v-alert
                  v-if="learningModuleForm.name.length > 1000"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Learning Module name should be less than 1000 characters
              </v-alert>
              <v-text-field
                  v-model="learningModuleForm.name"
                  label="Learning Module Name"
                  :rules="[nameRule]"
                  required
                  outlined
              />

              <v-alert
                  v-if="learningModuleForm.description.length > 1000"
                  type="error"
                  :value="true"
                  class="validation-alert"
              >
                Description is too long (max 1000 characters)
              </v-alert>
              <v-textarea
                  v-model="learningModuleForm.description"
                  label="Learning Module Description"
                  :rules="[descriptionRule]"
                  rows="4"
                  outlined
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text style="color: #e1e1e1;" @click="showEditLearningModuleDialog = false">Cancel</v-btn>
            <v-btn color="#42b883" class="create-learning-module-btn" @click="submitEditLearningModuleForm" :disabled="!valid">Save</v-btn>
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
import LearningModuleViewDialog from '../components/LearningModuleViewDialog.vue';  // Импортируем новый компонент
import LoadingIndicator from '../components/LoadingIndicator.vue';
import {loadUserProfile, logout} from '../services/keycloak';
import {fetchLearningModules, createLearningModule} from '../services/LearningModuleService';
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';

export default {
  name: 'HomeView',
  components: {
    Header,
    LearningModuleViewDialog,
    LoadingIndicator
  },
  setup() {
    const isLoggedIn = ref(false);
    const userProfile = ref({});
    const learningModules = ref([]);
    const totalElements = ref(0);
    const page = ref(1);
    const size = ref(6);
    const loading = ref(false);
    const dialog = ref(false);
    const selectedLearningModule = ref({});
    const showCreateLearningModuleDialog = ref(false);
    const learningModuleForm = ref({name: '', description: ''});
    const additionalFields = ref([]);
    const valid = ref(false);
    const menu = ref(false);
    const router = useRouter();

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
        await loadLearningModules(); // Reload learning modules after creation
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

    return {
      isLoggedIn,
      userProfile,
      learningModules,
      totalElements,
      page,
      size,
      loading,
      dialog,
      selectedLearningModule,
      showCreateLearningModuleDialog,
      learningModuleForm,
      additionalFields,
      valid,
      truncateText,
      logoutUser,
      goToProfile,
      openLearningModule,
      addField,
      loadLearningModules,
      submitCreateLearningModuleForm,
      menu,
    };
  },
};
</script>

<style scoped>
/* Общие стили */
.learning-modules-container {
  background-color: #1a1a1a; /* Фон страницы */
  color: #e1e1e1; /* Цвет текста */
  min-height: 100vh;
  padding: 40px 20px;
}

.title {
  font-size: 2rem;
  font-weight: 600;
  color: #e1e1e1;
  margin-left: 0;
}

/* Линия под Learning Modules и Add Learning Module */
.divider-line {
  border-color: #6a6a6a; /* Заменил цвет на #6a6a6a */
  /* Отступ от контента */
  width: 75%; /* Линия 75% ширины */
  margin: 20px auto 30px;
  height: 1px; /* Тонкая линия */
}

/* Кнопка Add Learning Module */
.learning-module-button {
  font-size: 1rem;
  font-weight: 500;
  color: #e1e1e1;
  background-color: #242424;
  border-radius: 6px;
  border: none; /* Убираем белую обводку */
  font-family: 'Roboto', sans-serif; /* Шрифт для кнопки Add Learning Module такой же, как у карточек */
}

/* Карточка модуля */
.learning-module-card {
  background-color: #242424; /* Цвет карточек */
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
  color: #b0b0b0; /* Светло-серый */
}

/* Линия между названием и описанием модуля */
.learning-module-divider {
  border-color: #6a6a6a; /* Линия между названием и описанием */
  margin: 10px auto;
  height: 1px;
  width: 50%; /* Ширина 50% */
}

/* Всплывающее окно */
.v-dialog .v-card {
  background-color: #1a1a1a; /* Темный фон для попапа */
  color: #e1e1e1; /* Белый текст */
  border-radius: 12px;
}

/* Стиль иконок */
.v-icon {
  color: #979797; /* Цвет иконки в выпадающем меню */
  font-size: 24px;
}

/* Наведение на элементы с текстом */
.learning-module-card:hover .learning-module-title,
.learning-module-card:hover .learning-module-description {
  color: #42b883; /* Зеленый цвет при наведении */
}

/* Пагинация */
.v-pagination {
  justify-content: center;
  color: #42b883; /* Зеленый цвет пагинации */
}

/* Кнопка */
.v-btn {
  background-color: #242424; /* Темный цвет кнопки */
  color: #e1e1e1; /* Цвет текста в кнопке */
}

/* Индикатор загрузки */
.v-progress-circular {
  color: #42b883; /* Зеленый цвет индикатора загрузки */
}

/* Стиль для кнопки Close */
.v-dialog .v-card-actions .v-btn {
  color: #e1e1e1; /* Цвет текста в кнопке Close */
}

/* Стиль для кнопки при наведении */
.v-btn:hover {
  background-color: #242424;
  color: #e1e1e1;
}

/* Стиль для выпадающего меню */
.menu-list {
  background-color: #242424; /* Цвет меню */
  color: #242424; /* Цвет текста в меню */
}

.menu-list .v-list-item {
  color: #242424; /* Цвет текста в списке */
}

.menu-list .v-list-item:hover {
  background-color: #3a3a3a; /* Цвет фона при наведении */
  color: #42b883; /* Подсвечиваем текст зеленым при наведении */
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
