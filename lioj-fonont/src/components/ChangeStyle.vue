<template>
    <div class="slip-outer">
        <div class="slip-block" ref="block"></div>
        <div class="inner">
            <div class="day" ref="day" @click="changeToDay">
                <slot name="day">日</slot>
            </div>
            <div class="night" ref="night" @click="changeToNight">
                <slot name="night">月</slot>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

// 定义 emit
const emit = defineEmits<{
    (e: 'day'): void
    (e: 'night'): void
}>()

// 状态管理
const situation = ref<boolean>(true)  // true 表示白天模式，false 表示黑夜模式

// DOM 引用
const block = ref<HTMLElement | null>(null)    // 滑动块
const day = ref<HTMLElement | null>(null)      // 日模式按钮
const night = ref<HTMLElement | null>(null)    // 夜模式按钮

// 切换到日间模式
const changeToDay = () => {
    if (!situation.value) {
        situation.value = true
    }
}

// 切换到夜间模式
const changeToNight = () => {
    if (situation.value) {
        situation.value = false
    }
}

// 切换动画效果
const changeWidth = () => {
    if (block.value) {
        block.value.style.width = '50px'
        setTimeout(() => {
            if (block.value) {
                block.value.style.width = '30px'
            }
        }, 80)
    }
}

// 监听状态变化
watch(situation, (newValue) => {
    changeWidth()
    if (newValue === true) {
        emit('day')
        if (block.value && night.value) {
            block.value.style.left = '2px'
            block.value.style.backgroundColor = '#fff'
            night.value.style.color = '#000'
        }
    } else {
        emit('night')
        if (block.value && night.value) {
            block.value.style.left = '38px'
            block.value.style.backgroundColor = '#000'
            night.value.style.color = '#fff'
        }
    }
})
</script>

<style scoped>
.slip-outer {
    height: 30px;
    width: 70px;
    border-radius: 15px;
    background-color: rgb(221, 221, 221);
    box-shadow: 0px 2px 5px rgb(140, 140, 140) inset;
    position: relative;
    z-index: 0;
}

.slip-block {
    height: 26px;
    width: 30px;
    border-radius: 13px;
    background-color: #fff;
    position: absolute;
    z-index: 0;
    top: 2px;
    left: 2px;
    box-shadow: 0px 2px 2px rgb(140, 140, 140);
    transition: 0.3s;
}

.inner {
    display: flex;
    width: 70px;
    cursor: pointer;
}

.inner div {
    z-index: 1;
    width: 50%;
    line-height: 30px;
    text-align: center;
}
</style>
