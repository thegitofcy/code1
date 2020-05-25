package com.cy.example;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

/**
 * TaskService
 * 主要是操作 UserTask 对象.
 */
@Slf4j
public class MyUnitTest_TaskService {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	/**
	 * 通过 TaskService 获取 task
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void test() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("message", "123456");
		ProcessInstance processInstance = activitiRule
				.getRuntimeService()
				.startProcessInstanceByKey("my-process", variables);
		Task task = activitiRule.getTaskService()
				.createTaskQuery()
				.singleResult();
		log.info("task ==> [{}]", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
		log.info("task descripition ==> [{}]", task.getDescription());
	}

	/**
	 * 参数变量
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void taskVariablesTest(){
		Map<String, Object> message = Maps.newHashMap();
		message.put("message", "the message of task");

		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", message);

		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		log.info("task ==> [{}]", task);
		log.info("task description ==> [{}]", task.getDescription());

		taskService.setVariable(task.getId(), "key1", "value1");
		taskService.setVariableLocal(task.getId(), "key_local", "value_local");

		Map<String, Object> variables = taskService.getVariables(task.getId());
		Map<String, VariableInstance> variableInstancesLocal = taskService.getVariableInstancesLocal(task.getId());
		Map<String, Object> processVar = activitiRule.getRuntimeService().getVariables(task.getExecutionId());

		log.info("variables ==> [{}]", variables);
		log.info("variableInstancesLocal ==> [{}]", variableInstancesLocal);
		log.info("processVar ==> [{}]", processVar);
	}


	/**
	 * 设置 UserTask 的权限信息
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void variablesTest(){
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("key1", "value1");

		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);

		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		log.info("task ==> [{}]", task);
		log.info("task des ==> [{}]", task.getDescription());

		Map<String, Object> variableLocal = Maps.newHashMap();
		variables.put("key1", "value1");

		taskService.setVariable(task.getId(), "key2", "value2");
		taskService.setVariableLocal(task.getId(), "key_local", "value_local");

		Map<String, Object> variables1 = taskService.getVariables(task.getId());
		Map<String, Object> variablesLocal = taskService.getVariablesLocal(task.getId());
		Map<String, Object> variables2 = activitiRule.getRuntimeService().getVariables(task.getExecutionId());

		log.info("task var ==> [{}]", variables1);
		log.info("task varLocal ==> [{}]", variablesLocal);
		log.info("process var ==> [{}]", variables2);
	}

}
