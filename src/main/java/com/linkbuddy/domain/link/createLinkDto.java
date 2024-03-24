package com.linkbuddy.domain.link;

import lombok.Data;

@Data
public class createLinkDto {
  private String name;
  private String description;
  private String linkUrl;
  private Integer linkGroupId;
  private Boolean deleteTf;
  private Integer userId;

}
