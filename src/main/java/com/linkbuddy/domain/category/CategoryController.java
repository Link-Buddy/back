package com.linkbuddy.domain.category;

import com.linkbuddy.global.config.jwt.SecurityUtil;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.util.ResponseMessage;
import com.linkbuddy.global.util.StatusEnum;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.Category
 * fileName       : CategoryController
 * author         : yl951
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        yl951       최초 생성
 */

@RequestMapping("categories")
@RestController
public class CategoryController {
  @Autowired
  private CategoryService categoryService;
  @Autowired
  SecurityUtil securityUtil;


  @GetMapping("my")
  public ResponseEntity getMyPrivateCategory() throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<CategoryDto.PrivateCategory> privateCategories = categoryService.findMyPrivateCategories(userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(privateCategories)
            .build());
  }

  @GetMapping("buddy")
  public ResponseEntity getMyBuddyCategory(@RequestParam("buddyId") Long buddyId) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    List<CategoryDto.BuddyCategory> buddyCategories = categoryService.findMyBuddyCategoriesByBuddyId(buddyId, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(buddyCategories)
            .build());
  }

  @PostMapping("/my")
  public ResponseEntity createPrivateCategory(@RequestBody CategoryDto.CreatePrivate privateDto) throws Exception {
    Long userId = securityUtil.getCurrentUserId();

    Category newCategory = categoryService.createPrivateCategory(privateDto, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.CreatePrivate(newCategory))
            .build());
  }

  @PostMapping("/buddy")
  public ResponseEntity createBuddyCategory(@RequestBody CategoryDto.CreateBuddy buddyCategoryDto) throws Exception {
    Long userId = securityUtil.getCurrentUserId();
    Category newCategory = categoryService.createBuddyCategory(buddyCategoryDto, userId);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.CreateBuddy(newCategory))
            .build());
  }

  @PutMapping("{id}")
  public ResponseEntity updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto.Update updateDto) throws Exception {

    Category updatedCategory = categoryService.updateCategory(id, updateDto);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data(new CategoryDto.Update(updatedCategory))
            .build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteCategory(@PathVariable("id") Long id) throws Exception {

    categoryService.deleteCategory(id);

    return ResponseEntity.ok(ResponseMessage.builder()
            .status(StatusEnum.OK)
            .data("")
            .build());
  }

}
