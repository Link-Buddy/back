package com.linkbuddy.domain.category;

import com.linkbuddy.domain.category.repository.CategoryRepository;
import com.linkbuddy.global.entity.Category;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.linkbuddy.domain.Category
 * fileName       : CategoryService
 * author         : yl951
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        yl951       최초 생성
 */

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  //유저 생성 시 기본으로 "미분류" 카테고리 생성
  public Category createGeneralCategory(CategoryDto.CreatePrivate privateGroupDto) throws Exception {
    try {

      Long userId = (long) 1234; //getUserId;
      Long shareType = (long) 10; //get ShearType

      Category generalLinkGroup = Category.builder()
              .groupName(privateGroupDto.getGroupName())
              .shareTypeCd(shareType)
              .userId(userId)
              .build();

      return categoryRepository.save(generalLinkGroup);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<Tuple> findMyPrivateGroup() throws Exception {
    try {
      Long userId = (long) 1234; //getUserId;
      Long shareCode = (long) 10;//getShareCode
      return categoryRepository.findMyPrivateCategories(userId, shareCode);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  public List<Tuple> findMyBuddyGroup() throws Exception {
    try {
      Long userId = (long) 1234; //getUserId;
      Long shareCode = (long) 20; //getShareCode

      //TODO: 해당 buddy에 해당 user가 포함되어있느지도 확인해야함
      return categoryRepository.findMyBuddyCategories(shareCode);
    } catch (Exception e) {
      System.out.println("e");
      throw new Exception(e);
    }
  }

  //TODO: [get] 특정 버디 들어갔을때 해당하는 폴더들 조회

  //TODO : [put/delete] 특정 버디에 속해있으면 누구든 폴더명 편집/삭제 가능 ? > 가능하면 editorId 있어야할듯
  // 삭제 시 내부 링크들도 전체 삭제됨


}
