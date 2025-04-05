import { ref } from "vue"

import { ACCESS_ENUM } from "@/access/config"


import { UserControllerService, QuestionCommentControllerService } from "@/generated"
import store from "@/store"

export const IsStart = ref(true);


export const Initial = async () => {
    // @ts-ignore
    if (store.state.user.user.userRole == ACCESS_ENUM.NO_LOGIN) {
        return
    }
    else {
        let res = await QuestionCommentControllerService.getRequestedUsingGet()
        if (res.code == 0) {


            IsStart.value = res.data as boolean
        }

    }




}