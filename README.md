# learning-architect
 个人学习架构师的代码，从简单的Spring到后续的各种框架
  
  ## 01.springboot-mvc-frame
  采用的是最简单的权限控制，filter的RBAC，功能包含 登录、菜单、角色、用户、权限,后端密码采用MD5加密
  
  相应技术栈：
      
      后端：SpringBoot、Mybatis、PageHelper、FilterRBAC
      
      前端：Thymeleaf、Bootstarp、BootstarpTable、BootstarpTree、jsTree、Layer
      
      账号密码：admin/123
      
      
  ## 02.springboot-mvc-dubbo (dubbo分布式版)
  采用的是最简单的权限控制，filter的RBAC，功能包含 登录、菜单、角色、用户、权限,后端密码采用MD5加密
  
  相应技术栈：
      
      分布式：Zookeeper为注册中心、Dubbo为管控中心
            目前为二层结构：
                1.provide 提供各项服务
                2.customer 服务调取方、权限控制方、前端
      
      后端：Dubbo、Zookeeper、SpringBoot、Mybatis、PageHelper、FilterRBAC、Swagger
      
      前端：Thymeleaf、Bootstarp、BootstarpTable、BootstarpTree、jsTree、Layer
      
      账号密码：admin/123
      
  ## 02.smartdevelop-admin-dubbo (dubbo分布式版)
  采用的是最简单的权限控制，filter的RBAC，功能包含 登录、菜单、角色、用户、权限,后端密码采用MD5加密
  与上一版本不同的是 经过以前架构的过度，该版本加入maven聚合分布式
  
  相应技术栈：
      
      分布式：Zookeeper为注册中心、Dubbo为管控中心
            目前为二层结构：
                1.provide 提供各项服务
                2.customer 服务调取方、权限控制方、前端
      
      后端：Dubbo、Zookeeper、SpringBoot、Mybatis、PageHelper、FilterRBAC、Swagger
      
      前端：Thymeleaf、Bootstarp、BootstarpTable、BootstarpTree、jsTree、Layer
      
      账号密码：admin/123     
