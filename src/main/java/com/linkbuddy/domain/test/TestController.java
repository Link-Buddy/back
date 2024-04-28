package com.linkbuddy.domain.test;

import com.linkbuddy.domain.buddy.dto.BuddyDTO;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.test
 * fileName       : TestController
 * author         : admin
 * date           : 2024-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-27        admin       최초 생성
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String home(Model model) {
        return "HELLO WORLD!";
    }
}
