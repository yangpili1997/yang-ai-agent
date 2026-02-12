<template>
  <div class="app-root">
    <header class="app-header">
      <div class="brand" @click="$router.push('/')">
        <span class="brand-logo">YA</span>
        <span class="brand-text">Yang AI Agent</span>
      </div>
      <nav class="nav-links">
        <RouterLink to="/" class="nav-link">首页</RouterLink>
        <RouterLink to="/love" class="nav-link">AI 恋爱大师</RouterLink>
        <RouterLink to="/manus" class="nav-link">AI 超级智能体</RouterLink>
      </nav>
    </header>
    <main class="app-main">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

