package com.xxxx.yeb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共的返回对象
 *    Lombok注解
 *      无参构造：@NoArgsConstructor
 *      全参构造：@AllArgsConstructor
 * @author arthur
 * @date 2021/3/8 18:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private Integer code;
    private String message;
    private Object object;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    /**
     * 成功返回结果
     * @param message
     * @param object
     * @return
     */
    public static RespBean success(String message,Object object){
        return new RespBean(200,message,object);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param object
     * @return
     */
    public static RespBean error(String message,Object object){
        return new RespBean(500,message,object);
    }


}
