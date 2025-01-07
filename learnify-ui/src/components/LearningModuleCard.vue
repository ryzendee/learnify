<template>
  <v-card elevation="2" class="learning-module-card" @click="handleClick">
    <v-card-title class="learning-module-title">{{ module.name }}</v-card-title>
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
  },
};
</script>

<style scoped>
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

.learning-module-card:hover .learning-module-title,
.learning-module-card:hover .learning-module-description {
  color: #42b883; /* Зеленый цвет при наведении */
}
</style>