
# Link-Buddy (링크 버디)
<p align="center">
  <img width="30%" alt="logo_main" src="https://github.com/user-attachments/assets/93591328-14c9-4344-885c-8a4b1a71782c">
</p>

## Backend 스택
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-%23003545.svg?style=for-the-badge&logo=mariadb&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-%236DB33F.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-%23007ACC.svg?style=for-the-badge&logo=java&logoColor=white)
![DBeaver](https://img.shields.io/badge/DBeaver-%230072BC.svg?style=for-the-badge&logo=dbeaver&logoColor=white)

### ERD


<details>
  <summary>SQL 테이블 생성 스크립트 보기/접기</summary>

  ```sql
  CREATE TABLE `User` (
      `id`    INT NULL,
      `email` VARCHAR(255) NOT NULL,
      `name`  VARCHAR(255) NULL,
      `password`  VARCHAR(255) NULL,
      `social`    VARCHAR(255) NULL,
      `회원상태`  INT NULL,
      `image_url` VARCHAR(255) NULL,
      `refreshToken`  VARCHAR(255) NULL,
      `created_at`    TIMESTAMP NULL,
      `deleted_at`    TIMESTAMP NULL,
      `last_logged_at`    TIMESTAMP NULL
  );

  CREATE TABLE `Buddy` (
      `id`    INT NULL,
      `creator_id`    INT NULL,
      `name`  VARCHAR(255) NULL,
      `created_at`    TIMESTAMP NULL,
      `updated_at`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE `Link` (
      `id`    INT NULL,
      `user_id`   INT NULL,
      `category_id`   BIGINT NOT NULL,
      `name`  VARCHAR(255) NULL,
      `description`   TEXT NULL,
      `link_url`  VARCHAR(255) NULL,
      `delete_tf` BOOLEAN NULL,
      `created_at`    TIMESTAMP NULL,
      `updated_at`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE `BuddyUser` (
      `id`    INT NULL,
      `user_id`   INT NULL,
      `buddy_id`  INT NULL,
      `alert_tf`  BOOLEAN NULL,
      `pin_tf`    BOOLEAN NULL
  );

  CREATE TABLE `Category` (
      `id`    BIGINT NOT NULL,
      `user_id`   INT NULL,
      `buddy_id`  INT NULL,
      `category_name` VARCHAR(255) NULL,
      `share_type_cd` BIGINT NULL,
      `delete_tf` BOOLEAN NULL,
      `alert_tf`  BOOLEAN NULL,
      `pin_tf`    BOOLEAN NULL,
      `accept_tf` BOOLEAN NULL,
      `accept_df` BOOLEAN NULL,
      `created_at`    TIMESTAMP NULL,
      `updated_at`    TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE `Favorite` (
      `d` INT NOT NULL,
      `user_id`   INT NULL,
      `link_id`   INT NULL,
      `id2`   BIGINT NOT NULL,
      `created_at`    TIMESTAMP NULL,
      `Field` VARCHAR(255) NULL
  );
```
</details> 


<p align="center">
      <img width="80%" alt="erd" src="https://github.com/user-attachments/assets/b57262f8-05d0-4458-894e-3492ceb778a3">
    </p>

### Backend 폴더구조
```jsx
src/
└── main/
    └── java/
        └── com/
            └── linkbuddy/
                ├── config/                  # 애플리케이션 설정 파일들
                ├── common/                  # 전역적으로 사용되는 공통 클래스들
                ├── domain/                  # 도메인별 폴더
                    ├── buddy/              # 'Buddy' 도메인
                        ├── dto/            # Data Transfer Objects
                        ├── entity/         # JPA 엔티티 클래스
                        ├── repository/     # Spring Data JPA 리포지토리
                        ├── service/        # 비즈니스 로직
                        └── controller/     # HTTP 요청 처리
                    ├── anotherdomain/     # 다른 도메인
                        ├── ...
                ├── Application.java        # 애플리케이션 메인 클래스
└── resources/
```

## ⭐이슈사항
- [[Spring boot] Spring Security 3.x.x + JWT ](https://www.notion.so/Spring-boot-Spring-Security-3-x-x-JWT-5485f360e1e24726a018125e72a8b33e?pvs=21)
- [[Spring boot] OncePerRequestFilter와 Filter의 차이](https://www.notion.so/Spring-boot-OncePerRequestFilter-Filter-2664db6691cb46e8803a30c91988229d?pvs=21)
- [[Spring boot] Ouath2](https://www.notion.so/Spring-boot-Ouath2-3ded56be98494a9b84aa725c1ee5a13f?pvs=21)
- [[241102] Link Preview](https://www.notion.so/241102-Link-Preview-efe8bc029fb74ae6a3849333f47be8a7?pvs=21)
- [[241019] SQL문 조건 위치에 따른 문제 해결](https://www.notion.so/241019-SQL-1240e2a0ccba8016a55bd0f64ed7a793?pvs=21)


---

#### 📚 Repository
* Frontend: [Link-Buddy/front](https://github.com/Link-Buddy/front)
