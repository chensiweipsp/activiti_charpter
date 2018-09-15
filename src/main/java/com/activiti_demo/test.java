package com.activiti_demo;

import static org.junit.Assert.*;
import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.Task;
import org.junit.Test;


//分支一

public class test {
	@Test
	public void test() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService().createDeployment().name("emp")
				.addClasspathResource("emp.png").addClasspathResource("emp.bpmn").deploy();
	}

	// 12501
	@Test
	public void test2() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService().deleteDeployment("47501", true);
	}

	@Test
	public void test3() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService().startProcessInstanceByKey("myfirstProcess");
	}

	@Test
	public void test4() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("狄仁杰").list();

		for (Task task : tasks) {
			System.out.println(task.getAssignee());
			System.err.println(task.getProcessInstanceId());
			System.out.println(task.getProcessDefinitionId());
			System.out.println(task.getId());
		}
	}
	// 17504
	@Test
	public void test6() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService().complete("35002");
	}
	
	
	// 17504
	@Test
	public void test15() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<ProcessInstance> processInstances=	processEngine.getRuntimeService().createProcessInstanceQuery().
		list();
		for (ProcessInstance processInstance : processInstances) {
			System.out.println(processInstance.getName());
			System.out.println(processInstance.getDeploymentId());
		}
	}
	
	
	

}
