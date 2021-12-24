# 学生信息查询管理系统（管理部分）
## Author: RoyMikeJiang
### 如何复现该项目：
Step1. 在本机或服务器中部署一个MYSQL数据库服务器，最好为MYSQL8
<br> Step2. 利用MYSQL控制台或者DBeaver等数据库管理软件执行项目中创建一个数据库，记住该名字
<br> Step3. 在新建立的数据库中执行database.sql文件，从而构建数据库结构
<br> Step4. 在userid中添加一列用户记录，同时设置密码
<br> Step5. 安装Maven、Java8 jdk（非jre），并配置完成
<br> Step6. 用IDEA或其他IDE打开本项目
<br> Step7. 检测Maven要求的库是否已安装
<br> Step8. 修改项目中src/main/java/utils/StaticResourcesConfig/StaticResourcesConfig类中的MYSQL参数部分为自己数据库的参
<br> Step9. 在IDE中通过ViewAlter启动程序