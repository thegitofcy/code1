package com.cy.example;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 表单管理服务
 */
@Slf4j
public class MyUnitTest_FormService {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = {"com/cy/example/my-process.bpmn20.xml"})
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
		assertNotNull(processInstance);

		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("Activiti is awesome!", task.getName());
	}


	@Test
	@Deployment(resources = {"my-process-form.bpmn20.xml"})
	public void formServiceTest(){
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();

		FormService formService = activitiRule.getFormService();

		String startFormKey = formService.getStartFormKey(processDefinition.getId());
		log.info("startFormKey ==> [{}]", startFormKey);

		StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
		List<FormProperty> startFormDataFormProperties = startFormData.getFormProperties();
		startFormDataFormProperties.forEach(formProperty -> {
			log.info("startFormData formPropertie ==> [{}]", toString(formProperty));
		});

		Map<String, String> properties = Maps.newHashMap();
		properties.put("message", "my test message of start");
		properties.put("yesOrNo", "yes");
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), properties);

		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

		TaskFormData taskFormData = formService.getTaskFormData(task.getId());
		List<FormProperty> taskFormDataFormProperties = taskFormData.getFormProperties();
		taskFormDataFormProperties.forEach(formProperty -> {
			log.info("taskFormData formPropertie ==> [{}]", toString(formProperty));
		});

		Map<String, String> taskProperties = Maps.newHashMap();
		taskProperties.put("message", "my test message of task!");
		formService.submitTaskFormData(task.getId(), taskProperties);

		Task task1 = activitiRule.getTaskService().createTaskQuery().singleResult();
		log.info("task 是否存在 [{}]", task1);
	}


	public String toString(Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.JSON_STYLE);
	}

}
