package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.utils.AI;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @date 2024-05-15 18:16:38
 * @description
 */
@RestController
@RequestMapping("/AI")
public class AIController {
    @PostMapping("/getAIAnswer")
    public R<String> getAIAnswer(@RequestBody String question){
        return R.success(AI.getAIAnswer(question));
    }
}
