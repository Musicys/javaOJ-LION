<template>
    <div class="messge">

        <!-- 编辑 -->

        <div class="cart">
            <div class="cart-left">
                <img :src="data.userAvatar" alt="">
            </div>
            <div class="cart-right">

                <div class="tilte">
                    {{ data.userName }}
                </div>
                <div class="desc">
                    {{ data.userProfile }}
                </div>
                <div class="tut">
                    <a-button v-if="IsMyMessge" @click="goform">编辑</a-button>
                </div>
            </div>

        </div>


        <div class="gird" style="margin: 1em auto;">
            <a-card class="a-cart" style="background: #383F3F;color: white; min-width: 150px;" title="用户提交题目数">
                <template #extra>

                </template>
                <div class="cart-span">
                    {{ data.quesitonSumbitNumber.sumbitnumber }}
                </div>

            </a-card>
            <a-card class="a-cart" style="background: #383F3F;color: white; min-width: 150px;" title="用户正确率">
                <template #extra>

                </template>
                <div class="cart-span">
                    {{ calculatePassRate() || 0 }}%
                </div>

            </a-card>
            <a-card class="a-cart" style="background: #383F3F;color: white;  min-width: 150px;" title="用户创建时间">
                <template #extra>

                </template>

                <div class="cart-span">
                    {{ initialData(data.createTime) }}
                </div>
            </a-card>
        </div>
    </div>

    <div class="xinx">
        <!--展示 -->

    </div>




</template>

<script setup lang="ts">
import { useStore } from 'vuex';
import { ref, Ref, onMounted, watch } from 'vue';
import { Question, QuestionAddRequest, QuestionControllerService, QuestionQueryRequest, UserControllerService } from '@/generated';
import { useRouter } from 'vue-router';
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { LineChart } from 'echarts/charts';
import { GridComponent, TooltipComponent } from 'echarts/components';
import VChart from 'vue-echarts';
import { message } from 'ant-design-vue';
import { initialData } from "@/util/date"
use([CanvasRenderer, LineChart, GridComponent, TooltipComponent]);


const chartOption = ref({
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [],
            type: 'line'
        }
    ]
});

// TODO: 添加获取提交数据的逻辑
const router = useRouter()
const store = useStore()
const IsMyMessge = ref(false)
const pops = defineProps(["id"])

const goform = () => {
    router.push({
        path: "/user/mssge"
    })
}

const Initial = () => {

    if (data.value.id == store.state.user.user.id) {
        IsMyMessge.value = true



    }




}

const data = ref({
    createTime: "",
    id: "",
    qqEmail: "",
    quesitonSumbitNumber: {
        sumbitnumber: '3',
        correctnumber: '0'
    },
    updateTime: "",
    userAvatar: "",
    userName: "",
    userProfile: "",
    userRole: ""
})


const InitialData = async () => {



    let res = await UserControllerService.getDtoUserByIdUsingGet(pops.id);
    if (res.code == 0) {
        console.log(res);
        data.value = res.data as any

    }
    else {

        let res = await UserControllerService.getDtoUserByIdUsingGet(store.state.user.user.id);
        if (res.code == 0) {

            data.value = res.data as any
            // @ts-ignore
            console.log(data.quesitonSumbitNumber.correctnumber / data.quesitonSumbitNumber.sumbitnumber);

        }
        else {
            message.error("错误")
        }
    }
    Initial()
}





// 计算题目通过率
function calculatePassRate() {
    const totalSubmissions = data.value.quesitonSumbitNumber.sumbitnumber; // 总提交数
    const correctSubmissions = data.value.quesitonSumbitNumber.correctnumber; // 正确提交数

    // 防止除以 0 的错误
    // @ts-ignore
    if (totalSubmissions === 0) {
        return 0; // 如果总提交数为 0，通过率为 0
    }

    // 计算通过率并保留两位小数
    // @ts-ignore
    const passRate = (correctSubmissions / totalSubmissions) * 100;
    return parseFloat(passRate.toFixed(2)); // 返回百分比格式，保留两位小数
}


onMounted(() => {
    InitialData()

})


</script>

<style scoped>
.messge {
    width: 65%;
    margin: auto;
    color: white;

}

.cart {
    width: 100%;
    margin: 1em auto;
    height: 250px;
    display: flex;

    background: var(--home-back);
    color: var(--home-color);
    display: flex;
    gap: 10px;
    border: 1px solid var(--border);
    border-radius: 15px;
}

.cart-left>img {
    margin-left: 1em;
    margin-top: 1em;
    width: 150px;
    height: 150px;
    border-radius: 50%;
    border: 10px solid#3DB7FD;

}

.cart-right {
    margin-left: 1em;
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: start;
    flex-direction: column;



    .tilte {
        margin-top: 1.2em;
        font-size: 1.5em;
    }

    .desc {
        margin-top: 2em;
        min-height: 100px;
        width: 90%;
        font-size: 1.2em;
    }

    .tut {

        display: flex;
        justify-content: end;
        align-items: center;
        margin-top: 2em;
    }
}

.cart-right>div {
    width: 95%;
}

.gird {
    width: 100%;
    display: grid;
    gap: 10px;

    height: 150px;
    grid-template-columns: repeat(3, 1fr);

}

.a-cart {
    text-align: center;
}

.cart-span {
    font-size: 2em;
    margin-top: 1em;
}
</style>
