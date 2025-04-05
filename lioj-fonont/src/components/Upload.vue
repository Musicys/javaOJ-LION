<template>
    <div class="update">

        <div class="box">
            <a-space direction="vertical">
                <a-upload action="/" :fileList="file ? [file] : []" :show-file-list="false" @change="onChange"
                    @progress="onProgress">
                    <template #upload-button>
                        <div :class="`arco-upload-list-item${file && file.status === 'error' ? ' arco-upload-list-item-error' : ''
                            }`">
                            <div class="arco-upload-list-picture custom-upload-avatar" v-if="file && file.url">
                                <img :src="file.url" />
                                <div class="arco-upload-list-picture-mask">
                                    <IconEdit />
                                </div>
                                <a-progress v-if="file.status === 'uploading' && file.percent < 100"
                                    :percent="file.percent" type="circle" size="mini" :style="{
                                        position: 'absolute',
                                        left: '50%',
                                        top: '50%',
                                        transform: 'translateX(-50%) translateY(-50%)',
                                    }" />
                            </div>
                            <div class="arco-upload-picture-card" style="position: relative;" v-else>
                                <div class="arco-upload-picture-card-text ">
                                    <IconPlus />
                                    <img style="top: 0;left: 0; position: absolute; height:100%; width: 100%; z-index: 0; opacity: .6;"
                                        :src="url" />
                                    <div style="margin-top: 10px; font-weight: 600">Upload</div>
                                </div>
                            </div>
                        </div>
                    </template>
                </a-upload>
            </a-space>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { IconEdit, IconPlus } from '@arco-design/web-vue/es/icon';
import { onMounted, ref } from 'vue';

const file = ref();
const { url } = defineProps(['url'])
const emit = defineEmits(["updateFile"])
const onChange = (_, currentFile) => {
    file.value = {
        ...currentFile,
        // url: URL.createObjectURL(currentFile.file),
    };


};
const onProgress = (currentFile) => {
    file.value = currentFile;
    console.log(file.value);
    emit("updateFile", file.value)
};
onMounted(() => {


})
</script>


<style scoped>
.update {
    display: flex;

    justify-content: center;
    margin-bottom: 1em;
    align-items: center;
    width: 100%;
}
</style>