package com.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("workflowAction.do")
public class ActivitiController {

	@RequestMapping(params="method=geStatus")
	public String geStatus(String taskid,ModelMap modelmap)
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		Task task=	processEngine.getTaskService().createTaskQuery().taskId(taskid)
				.singleResult();

		String processInstanceId=  task.getProcessInstanceId();
		String processDefinitionId=  task.getProcessDefinitionId();

		ProcessInstance processInstance=processEngine.getRuntimeService().createProcessInstanceQuery().
				processInstanceId(processInstanceId).singleResult();

		String activityId =processInstance.getActivityId();

		ProcessDefinition  processDefinition =processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);
		String deploymentId = processDefinition.getDeploymentId();
		//这里很容易写错
		//String resourceName=processDefinition.getResourceName();
		String resourceName=processDefinition.getDiagramResourceName();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) processDefinition;

		ActivityImpl  activityImpl=	definitionEntity.findActivity(activityId);
		
		
		modelmap.addAttribute("x", activityImpl.getX());
		modelmap.addAttribute("y", activityImpl.getY());
		modelmap.addAttribute("width", activityImpl.getWidth());
		modelmap.addAttribute("height", activityImpl.getHeight());
		modelmap.addAttribute("deploymentId", deploymentId);
		modelmap.addAttribute("imageName", resourceName);

		return "image";
	}
	
	
	@RequestMapping(params="method=getImage")
	public void getImage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException
	{

		String deploymentId= httpServletRequest.getParameter("deploymentId");
		String imageName = httpServletRequest.getParameter("imageName");
		
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();

		InputStream inputStream= processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);

		OutputStream outputStream = httpServletResponse.getOutputStream();
		byte[] bytes= new byte[1024];
		int i = 0;
		while(( i = inputStream.read(bytes))>0)
		{
			outputStream.write(bytes,0, i);
		}
		
	}
}
