<template>
  <v-card elevation="2" class="learning-module-card" @click="handleClick">
    <v-card-title class="learning-module-title">
      <span>{{ module.name }}</span>
      <div class="action-icons">
        <v-btn icon class="edit-icon" @click.stop="handleEditClick">
          <v-icon small>mdi-pencil</v-icon>
        </v-btn>
        <v-btn icon class="delete-icon" @click.stop="handleDeleteClick">
          <v-icon small>mdi-close</v-icon>
        </v-btn>
      </div>
    </v-card-title>
    <v-divider class="learning-module-divider"></v-divider>
    <v-card-text class="learning-module-description">
      {{ truncateText(module.description, 100) }}
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: 'LearningModuleCard',
  props: {
    module: {
      type: Object,
      required: true,
    },
  },
  methods: {
    truncateText(text, length) {
      return text.length > length ? text.substring(0, length) + '...' : text;
    },
    handleClick() {
      this.$emit('open-module', this.module);
    },
    handleEditClick(event) {
      event.stopPropagation();
      this.$emit('edit-module', this.module);
    },
    handleDeleteClick(event) {
      event.stopPropagation();
      this.$emit('delete-module', this.module);
    }
  },
};
</script>

<style scoped>
.learning-module-card {
  background-color: #242424;
  border-radius: 8px;
  height: 200px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  padding: 20px;
  font-size: 1.1rem;
}

.learning-module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.2);
}

.learning-module-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #e1e1e1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.action-icons {
  display: flex;
  gap: 10px;
}

.learning-module-description {
  font-size: 1rem;
  color: #b0b0b0;
  text-align: center;
}

.learning-module-divider {
  border-color: #6a6a6a;
  margin: 10px auto;
  height: 1px;
  width: 50%;
}

.learning-module-card:hover .learning-module-title,
.learning-module-card:hover .learning-module-description {
  color: #42b883;
}

.edit-icon, .delete-icon {
  color: #e1e1e1;
  background-color: transparent;
  padding: 0;
}

.edit-icon:hover, .delete-icon:hover {
  color: #42b883;
}
</style>
