package com.activiti_demo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class test02 {

	@Test
	public void test() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		engine.getRepositoryService().createDeployment().addClasspathResource("emp.bpmn")
				.addClasspathResource("emp.png").deploy();

	}

	@Test
	public void test02() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "狄仁杰");
		engine.getRuntimeService().startProcessInstanceByKey("emp", map);
	}

	@Test
	// 92501
	public void test03() {
		// 60001
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		List<Task> tasks = engine.getTaskService().createTaskQuery().taskAssignee("武则天二代").list();
		for (Task task : tasks) {
			System.out.println("" + task.getName());
			System.out.println("流程实例ID" + task.getProcessInstanceId());
			System.out.println("任务ID" + task.getId());
		}

	}

	@Test
	public void test06() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		engine.getRepositoryService().deleteDeployment("80001", true);
	}

	// 50001
	// 普通员工 附带 批注
	@Test
	public void test04() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "武则天");
		map.put("role", "staff");
		TaskService taskService = engine.getTaskService();
		taskService.addComment("105004", "100001", "通过", "谁让你救过朕呢！。审核人：武则天");
		taskService.complete("105004", map);
	}

	// 部门经理
	@Test
	public void test044() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "曹操");
		map.put("role", "manager");
		engine.getTaskService().complete("55005", map);
	}

	// 财务
	@Test
	public void test0414() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "思维哥");
		map.put("role", "cashier");
		engine.getTaskService().complete("92505", map);
	}

	// 总经理
	@Test
	public void test04114() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "曹操");
		map.put("role", "generalmanager");
		engine.getTaskService().complete("65005", map);

	}

	// 总经理审核报销单通过
	@Test
	public void test041114() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", "思维哥");
		map.put("rollback", "no");
		engine.getTaskService().complete("95003", map);
	}

	@Test
	public void test011() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessInstance> instances = engine.getRuntimeService().createProcessInstanceQuery().list();
		for (ProcessInstance processInstance : instances) {
			System.out.println(processInstance.getDeploymentId());
			System.out.println(processInstance.getId());
		}
	}

	@Test
	public void test0111() {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		List<HistoricTaskInstance> historicTaskInstances = engine.getHistoryService().createHistoricTaskInstanceQuery()
				.list();
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
			System.out.println(historicTaskInstance.getAssignee());
		}
	}

	//根据流程实例 查看 批注历史记录
	@Test
	public void test1982() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


		List<Comment> comments=   processEngine.getTaskService().getProcessInstanceComments("100001");
	
	
	    for (Comment comment : comments) {
			System.out.println(comment.getFullMessage());
		}
	}
	
	
	//修改审核人
	@Test
	public void test834987()
	{
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		processEngine.getTaskService().setAssignee("105004", "武则天二代");
	}
	
    //查看流程图
	
	
	
}
