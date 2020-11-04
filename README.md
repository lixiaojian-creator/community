## 码匠社区

## 资料
[spring文档](https://spring.io/)  
[springweb文档](https://spring.io/guides/gs/serving-web-content/)  
[es社区](https://elasticsearch.cn/)  
[bootstrap4](https://v4.bootcss.com/docs/components/buttons/)  
[oAuth](https://developer.github.com/apps/)
## 工具
[git官网](https://git-scm.com/)  
[flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven#creating-the-project)

## 脚本
```sql
create table USER
(
  ID           INTEGER auto_increment primary key,
  ACCOUNT_ID   VARCHAR(100),
  NAME         VARCHAR(50),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
);
```