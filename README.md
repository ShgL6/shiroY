# Readme
- 关系型数据库 `mysql` 存储用户的**认证**信息和**权限**信息，基于 `RBAC` - Role-Based Access Control 模型
- 自定义 `Realm` - 向 `SecurityManager` 提供存储在数据库的用户信息
- 自定义过滤器 `Filter`，配置 `FilterChain`，实现定制化的权限验证和错误反馈
