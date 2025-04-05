<template>
    <div class="messge">
        <div style="color: white;">更新用户</div>

        <Upload :url="store.state.user.user.userAvatar" @updateFile="updateFile"></Upload>
        <a-form :model="form" :style="{ width: '600px' }">

            <a-input v-model="form.userName" placeholder="用户名称" style="margin-bottom:1em ;" />


            <a-textarea v-model="form.userProfile" :auto-size="true" placeholder="用户简介"
                style=" margin-bottom:1em ; min-height: 150px;" :max-length="{ length: 500 }" allow-clear
                show-word-limit />

            <a-button @click="handleSubmit">提交</a-button>

        </a-form>

    </div>

</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue"
import Upload from "@/components/Upload.vue"
import { QuestionCommentControllerService, FileControllerService, UserControllerService } from "@/generated"
import { useStore } from "vuex"
import { copyMatchingProperties } from "@/util/copy"
import { message } from "ant-design-vue"
import { useRouter } from "vue-router"
const router = useRouter()
const store = useStore()
const form = ref({
    "userAvatar": "",
    "userName": "",
    "userProfile": ""
})
const File = ref()


const updateFile = (e) => {
    File.value = e
    console.log(File.value);


}
const handleSubmit = async () => {


    if (!form.value.userAvatar) {
        message.warn("文件为空")
        return

    }

    if (File.value) {
        let res = await FileControllerService.uploadFileUsingPost(
            "user_avatar", File.value.file
        )
        if (res.code == 0) {
            form.value.userAvatar = res.data
            let resd = await UserControllerService.updateMyUserUsingPost(
                form.value
            )
            if (resd.code == 0) {


                message.success("修改成功")

            }
            else {

                message.error("修改失败")
                return
            }

        }
        else {
            message.error("图片格式错误")
        }
    }
    else {
        let resd = await UserControllerService.updateMyUserUsingPost(
            form.value
        )
        if (resd.code == 0) {


            message.success("修改成功")

        } else {

            message.error("修改失败")
            return
        }

    }


    router.push({
        path: `/messge/${store.state.user.user.id}`
    }).then(() => {
        store.dispatch('increment')
    });


}

onMounted(() => {
    form.value = copyMatchingProperties(form.value, store.state.user.user)

})
</script>

<style scoped>
.messge {
    width: 80%;
    margin: 1em auto;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;

}
</style>