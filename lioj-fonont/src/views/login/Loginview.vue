<template>
    <div class="back-link" @click="toHome" href="/">返回主页</div>
    <div class="login-container">
        <!-- <canvas ref="canvasRef" class="canvas-bg"></canvas> -->

        <div class="login-form">
            <div style="text-align: center; margin-bottom: 24px;">
                <img src="https://web-muisc-1321946919.cos.ap-guangzhou.myqcloud.com/user_avatar/1895117906623131650/oqlLmAMv-fufu.jpg"
                    alt="logo" style="width: 80px; height: 80px; border-radius: 50%;" />
            </div>
            <a-form :model="form" :style="{ width: '400px' }" @submit="handleSubmit">
                <a-form-item field="name" label="账号">
                    <a-input v-model="form.userAccount" placeholder="请输入账号" />
                </a-form-item>
                <a-form-item field="post" label="密码">
                    <a-input-password v-model="form.userPassword" placeholder="请输入密码" />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" html-type="submit" class="login-btn">登录</a-button>
                    <a-link @click="toRegister" style="margin-left: 25px; width: 2em; white-space: nowrap;">注册</a-link>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from "vue"
import { UserControllerService } from "@/generated"
import store from "@/store";
import { message } from "ant-design-vue"
import { useRouter } from "vue-router";




const router = useRouter()
const form = reactive({
    "userAccount": "",
    "userPassword": ""
});

const toHome = () => {
    router.push('/')
}

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

    constructor(x: number, y: number) {
        this.x = x
        this.y = y
        this.size = Math.random() * 3 + 1
        this.speedX = Math.random() * 2 - 1
        this.speedY = Math.random() * 2 - 1
        this.color = `rgba(255, 255, 255, ${Math.random() * 0.5 + 0.2})`
    }

    update() {
        this.x += this.speedX
        this.y += this.speedY

        if (this.size > 0.2) this.size -= 0.1
    }

    draw(ctx: CanvasRenderingContext2D) {
        ctx.fillStyle = this.color
        ctx.beginPath()
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2)
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
        for (let i = 0; i < 50; i++) {
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
        if (particles.length < 50) {
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

const toLogin = () => {
    router.push('/');
};

const toRegister = () => {
    router.push('/user/createUser');
};

const handleSubmit = async (data: any) => {
    let res = await UserControllerService.userLoginUsingPost(form)
    console.log(res.code);
    if (res.code == 0) {
        await store.dispatch('increment')
        message.success("登录成功")
        router.push({
            path: "/"
        })
    }
    else {
        message.error("密码错误")
    }
};
</script>

<style scoped>
.back-link {

    z-index: 999;

    position: fixed;
    top: 20px;
    right: 60px;
    font-size: 14px;
    color: #1890ff;
    cursor: pointer;

}

.login-container {

    display: flex;
    z-index: 99;
    justify-content: center;
    align-items: center;
    /* background: linear-gradient(135deg, #1a2a6c, #b21f1f, #fdbb2d); */
    background: var(--home-back);

    overflow: hidden;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;

}

.canvas-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
}

/* 确保登录表单在 canvas 上层 */
.login-container>*:not(.canvas-bg) {
    position: relative;
    z-index: 2;
}

.login-form {
    background: var(--home-back-from);
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    animation: fadeIn 0.5s ease;
    border: .5px solid var(--border);
}

.login-btn {
    width: 100%;
    height: 40px;
    font-size: 16px;
    border: .5px solid var(--border);
    transition: all 0.3s ease;
}

.login-btn:hover {
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