<template>
  <ChatPanel
    mode="manus"
    title="AI 超级智能体"
    subtitle="面向通用任务的高级 Agent，可以进行复杂推理、长文理解与多步骤规划。"
    badge-text="接口：GET /ai/manus/chat · SseEmitter 流式返回"
    placeholder="任何你想问的问题，例如：帮我梳理一下这个项目的技术方案？"
  />
</template>

<script setup>
import ChatPanel from "@/components/ChatPanel.vue";
</script>

