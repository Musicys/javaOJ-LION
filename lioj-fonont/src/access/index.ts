import router from "@/router"
import store from "@/store";
import { ACCESS_ENUM } from "@/access/config"
import { Initial } from "@/util/routerRaget"

router.beforeEach(async (to, from, next) => {



    // @ts-ignore
    if (store.state.user.user.userRole == ACCESS_ENUM.NO_LOGIN) {
        await store.dispatch('increment')
        // @ts-ignore

    }


    // @ts-ignore 管理员
    let userRole = store.state.user.user.userRole


    //页面无权限
    if (to.meta.assess == undefined) {

        return next()

    }

    if (userRole == ACCESS_ENUM.ADMIN) {




        return next()

    }



    // @ts-ignore 用户
    console.log(userRole == to.meta.assess);

    // @ts-ignore 用户
    if (to.meta.assess == userRole) {
        return next()

    }
    else {


        next("/user/login")
    }








})
//跳转路由后做的逻辑，过渡动画，或者动态请求啥的
router.afterEach((to, from) => {

    Initial()
    document.title = to.name as string
})