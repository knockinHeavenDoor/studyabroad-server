package com.sakura.study.controller.apiController;

import com.google.common.cache.LoadingCache;
import com.sakura.study.dto.UserDto;
import com.sakura.study.model.User;
import com.sakura.study.service.UserService;
import com.sakura.study.utils.Assert;
import com.sakura.study.utils.BusinessException;
import com.sakura.study.utils.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource(name = "userCache")
    private LoadingCache<String, Optional<User>> userCache;

    /**
     * 用户详细信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/info",method = RequestMethod.GET)
    @ApiOperation(value="获取用户详细信息", notes="根据token来获取用户详细信息")
    @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "header")
    public ResponseResult getUserById(@RequestHeader("Token")String token) {
        User user = userCache.getUnchecked(token).orElse(null);
        Assert.notNull(user,"登录已失效");
        UserDto data = userService.getUserInfo(user.getId());
        return ResponseResult.success(data);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/session",method = RequestMethod.POST)
    public ResponseResult login(@RequestBody User user){
        String message = "用户名或密码不能为空";
        Assert.notEmpty(user.getUsername(),message);
        Assert.notEmpty(user.getPassword(),message);
        User record = userService.login(user);
        String token = UUID.randomUUID().toString();
        userCache.put(token,Optional.of(record));
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",record);
        return ResponseResult.success(map);
    }

    /**
     * 用户登出
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/session",method = RequestMethod.DELETE)
    public ResponseResult login_out(@RequestHeader("Token")String token){
        if(StringUtils.isEmpty(token))
            throw new BusinessException(404,"您尚未登录");
        userCache.invalidate(token);
        return ResponseResult.success("退出登录成功",null);
    }

    /**
     * 用户注册
     * @param userDto
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseResult register(@RequestBody UserDto userDto){
        UserDto data = userService.register(userDto);
        return ResponseResult.success(data);
    }
}
