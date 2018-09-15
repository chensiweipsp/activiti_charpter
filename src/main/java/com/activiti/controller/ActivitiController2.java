package com.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
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
@RequestMapping("workflowAction2.do")
public class ActivitiController2 {
	//30004
	@RequestMapping(params="method=geStatus")
	public String geStatus(String taskid,ModelMap modelmap)
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		Task task=processEngine.getTaskService().createTaskQuery().taskId(taskid).singleResult();
		String InstanceId =task.getProcessInstanceId();
		String DefinitionId= task.getProcessDefinitionId();

		ProcessInstance processInstance=processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(InstanceId).singleResult();
		ProcessDefinition processDefinition =processEngine.getRepositoryService().getProcessDefinition(DefinitionId);

		String DeploymentId= processInstance.getDeploymentId();
		String ResourceName  = processDefinition.getDiagramResourceName();
		String ActivityId =processInstance.getActivityId();
		ProcessDefinitionEntity definitionEntity= (ProcessDefinitionEntity) (processDefinition);
		ActivityImpl activityImpl  =definitionEntity.findActivity(ActivityId);

		modelmap.addAttribute("deploymentId", DeploymentId);
		modelmap.addAttribute("imageName", ResourceName);
		modelmap.addAttribute("width", activityImpl.getWidth());
		modelmap.addAttribute("height", activityImpl.getHeight());
		modelmap.addAttribute("x", activityImpl.getX());
		modelmap.addAttribute("y", activityImpl.getY());

		return "image";
	}

	//30004
	@RequestMapping(params="method=getImage")
	public void getImage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException
	{
		String deploymentId=	httpServletRequest.getParameter("deploymentId");
		String resourceName=	httpServletRequest.getParameter("imageName");
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		InputStream inputStream= processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		ServletOutputStream  OutputStream = httpServletResponse.getOutputStream();
		byte[] bytes = new byte[1024];
		int temp=0;
		while((temp = inputStream.read(bytes))>0 )
		{
			OutputStream.write(bytes);
		}

	}
}
