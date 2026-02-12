<template>
  <div class="page-container">
    <div>
      <div class="badge">
        <span class="badge-dot"></span>
        <span>本地运行 · http://localhost:8123/api</span>
      </div>
      <h1 class="page-title">选择你的 AI 应用</h1>
      <p class="page-subtitle">
        首页用于在不同智能体之间切换，进入后即可开始对话。
      </p>
    </div>

    <div class="card-grid">
      <div class="card" @click="$router.push('/love')">
        <div>
          <div class="card-title">AI 恋爱大师</div>
          <div class="card-tagline">
            情感咨询、恋爱话术、聊天润色，一站式智能恋爱助手。
          </div>
        </div>
        <div class="card-footer">
          <span class="card-pill">基于 /ai/love_app/chat/sse</span>
          <span>支持多轮会话与实时流式回复</span>
        </div>
      </div>

      <div class="card" @click="$router.push('/manus')">
        <div>
          <div class="card-title">AI 超级智能体</div>
          <div class="card-tagline">
            通用智能问答、长文理解、多任务处理的超级 Agent。
          </div>
        </div>
        <div class="card-footer">
          <span class="card-pill">基于 /ai/manus/chat</span>
          <span>更强大的综合智能与工具调用能力</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

