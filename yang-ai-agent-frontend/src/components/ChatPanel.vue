<template>
  <div class="page-container">
    <div>
      <div class="badge">
        <span class="badge-dot"></span>
        <span>{{ badgeText }}</span>
      </div>
      <h1 class="page-title">{{ title }}</h1>
      <p class="page-subtitle">
        {{ subtitle }}
      </p>
    </div>

    <div class="chat-wrapper">
      <div class="chat-meta">
        <div>
          <span>实时对话 · SSE</span>
        </div>
        <div v-if="mode === 'love'">
          <span>当前聊天室 ID：</span>
          <span class="chat-id">{{ chatId }}</span>
        </div>
      </div>

      <div class="chat-body" ref="chatBodyRef">
        <div v-if="messages.length === 0" class="chat-empty">
          <div class="chat-empty-emoji">💬</div>
          <div>还没有消息，先说点什么吧～</div>
          <div style="font-size: 12px">
            提示：按 Enter 发送，Shift + Enter 换行
          </div>
        </div>
        <template v-else>
          <div
            v-for="msg in messages"
            :key="msg.id"
            class="chat-message-row"
            :class="msg.role"
          >
            <div class="chat-message" :class="msg.role">
              <div class="chat-message-meta">
                <span class="chat-message-role">
                  {{ msg.role === "user" ? "你" : aiRoleLabel }}
                </span>
                <span class="chat-message-time">
                  {{ msg.time }}
                </span>
              </div>
              <div>{{ msg.content }}</div>
            </div>
          </div>
        </template>
      </div>

      <div class="chat-input-bar">
        <div class="chat-textarea-wrapper">
          <textarea
            v-model="input"
            class="chat-textarea"
            :placeholder="placeholder"
            :disabled="loading"
            @keydown.enter.prevent="handleEnter"
          ></textarea>
          <div class="chat-hint">Enter 发送 · Shift+Enter 换行</div>
        </div>
        <button class="chat-send-btn" :disabled="!canSend" @click="onSend">
          <span class="chat-send-btn-icon">➤</span>
          <span>{{ loading ? "发送中..." : "发送" }}</span>
        </button>
      </div>

      <div class="chat-status">
        <span v-if="loading">
          <span class="dot-pulse"></span>
          <span>AI 正在思考中，请稍候...</span>
        </span>
        <span v-else> 已就绪，开始你的提问吧。 </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from "vue";
import { API_BASE_URL } from "@/api/http";

const props = defineProps({
  mode: {
    type: String,
    required: true, // 'love' | 'manus'
  },
  title: {
    type: String,
    required: true,
  },
  subtitle: {
    type: String,
    required: true,
  },
  badgeText: {
    type: String,
    required: true,
  },
  placeholder: {
    type: String,
    default: "请输入你的问题...",
  },
});

const messages = ref([]);
const input = ref("");
const loading = ref(false);
const chatId = ref("");
const chatBodyRef = ref(null);

let currentEventSource = null;

const aiRoleLabel = computed(() =>
  props.mode === "love" ? "AI 恋爱大师" : "AI 超级智能体"
);

const canSend = computed(() => {
  return input.value.trim().length > 0 && !loading.value;
});

const formatTime = (date = new Date()) => {
  return date.toTimeString().slice(0, 8);
};

const genChatId = () => {
  const rand = Math.random().toString(36).substring(2, 8);
  return "chat_" + Date.now().toString(36) + "_" + rand;
};

const scrollToBottom = () => {
  requestAnimationFrame(() => {
    if (chatBodyRef.value) {
      chatBodyRef.value.scrollTop = chatBodyRef.value.scrollHeight;
    }
  });
};

const appendMessage = (msg) => {
  messages.value.push(msg);
  scrollToBottom();
};

const updateLastAiMessage = (delta) => {
  const list = messages.value;
  for (let i = list.length - 1; i >= 0; i--) {
    if (list[i].role === "ai") {
      list[i] = {
        ...list[i],
        content: list[i].content + delta,
      };
      break;
    }
  }
  scrollToBottom();
};

const closeCurrentStream = () => {
  if (currentEventSource) {
    currentEventSource.close();
    currentEventSource = null;
  }
};

const buildUrl = (content) => {
  const searchParams = new URLSearchParams();
  searchParams.append("message", content);
  if (props.mode === "love") {
    searchParams.append("chatId", chatId.value);
  }

  let path = "";
  if (props.mode === "love") {
    path = "/ai/love_app/chat/sse";
  } else {
    path = "/ai/manus/chat";
  }

  return `${API_BASE_URL}${path}?${searchParams.toString()}`;
};

const onSend = () => {
  const content = input.value.trim();
  if (!content || loading.value) return;

  loading.value = true;
  closeCurrentStream();

  const userMsg = {
    id: "user_" + Date.now(),
    role: "user",
    content,
    time: formatTime(),
  };
  appendMessage(userMsg);

  const aiMsg = {
    id: "ai_" + Date.now(),
    role: "ai",
    content: "",
    time: formatTime(),
  };
  appendMessage(aiMsg);

  input.value = "";

  const url = buildUrl(content);

  try {
    const es = new EventSource(url);
    currentEventSource = es;

    es.onmessage = (event) => {
      if (!event.data) return;
      updateLastAiMessage(event.data);
    };

    es.onerror = () => {
      console.error("SSE 连接出错");
      loading.value = false;
      closeCurrentStream();
    };

    es.onopen = () => {
      // 连接建立，仅用于调试
      // console.log("SSE 连接已打开");
    };
  } catch (e) {
    console.error("创建 SSE 连接失败:", e);
    loading.value = false;
    closeCurrentStream();
  }
};

const handleEnter = (e) => {
  if (e.shiftKey) {
    // 允许换行
    input.value += "\n";
    return;
  }
  onSend();
};

onMounted(() => {
  if (props.mode === "love") {
    chatId.value = genChatId();
  }
});

onBeforeUnmount(() => {
  closeCurrentStream();
});
</script>

