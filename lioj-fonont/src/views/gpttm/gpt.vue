<template>
    <div class="gpt">
        <a-input-search style="width: 100%;" v-model="tilte" :disabled="Isloding" placeholder="输入标题(比如两数之和)"
            search-button>
            <template #button-icon>
                <icon-plus-circle />
            </template>
            <template #button-default>
                <div @click="addti">{{ Isloding ? '生成中' : '生成' }}</div>

            </template>
        </a-input-search>

        <div class="timelist">
            <a-table :data="data" :pagination="false" style="margin-top: 30px;max-width: 1200px; margin: auto;">
                <template #columns>


                    <a-table-column title="题目标题" data-index="tags">
                        <template #cell="{ record }">

                            {{ record.title }}

                        </template>
                    </a-table-column>

                    <a-table-column title="题目标签" data-index="tags">
                        <template #cell="{ record }">

                            <a-tag v-for="i in record.tags">{{ i }}</a-tag>

                        </template>
                    </a-table-column>

                    <a-table-column title="判题用例" data-index="judgeCase">
                        <template #cell="{ record }">
                            <div style="font-size: .6em;">

                                <a-trigger trigger="click">
                                    <a-button style="font-size: 1em;">查看</a-button>
                                    <template #content>
                                        <div class="demo-arrow" style="background: #2E2E30;
                                        width: 150px;
                                        box-shadow: .1px .1px .1px #888888;
                                        font-size: .6em;

                                        border-radius: 10px;
                                        border: 1px solid rgba(.1, 255, 255, 255);
                                        padding: 5px;
                                        ">
                                            <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;"
                                                v-for="i in record.judgeCase">{{ i.input }}->{{
                                                    i.output
                                                }}</a-tag>
                                        </div>
                                    </template>
                                </a-trigger>

                            </div>


                        </template>
                    </a-table-column>

                    <a-table-column title="判题规则" data-index="judgeConfig">
                        <template #cell="{ record }">
                            <!-- {{ record.judgeConfig }} -->
                            <div style="font-size: .6em;">

                                <a-trigger trigger="click">
                                    <a-button style="font-size: 1em;">查看</a-button>
                                    <template #content>
                                        <div class="demo-arrow" style="background: #2E2E30;
                                        width: 80px;
                                        box-shadow: .1px .1px .1px #888888;
                                        font-size: .6em;
                                        border-radius: 10px;
                                        border: 1px solid rgba(.1, 255, 255, 255);
                                        padding: 5px;
                                        display: flex;
                                        justify-content: center;
                                        align-items: center;
                                        flex-direction: column;
                                        ">
                                            <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                                时间：{{ record.judgeConfig.timeLimit }}
                                            </a-tag>
                                            <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                                内存：{{ record.judgeConfig.stackLimit }}
                                            </a-tag>
                                            <a-tag style="font-size: 1em; margin-right: .5em;   margin-top: .5em;">

                                                堆栈：{{ record.judgeConfig.memoryLimit }}
                                            </a-tag>
                                        </div>
                                    </template>
                                </a-trigger>

                            </div>


                        </template>
                    </a-table-column>





                    <a-table-column title="题目内容" data-index="content">
                        <template #cell="{ record }">

                            <a-typography-paragraph style="max-width:100px ; cursor: pointer;" underline :ellipsis="{
                                rows: 1,
                                showTooltip: true,
                            }">
                                {{ record.content }}
                            </a-typography-paragraph>
                        </template>
                    </a-table-column>

                    <a-table-column title="操作">
                        <template #cell="{ record, index }">
                            <a-button @click="addqeustionComment(record)"
                                style="font-size: .6em; border-radius: 15px; border: 1px solid rgba(255,255,255,.3); margin-right: 1em; background: #303030;"
                                type="primary" icon="<icon-pen-fill />" status="success">追加</a-button>

                            <a-button @click="deletes(index)"
                                style="font-size: .6em; border-radius: 15px; margin-right: 1em; border: .5px solid rgba(255,255,255,.3);  background: #303030;"
                                type="primary" status="danger">删除</a-button>

                        </template>
                    </a-table-column>
                </template>
            </a-table>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref, Ref } from 'vue';
import { QianWenControllerService, QuestionControllerService } from '@/generated';
import { message } from 'ant-design-vue';
import { GPTItem, appendToGPTlist, removeFromGPTlist, resetGPTlist, getGPTlist } from '@/util/localStorage';

const data: Ref<any[]> = ref([


])

const tilte = ref("")

//是否在生成
const Isloding = ref(false)

const addti = async () => {

    if (Isloding.value) {
        return
    }
    else if (tilte.value == "") {
        message.error("请输入标题")
        return
    }

    Isloding.value = true
    let res = await QianWenControllerService.qwenGptUsingGet(tilte.value)


    if (res.code == 0) {


        let tip = JSON.parse(`${res.data as any}`)

        data.value.push(tip)

        appendToGPTlist(tip)
        message.success("生成题目成功")

    }
    else {
        message.error("错误")
    }


    Isloding.value = false
}
const deletes = (index: number) => {


    data.value.splice(index, 1);
    removeFromGPTlist(index);


}
const addqeustionComment = async (obj) => {
    if (obj == null) {
        return
    }
    let res = await QuestionControllerService.addQuestionUsingPost(obj)

    if (res.code == 0) {
        message.success("添加成功")
    }
    else {
        message.success("题目格式错误，请重新生成")



    }

}

onMounted(() => {

    data.value = getGPTlist()



})

</script>

<style scoped>
.gpt {
    max-width: 1080px;
    width: 80%;
    margin: 1em auto;

}

.timelist {
    margin: 1em auto;
}
</style>
