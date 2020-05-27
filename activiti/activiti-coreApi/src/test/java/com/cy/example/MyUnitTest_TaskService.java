package com.cy.example;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.*;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskService
 * 主要是操作 UserTask 对象.
 * 	1. 获取 UserTask
 * 	2. 参数变量设置和获取
 * 	3. UserTask 权限信息(owner, assignee)
 * 	4. 任务附件, 评论, event 等
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

		HashMap<String, Object> competeVar = Maps.newHashMap();
		competeVar.put("completeKey", "completeValue");
		taskService.complete(task.getId(), competeVar);

		Task task1 = taskService.createTaskQuery().taskId(task.getId()).singleResult();
		log.info("task1 ==> [{}]", task1);
	}


	/**
	 * 设置 UserTask 的权限信息
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void taskUserTest(){
		Map<String, Object> message = Maps.newHashMap();
		message.put("message", "the description of task");
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", message);

		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		log.info("task ==> [{}]", task);
		log.info("task description ==> [{}]", task.getDescription());

		taskService.setOwner(task.getId(), "user1");	// 指定拥有人
//		taskService.claim(task.getId(), "user1");

		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateUser("cy") // 查询指定参数为候选人
				.taskUnassigned() // 但是没有指定代办人
				.listPage(0, 100);
		tasks.forEach(taskEntity -> {
			log.info("task candidateUser is cy, and unAssigned ==> [{}]", taskEntity);
			taskService.claim(taskEntity.getId(), "cy");	// 设置办理人
		});

		List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());	// 获取关系
		identityLinksForTask.forEach(identityLink -> {
			log.info("identityLink ==> [{}]", identityLink);
		});

		List<Task> cys = taskService.createTaskQuery()
				.taskAssignee("cy")	// 查询办理人为 cy 的任务
				.listPage(0, 100);
		cys.forEach(cyTask -> {
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("ccKey", "ccValue");
			taskService.complete(cyTask.getId());	// 将查询出的办理人为 cy 的任务提交
			log.info("提交任务 ==> [{}]", cyTask.getId());
		});

		List<Task> cyTasks = taskService.createTaskQuery()
				.taskAssignee("cy")	// 查询办理人为 cy任务
				.listPage(0, 100);
		log.info("是否已经不存在 [{}]", CollectionUtils.isEmpty(cyTasks));	// 查看这些任务是否还存在.
	}


	/**
	 * TaskService 设置 Task 的附件信息: attachment 附件, comment评论 , event事件等
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void attachmentTest() {
		Map<String, Object> message = Maps.newHashMap();
		message.put("message", "the message of task");
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", message);

		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		log.info("task ==> [{}]", task);
		log.info("task description ==> [{}]", task.getDescription());

		// 创建一个 task 附件
		taskService.createAttachment("url",
				task.getId(),
				task.getProcessInstanceId(),
				"attachmentName",
				"attachMent desc",
				"/user/path/test.png");

		// 查询任务附件
		List<Attachment> taskAttachments = taskService.getTaskAttachments(task.getId());
		taskAttachments.forEach(attachment -> {
			log.info("attachment ==> [{}]", ToStringBuilder.reflectionToString(attachment, ToStringStyle.JSON_STYLE));
		});

		// 查询 event
		List<Event> taskEvents = taskService.getTaskEvents(task.getId());
		taskEvents.forEach(taskEvent -> {
			log.info("taskEvent ==> [{}]", ToStringBuilder.reflectionToString(taskEvent, ToStringStyle.JSON_STYLE));
		});

	}

	/**
	 * TaskService 设置 Task 的附件信息: attachment 附件, comment评论 , event事件等
	 */
	@Test
	@Deployment(resources = {"my-process-task.bpmn20.xml"})
	public void commentTest() {
		Map<String, Object> message = Maps.newHashMap();
		message.put("message", "the message of task");
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", message);

		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		log.info("task ==> [{}]", task);
		log.info("task description ==> [{}]", task.getDescription());

		// 创建一个 task 评论.
		taskService.addComment(task.getId(),
				task.getProcessInstanceId(),
				"task comment 1");
		taskService.addComment(task.getId(),
				task.getProcessInstanceId(),
				"task comment 2");

		// 查询任务评论
		List<Comment> taskComments = taskService.getTaskComments(task.getId());
		taskComments.forEach(comment -> {
			log.info("comment ==> [{}]", ToStringBuilder.reflectionToString(comment, ToStringStyle.JSON_STYLE));
		});

		// 查询 event
		List<Event> taskEvents = taskService.getTaskEvents(task.getId());
		taskEvents.forEach(taskEvent -> {
			log.info("taskEvent ==> [{}]", ToStringBuilder.reflectionToString(taskEvent, ToStringStyle.JSON_STYLE));
		});
	}
}
