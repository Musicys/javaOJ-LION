package com.yupi.yuoj.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.yupi.yuoj.model.dto.comment.QuesitonSumbitNumber;
import lombok.Data;

/**
 * 用户
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@TableName(value = "user")
@Data
public class DtoUser implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;








    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;





    /**
     *QQ邮箱
     */
    private String qqEmail;


    /**
     *用户答题数
     */

    QuesitonSumbitNumber quesitonSumbitNumber;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public  DtoUser setuser(){
            return null;
    }
}