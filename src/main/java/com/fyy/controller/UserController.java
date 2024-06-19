package com.fyy.controller;

import com.fyy.common.MyException;
import com.fyy.common.R;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.pojo.dto.ForgetPasswordDTO;
import com.fyy.pojo.dto.LoginDTO;
import com.fyy.pojo.dto.RegisterDTO;
import com.fyy.service.StudentService;
import com.fyy.service.TeacherService;
import com.fyy.utils.VerifyUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录注册界面
 * @date 2024-05-30 18:38:51
 * @description
 */
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    VerifyUtil verifyUtil;

    @ApiOperation("获取验证码")
    @GetMapping("/getVerify")
    public R<String> getVerify() {
        return R.success(verifyUtil.getVerify());
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public R<?> login(@Valid @RequestBody LoginDTO loginDto) {
        if (loginDto.getIsTeacher()) teacherService.getTeacher(loginDto);
        else studentService.getStudent(loginDto);
        return R.success();
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R<?> register(@Valid @RequestBody RegisterDTO registerDto) {
        if (registerDto.getIsTeacher()) teacherService.addTeacher(registerDto);
        else studentService.addStudent(registerDto);
        return R.success();
    }

    @ApiOperation("忘记密码")
    @PostMapping("/forgetPassword")
    public R<?> forgetPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDto) {
        if (!studentService.forgetPassword(forgetPasswordDto) && !teacherService.forgetPassword(forgetPasswordDto)) {
            throw new MyException(StatusCodeEnum.USER_NOT_EXIST);
        }
        return R.success();
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }
}
