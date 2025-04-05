package com.yupi.yuoj.controller;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.enums.UserRoleEnum;
import com.yupi.yuoj.service.UserService;
import com.yupi.yuoj.utils.QwenApiClient;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/qianwen")
public class QianWenController {

    @Resource
    private UserService userService;

    @Resource
    QwenApiClient qwenApiClient;

    @GetMapping
    public BaseResponse<String> QwenGpt(
            HttpServletRequest request,String title) {


        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();
        if (id == null) {

            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");

        }
        if(!UserRoleEnum.ADMIN.getValue().equals(loginUser.getUserRole()))
        {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "无权限");
        }



        // 示例：发送用户输入内容
        String userContent = title;
        String response = qwenApiClient.askQwen(userContent);


        try {
            return  ResultUtils.success(response.toString());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "错误");
        }



    }
}
