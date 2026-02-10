package com.yang.yangaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpAiInvoke {

    public static void main(String[] args) {
        // 1. 定义 API 地址
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
        
        // 2. 准备 API Key (确保环境变量中已配置，或直接填入字符串)
        String apiKey = TestApiKey.Api_Key;

        // 3. 构建请求体 (使用 Map 结构对应 JSON 格式)
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "qwen-plus");

        // 构建 input 节点
        Map<String, Object> input = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        
        messages.add(createMessage("system", "You are a helpful assistant."));
        messages.add(createMessage("user", "帮我定制深圳周末幽会地方？"));
        
        input.put("messages", messages);
        bodyMap.put("input", input);

        // 构建 parameters 节点
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("result_format", "message");
        bodyMap.put("parameters", parameters);

        // 4. 发送请求
        HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(bodyMap)) // 将 Map 转为 JSON 字符串
                .execute();

        // 5. 输出结果
        if (response.isOk()) {
            System.out.println("请求成功：");
            System.out.println(response.body());
        } else {
            System.err.println("请求失败，状态码：" + response.getStatus());
            System.err.println("错误信息：" + response.body());
        }
    }

    private static Map<String, String> createMessage(String role, String content) {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", role);
        msg.put("content", content);
        return msg;
    }
}