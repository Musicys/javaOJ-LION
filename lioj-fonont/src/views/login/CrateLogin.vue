<template>
    <div class="register-container">
        <!-- <canvas ref="canvasRef" class="canvas-bg"></canvas> -->
        <div class="register-form">
            <a-link class="back-link" @click="toLogin">返回登录</a-link>
            <div style="margin-bottom: 1em;"></div>

            <a-form :model="form" :style="{ width: '400px' }" @submit="handleSubmit">



                <a-form-item field="email" label="用户名称">
                    <a-input v-model="form.userName" placeholder="请输入名称" />
                </a-form-item>
                <a-form-item field="email" label="邮箱">
                    <a-input v-model="form.qqEmail" placeholder="请输入邮箱" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" @click="sendVerificationCode" :disabled="isCodeSending">{{ codeButtonText
                    }}</a-button>
                </a-form-item>
                <a-form-item field="account" label="账号">
                    <a-input v-model="form.userAccount" placeholder="请输入账号" />
                </a-form-item>
                <a-form-item field="password" label="密码">
                    <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
                </a-form-item>
                <a-form-item field="password" label="确认密码">
                    <a-input-password v-model="form.checkPassword" placeholder="确认密码" />
                </a-form-item>
                <a-form-item field="password" label="验证码">
                    <a-input v-model="form.verificationCode" placeholder="请输入验证码" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" html-type="submit" class="register-btn">注册</a-button>
                </a-form-item>
            </a-form>



        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import Upload from '@/components/Upload.vue'
import { UserControllerService, FileControllerService } from "@/generated"
import { message } from 'ant-design-vue'

const router = useRouter()
const form = ref({
    qqEmail: "",
    userAccount: "",
    userPassword: "",
    checkPassword: "",
    verificationCode: "",
    userName: ""
})
const toLogin = () => {
    router.push('/user/login')
}

const codeButtonText = ref("发送验证码")
const isCodeSending = ref(false)

const canvasRef = ref<HTMLCanvasElement | null>(null)
let animationFrameId: number
let particles: Particle[] = []

// 粒子类
class Particle {
    x: number
    y: number
    size: number
    speedX: number
    speedY: number
    color: string
    type: 'circle' | 'square' | 'triangle'

    constructor(x: number, y: number) {
        this.x = x
        this.y = y
        this.size = Math.random() * 4 + 2
        this.speedX = Math.random() * 2 - 1
        this.speedY = Math.random() * 2 - 1
        this.color = `rgba(255, 255, 255, ${Math.random() * 0.4 + 0.1})`
        this.type = ['circle', 'square', 'triangle'][Math.floor(Math.random() * 3)] as 'circle' | 'square' | 'triangle'
    }

    update() {
        this.x += this.speedX
        this.y += this.speedY

        // 添加一些随机运动
        this.speedX += (Math.random() - 0.5) * 0.1
        this.speedY += (Math.random() - 0.5) * 0.1

        // 限制速度
        this.speedX = Math.max(Math.min(this.speedX, 2), -2)
        this.speedY = Math.max(Math.min(this.speedY, 2), -2)

        if (this.size > 0.2) this.size -= 0.05
    }

    draw(ctx: CanvasRenderingContext2D) {
        ctx.fillStyle = this.color
        ctx.beginPath()

        switch (this.type) {
            case 'circle':
                ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2)
                break
            case 'square':
                ctx.rect(this.x - this.size, this.y - this.size, this.size * 2, this.size * 2)
                break
            case 'triangle':
                ctx.moveTo(this.x, this.y - this.size)
                ctx.lineTo(this.x + this.size, this.y + this.size)
                ctx.lineTo(this.x - this.size, this.y + this.size)
                break
        }

        ctx.fill()
    }
}

// 初始化 Canvas
const initCanvas = () => {
    const canvas = canvasRef.value
    if (!canvas) return

    const ctx = canvas.getContext('2d')
    if (!ctx) return

    // 设置 canvas 尺寸
    const resizeCanvas = () => {
        canvas.width = window.innerWidth
        canvas.height = window.innerHeight
    }
    resizeCanvas()
    window.addEventListener('resize', resizeCanvas)

    // 创建粒子
    const createParticles = () => {
        for (let i = 0; i < 60; i++) {
            particles.push(new Particle(
                Math.random() * canvas.width,
                Math.random() * canvas.height
            ))
        }
    }
    createParticles()

    // 动画循环
    const animate = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height)

        // 更新和绘制粒子
        particles.forEach((particle, index) => {
            particle.update()
            particle.draw(ctx)

            // 移除消失的粒子
            if (particle.size <= 0.2) {
                particles.splice(index, 1)
            }
        })

        // 添加新粒子
        if (particles.length < 60) {
            particles.push(new Particle(
                Math.random() * canvas.width,
                Math.random() * canvas.height
            ))
        }

        animationFrameId = requestAnimationFrame(animate)
    }
    animate()

    // 清理函数
    return () => {
        window.removeEventListener('resize', resizeCanvas)
        cancelAnimationFrame(animationFrameId)
    }
}

onMounted(() => {
    const cleanup = initCanvas()
    onUnmounted(() => {
        cleanup?.()
    })
})

const sendVerificationCode = async () => {
    if (!form.value.qqEmail) {
        message.error("请输入邮箱")
        return
    }

    isCodeSending.value = true
    codeButtonText.value = "发送中"


    let res = await UserControllerService.userSetVerificationCodeUsingPost({ qqEmail: form.value.qqEmail })
    if (res.code == 0) {
        // message.success(res.data)
        console.log(res);
        message.success("发送成功")

    }

    codeButtonText.value = "60秒后重试"

    let count = 60
    const timer = setInterval(() => {
        count--
        codeButtonText.value = `${count}秒后重试`
        if (count === 0) {
            clearInterval(timer)
            isCodeSending.value = false
            codeButtonText.value = "发送验证码"
        }
    }, 1000)
}

const handleSubmit = async () => {
    if (form.value.userPassword !== form.value.checkPassword) {
        message.error("两次输入的密码不一致")
        return
    }

    let res = await UserControllerService.userRegisterUsingPost({
        ...form.value
    })
    if (res.code == 0) {
        message.success("注册成功")







        router.push({
            path: `/user/login?username=${form.userAccount}`
        })
    }
    else {
        message.error(res.message)
    }
}
</script>

<style scoped>
.register-container {
    position: fixed;
    width: 100vw;
    height: 100vh;
    display: flex;
    top: 0;
    left: 0;
    justify-content: center;
    align-items: center;
    /* background: linear-gradient(135deg, #2c3e50, #3498db, #2ecc71); */
    background: var(--home-back);
    overflow: hidden;
}

.canvas-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
}

/* 确保注册表单在 canvas 上层 */
.register-container>*:not(.canvas-bg) {
    position: relative;
    z-index: 2;
}

.register-logo {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 20px;
}

.back-icon {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 24px;
    cursor: pointer;
}

.back-link {
    position: absolute;
    top: 20px;
    right: 60px;
    font-size: 14px;
    color: #1890ff;
    cursor: pointer;
}

.register-form {
    background: var(--home-back-from);
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    animation: fadeIn 0.5s ease;
    border: .5px solid var(--border);
}

.register-btn {
    width: 100%;
    height: 40px;
    font-size: 16px;
    transition: all 0.3s ease;
    border: .5px solid var(--border);
}

.register-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>