package com.yupi.yuoj.utils;

import org.json.JSONArray;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QwenApiClient {

    // 定义 API Key 和 URL





    // 定义 API Key 和 URL

    @Value("${QwenChat.apiKey}")
    private String DASHSCOPE_API_KEY; // 替换为您的实际 API Key
    private  String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";




    /**
     * 发送请求到 DashScope API 并获取响应a
     *
     * @param userContent 用户输入的内容
     * @return 返回解析后的 JSON 响应字符串，或者 null 如果没有有效内容
     */
    public String askQwen(String userContent) {
        HttpURLConnection connection = null;
        System.out.println("key01"+DASHSCOPE_API_KEY);
        try {
            // 构造请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "qwen-plus"); // 指定使用的模型
            String center="我提供题目，请你请生成如下字段,强调,只返回json格式数据,返回的数据必须通过JS：JSON.parse(\"\")，JSON数据以文本内容返回，不能使用md格式，不报错-，\n字段名有：1.title：（题目的标题直接照搬题目即可：示例：移除元素），\n\t\t 2.content：（题目的描述，是一个md文档，有标题，示例，和提示，参考lecode）\n\t\t 3. \"judgeConfig\": {\n                  \"memoryLimit\": 1000,\n                  \"stackLimit\": 1000,\n                  \"timeLimit\": 1000\n                    }(默认就行),\n               \t  4.\"tags\": [\"java\"]（默认就行）,\n\t\t  5.answer：（题目标准答案 要有换行，根据题目描述生成一个类为Main的一个方法，里面有主函数和算法函数入口，主函数不需要任何注释，算法函数不能有任何输出,只需要输出一次，并且answer字段代码引入所有需要的库。示例1.\n\t\n\n\nimport java.util.ArrayList;\nimport java.util.List;\n\npublic class Main {\n\n    public static int removeElement(String[] args) {\n \n        int val = Integer.parseInt(args[args.length - 1]);\n\n        int[] nums = new int[args.length - 1];\n        for (int i = 0; i < args.length - 1; i++) {\n            nums[i] = Integer.parseInt(args[i]);\n        }\n\n    \n        int slow = 0;\n        for (int fast = 0; fast < nums.length; fast++) {\n            if (nums[fast] != val) {\n                nums[slow] = nums[fast];\n                slow++;\n            }\n        }\n\n\n        return slow;\n    }\n\n\n    public static void main(String[] args) {\n        int result = removeElement(args);\n        System.out.println(result);\n    }\n}\n\n\t）\n5.judgeCase：（判题用例，根据题目答案生成(3组)，java Main.java  ,再用 javac Main.clss [输入用例]  程序执行sout出结果，这个为[输出用例],这个{  \"input\": \"[输入用例]\",\n\"output\": [输出用例]}对象为judgeCase数组的一项，示例 [{\"input\":\"3 2 2 3 3\",\"output\":\"2\"},{\"input\":\"0 1 2 2 3 0 4 2 2\",\"output\":\"5\"},{\"input\":\"1 2 3 4 5 6 7 8 9 10 1\",\"output\":\"9\"},{\"input\":\"1 1 1 1 1 1\",\"output\":\"0\"},{\"input\":\"0 0 0 0 0 0\",\"output\":\"0\"},{\"input\":\"1 2 3 4 5 6 7 8 9 10 11\",\"output\":\"10\"},{\"input\":\"5 5 5 5 5 5 5 5 5 5 5\",\"output\":\"0\"},{\"input\":\"10 20 30 40 50 60 70 80 90 100 10\",\"output\":\"9\"},{\"input\":\"1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21\",\"output\":\"20\"},{\"input\":\"-1 -2 -3 -4 -5 -6 -7 -8 -9 -10 -1\",\"output\":\"9\"}]）（所有测试用例必须要提供javac Main.clss [输入用例]  程序执行sout出结果，这个为[输出用例] 通过）(***强调判题用例必须通过)\n5.judgeCase字段要求如下：\n1.存在有效答案：数组中必须有两个数的和等于目标值。\n2.输入数据合理：避免极端或无效的输入（如全负数且目标值为正数）\n3.每种输入只会对应一个答案\n\n\n6.\"codeinitial\"：（根据answer字段生成，  如下格式\n //--用户编写代码处（禁止使用  System.out.println语句）（其他地方请勿修改）----的代码删除，\n\npublic class Main {\n    \n    public static int removeElement(String[] args) {\n        //--用户编写代码处（禁止使用  System.out.println语句）（其他地方请勿修改）----\n        \n        return 0;\n        //-----------------\n    }\n\n    public static void main(String[] args) {\n        int result = removeElement(args);\n        System.out.println(result);\n    }\n}\n\n\t\t\n）\n6..\"codeinitial\"字段要求如下：\n\n\n\n\n所有字段的大小写示例：\n{\n  \"answer\": \"\",\n  \"codeinitial\": \"\",\n  \"content\": \"\",\n  \"judgeCase\": [\n    {\n      \"input\": \"\",\n      \"output\": \"\"\n    }\n  ],\n  \"judgeConfig\": {\n    \"memoryLimit\": 0,\n    \"stackLimit\": 0,\n    \"timeLimit\": 0\n  },\n  \"tags\": [],\n  \"title\": \"\"\n}" ;// 系统角色提示

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", center));
            messages.put(new JSONObject().put("role", "user").put("content", userContent));
            requestBody.put("messages", messages);

            // 可选参数
//            requestBody.put("temperature", 0.7); // 控制生成文本的随机性
//            requestBody.put("max_tokens", 100);  // 限制生成文本的最大长度

            // 创建连接
            URL url = new URL(API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + DASHSCOPE_API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // 写入请求体
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                // 手动读取响应内容
                StringBuilder responseBody = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        responseBody.append(line);
                    }
                }

                // 解析响应内容
                JSONObject jsonResponse = new JSONObject(responseBody.toString());
                if (jsonResponse.has("choices") && jsonResponse.getJSONArray("choices").length() > 0) { // 使用 length() 检查数组是否为空
                    JSONObject choice = jsonResponse.getJSONArray("choices").getJSONObject(0);
                    if (choice.has("message") && choice.getJSONObject("message").has("content")) {
                        return choice.getJSONObject("message").getString("content");
                    }
                }
            } else {
                System.err.println("HTTP POST Request Failed with Error code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null; // 如果没有有效内容，返回 null
    }


}