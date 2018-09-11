package activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.Date;


public class ActivitiDemoTest {
    /**
     * 会默认按照Resources目录下的activiti.cfg.xml创建流程引擎
     */
    ProcessEngine processEngine = null;//ProcessEngines.getDefaultProcessEngine();
    String cfgName ="activiti.cfg.xml";
    String taskId = "";
    String assignee = "employee";
    String process = "myProcess_1";
    String deployName = "activitiDemo.bpmn";
    Logger logger = Logger.getLogger(ActivitiDemoTest.class.getName());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");


    @Test
    public void test() {
        //以下两种方式选择一种创建引擎方式：1.配置写在程序里 2.读对应的配置文件
        //1
//        testCreateProcessEngine();
        //2
        testCreateProcessEngineByCfgXml();

        logger.info("start-------->"+sdf.format(new Date()));

        deployProcess();
        startProcess();

        queryExecTask();

        logger.info("end-------->"+sdf.format(new Date()));

//        handleTask();

    }

    /**
     * 测试activiti环境
     */
    @Test
    public void testCreateProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        cfg.setJdbcDriver("com.mysql.jdbc.Driver");
        cfg.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti");
        cfg.setJdbcUsername("admin");
        cfg.setJdbcPassword("123456");
        //配置建表策略
        cfg.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = cfg.buildProcessEngine();
    }

    /**
     * 根据配置文件activiti.cfg.xml创建ProcessEngine
     */
    @Test
    public void testCreateProcessEngineByCfgXml() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(cfgName);
        processEngine = cfg.buildProcessEngine();
        logger.info("根据配置文件"+cfgName+"创建ProcessEngine成功");
    }

    /**
     * 发布流程
     * RepositoryService
     */
    @Test
    public void deployProcess() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource(deployName);
        builder.deploy();
        logger.info("发布流程"+deployName+"成功");
    }

    /**
     * 启动流程
     * RuntimeService
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //可根据id、key、message启动流程
        runtimeService.startProcessInstanceByKey(process);
        logger.info("启动流程"+process+"成功");
    }

    /**
     * 查看并执行任务
     * TaskService
     */
    @Test
    public void queryExecTask() {
        TaskService taskService = processEngine.getTaskService();
        //根据assignee(代理人)查询任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        Calendar calendar = Calendar.getInstance();
        for (Task task : tasks) {
            taskId= task.getId();
            calendar.setTime(task.getCreateTime());
            logger.info("查看任务：taskId:" + taskId +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + sdf.format(calendar.getTime()));

            handleTask();
        }
    }

    /**
     * 执行任务
     */
    @Test
    public void handleTask() {
            TaskService taskService = processEngine.getTaskService();
            taskService.complete(taskId);
            logger.info("执行任务taskId:" + taskId + " complete");

    }
}