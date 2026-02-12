# yang-ai-agent-frontend

基于 Vue 3 + Vite 的前端项目，用于对接 `yang-ai-agent` Spring Boot 后端，提供：

- AI 恋爱大师（SSE 流式对话）
- AI 超级智能体（SSE 流式对话）

## 技术栈

- Vue 3（组合式 API）
- Vite
- vue-router
- axios（用于后续 HTTP 请求封装）
- 浏览器原生 `EventSource` 实现 SSE 流式消息

## 目录结构

- `src/main.js`：应用入口
- `src/App.vue`：全局布局和导航
- `src/router/index.js`：路由配置
- `src/views/HomeView.vue`：首页，用于选择不同应用
- `src/views/LoveChatView.vue`：AI 恋爱大师聊天页
- `src/views/ManusChatView.vue`：AI 超级智能体聊天页
- `src/components/ChatPanel.vue`：通用聊天面板组件（SSE 流式展示）
- `src/api/http.js`：axios 实例（基础配置）
- `src/assets/styles.css`：统一样式和聊天 UI

## 后端依赖

请确保 Java 后端项目 `yang-ai-agent` 已启动，并监听：

- 基础地址：`http://localhost:8123/api`
- SSE 接口：
  - `GET /ai/love_app/chat/sse?message=xxx&chatId=yyy`
  - `GET /ai/manus/chat?message=xxx`

## 启动前端

```bash
cd yang-ai-agent-frontend

# 安装依赖
npm install

# 开发模式
npm run dev
```

启动后访问控制台输出的本地地址（默认 `http://localhost:5173`），即可在浏览器中使用首页与两个聊天应用。

