import axios from "axios";

// 定义 API Key 和 URL
const DASHSCOPE_API_KEY = 'sk-61eb263a11da4d8c8570a6c91ca08ee4'; // 替换为您的实际 API Key
const API_URL = 'https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions';

/**
 * 发送请求到 DashScope API 并获取响应
 * @param userContent 用户输入的内容
 * @returns 返回解析后的 JSON 响应，或者 null 如果没有有效内容
 */
export const askQwen = async (userContent: string) => {
    try {
        // 发起 POST 请求
        const response = await axios.post(API_URL, {
            model: "qwen-plus", // 指定使用的模型
            messages: [
                {
                    role: "system",
                    content: 
                {
                    role: "user",
                    content: userContent // 动态传入用户输入内容
                }
            ]

        }, {
            headers: {
                'Authorization': `Bearer ${DASHSCOPE_API_KEY}`, // 设置认证信息
                'Content-Type': 'application/json' // 设置请求头
            }
        });

        // 检查响应状态和内容
        if (response.status === 200 && response.data.choices && response.data.choices[0].message.content) {
            console.log(JSON.parse(response.data.choices[0].message.content));

            return JSON.parse(response.data.choices[0].message.content); // 返回解析后的 JSON 内容
        } else {
            console.warn("未收到有效内容:", response.data);
            return null; // 如果没有有效内容，返回 null
        }
    } catch (error) {
        // 捕获并处理错误
        if (axios.isAxiosError(error)) {
            console.error("请求失败:", error.response?.data || error.message);
        } else {
            console.error("未知错误:", error);
        }
        return null; // 在错误情况下返回 null
    }
};

// 导出函数以便于在 HTML 或其他模块中使用
export default askQwen;