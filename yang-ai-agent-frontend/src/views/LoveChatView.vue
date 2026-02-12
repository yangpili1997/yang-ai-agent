<template>
  <ChatPanel
    mode="love"
    title="AI 恋爱大师"
    subtitle="专注情感场景的智能助手，为你提供恋爱建议、聊天润色和约会策略。"
    badge-text="接口：GET /ai/love_app/chat/sse · SSE 流式返回"
    placeholder="例如：帮我想一段开场白，和喜欢的人开始聊天？"
  />
</template>

<script setup>
import ChatPanel from "@/components/ChatPanel.vue";
</script>

