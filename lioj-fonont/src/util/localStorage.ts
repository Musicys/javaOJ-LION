// 定义 GPTlist 数据结构
interface JudgeCase {
    input: string;
    output: string;
}

interface JudgeConfig {
    memoryLimit: number;
    stackLimit: number;
    timeLimit: number;
}

export interface GPTItem {
    answer: string;
    codeinitial: string;
    content: string;
    judgeCase: JudgeCase[];
    judgeConfig: JudgeConfig;
    tags: string[];
    title: string;
}

// 默认键名
const STORAGE_KEY = "GPTlist";

/**
 * 获取当前存储的 GPTlist 数据
 */
export function getGPTlist(): GPTItem[] {
    const storedData = localStorage.getItem(STORAGE_KEY);
    return storedData ? JSON.parse(storedData) : [];
}

/**
 * 保存 GPTlist 数据到 localStorage
 * @param data - 要保存的 GPTlist 数据
 */
function saveGPTlist(data: GPTItem[]): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

/**
 * 追加一个对象到 GPTlist 数组中
 * @param newItem - 要追加的对象
 */
export function appendToGPTlist(newItem: GPTItem): void {
    const currentList = getGPTlist();
    currentList.push(newItem);
    saveGPTlist(currentList);
}

/**
 * 从 GPTlist 数组中删除指定的对象
 * @param index - 要删除的对象的索引
 */
export function removeFromGPTlist(index: number): void {
    const currentList = getGPTlist();
    if (index >= 0 && index < currentList.length) {
        currentList.splice(index, 1);
        saveGPTlist(currentList);
    } else {
        console.error("Invalid index provided for removal.");
    }
}

/**
 * 重置 GPTlist 数组为空
 */
export function resetGPTlist(): void {
    saveGPTlist([]);
}