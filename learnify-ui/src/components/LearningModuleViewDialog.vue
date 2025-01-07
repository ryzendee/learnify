<template>
  <v-dialog v-model="localDialog" max-width="600">
    <v-card>
      <v-card-title>{{ selectedLearningModule.name }}</v-card-title>
      <v-card-text>{{ selectedLearningModule.description }}</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="closeDialog" style="color: #e1e1e1;">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'LearningModuleViewDialog',
  props: {
    dialog: {
      type: Boolean,
      required: true
    },
    selectedLearningModule: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      localDialog: this.dialog, // Копируем проп в локальное состояние
    };
  },
  watch: {
    // Следим за изменением пропса dialog и обновляем локальное состояние
    dialog(newValue) {
      this.localDialog = newValue;
    },
    // Если localDialog изменяется, то эмитируем событие update:dialog
    localDialog(newValue) {
      this.$emit('update:dialog', newValue);
    }
  },
  methods: {
    closeDialog() {
      this.localDialog = false; // Закрытие диалога при нажатии кнопки
    }
  }
};
</script>

<style scoped>
.v-dialog .v-card {
  background-color: #1a1a1a; /* Темный фон для попапа */
  color: #e1e1e1; /* Белый текст */
  border-radius: 12px;
}

.v-dialog .v-card-actions .v-btn {
  background-color: #242424;
  color: #e1e1e1;
  border-radius: 6px;
}
</style>
