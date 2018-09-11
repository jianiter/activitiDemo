# activitiDemo
activiti的使用

1、配置pom.xml，引入activiti，mysql相关资源。

    <!---Activiti依赖导入-->
    <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-spring</artifactId>
      <version>5.18.0</version>
    </dependency>
    <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-engine</artifactId>
      <version>5.18.0</version>
    </dependency>
    <!--MySQL 驱动包，如果是其他库的话需要换驱动包-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.35</version>
    </dependency>
    
2、配置activiti.cfg.xml,数据库连接源，用于初始化生成表。

    <bean id="processEngineConfiguration" class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
        <property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/activiti" />
        <property name="jdbcDriver" value="com.mysql.jdbc.Driver" />
        <property name="jdbcUsername" value="admin" />
        <property name="jdbcPassword" value="123456" />
        <property name="databaseSchemaUpdate" value="true" />
    </bean>

3、配置bpmn,添加task等。

4、编写用例测试，执行task。

