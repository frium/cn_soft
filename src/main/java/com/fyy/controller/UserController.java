package com.fyy.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.fyy.common.R;
import com.fyy.context.BaseContext;
import com.fyy.pojo.dto.ForgetPasswordDTO;
import com.fyy.pojo.dto.LoginDTO;
import com.fyy.pojo.dto.RegisterDTO;
import com.fyy.service.StudentService;
import com.fyy.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

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
    @Value("${verify.width}")
    Integer width;
    @Value("${verify.height}")
    Integer height;
    @Value("${verify.codeCount}")
    Integer codeCount;
    @Value("${verify.lineCount}")
    Integer lineCount;

    @ApiOperation("获取验证码")
    @GetMapping("/getVerify")
    public R<String> getVerify() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, lineCount);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lineCaptcha.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        IoUtil.close(outputStream);
        redisTemplate.opsForValue().set("verify", lineCaptcha.getCode(), 1, TimeUnit.MINUTES);
        return R.success(Base64.encode(bytes));
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
    public R<String> forgetPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDto) {
        String password = studentService.forgetPassword(forgetPasswordDto);
        if (password.isEmpty()) return R.success(teacherService.forgetPassword(forgetPasswordDto));
        else return R.success(password);
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }
}
