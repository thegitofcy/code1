package com.cy.example;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

/**
 * 流程运行控制服务
 */
@Slf4j
public class MyUnitTest_RuntimeService {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	/**
	 * 通过 ProcessInstanceKey 启动流程
	 */
	@Test
	@Deployment(resources = {"my-process.bpmn20.xml"})
	public void startProcessByKeyTest() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("key1", "value1");
		variables.put("key2", "value2");
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("my-process", variables);
		log.info("processInstance ==> [{}]", processInstance);
	}

	/**
	 * 通过 ProcessDefinition Id 启动流程
	 */
	@Test
	@Deployment(resources = {"my-process.bpmn20.xml"})
	public void startProcessByIdTest(){
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();

		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

		log.info("processInstance ==> [{}]", processInstance);
	}


	/**
	 * 通过 processInstanceBuilder 启动流程
	 */
	@Test
	@Deployment(resources = {"my-process.bpmn20.xml"})
	public void startProcessByProcessInstanceBuilderTest(){
		RuntimeService runtimeService = activitiRule.getRuntimeService();

		Map<String, Object> variables = Maps.newHashMap();
		variables.put("key1", "value1");
		variables.put("key2", "value2");
		ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
		ProcessInstance processInstance = processInstanceBuilder.businessKey("")
				.processDefinitionKey("my-process")
				.variables(variables)
				.start();
		log.info("processInstance ==> [{}]", processInstance);
	}

	/**
	 * 向流程添加参数和修改参数
	 */
	@Test
	@Deployment(resources = {"my-process.bpmn20.xml"})
	public void variablesTest(){
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("key1", "value1");
		variables.put("key2", "value2");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", variables);

		Map<String, Object> variables1 = runtimeService.getVariables(processInstance.getId());
		log.info("variables1: [{}]", variables1);

		runtimeService.setVariable(processInstance.getId(), "key3", "value3");
		runtimeService.setVariable(processInstance.getId(), "key2", "value2_1");

		Map<String, Object> variables2 = runtimeService.getVariables(processInstance.getId());
		log.info("variables2: [{}]", variables2);
	}


	/**
	 * trigger 触发 receiveTask
	 * 需要在流程中设置 receiveTask, 类似于 userTask,
	 * 当流程运行到此节点的时候会进行等待, 接收到 trigger 的时候, 会继续向下运行.
	 */
	@Test
	@Deployment(resources = {"my-process-trigger.bpmn20.xml"})
	public void triggerTest() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
		// 获取执行流对象
		Execution execution = runtimeService.createExecutionQuery()
				.activityId("someTask")
				.singleResult();
		log.info("execution ==> [{}]", execution);

		// 触发 receiveTask
		runtimeService.trigger(execution.getId());

		// 再次获取执行流对象, 此时已经执行完, 所以获取到的为 null
		execution = runtimeService.createExecutionQuery()
				.activityId("someTask")
				.singleResult();
		log.info("execution ==> [{}]", execution);
	}

	/**
	 * signalEventReceive
	 *
	 * 涉及:
	 * 	signal : 设置信号
	 * 	intermediateCatching: 边界事件. 当流程运行到这里的时候, 会等待接收 signal 来触发这个边界事件
	 * 当流程运行到信号节点的时候, 会当前等待, 接收到信号后, 会继续向下运行.
	 */
	@Test
	@Deployment(resources = {"my-process-signal-received.bpmn20.xml"})
	public void signalEventReceiveTest(){
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");

		Execution execution = runtimeService
				.createExecutionQuery()
				.signalEventSubscriptionName("my-signal")	// 获取订阅了 my-signal 的流程
				.singleResult();
		log.info("execution ==> [{}]", execution);

		// 发行信号
		runtimeService.signalEventReceived("my-signal");

		execution = runtimeService
				.createExecutionQuery()
				.signalEventSubscriptionName("my-signal")
				.singleResult();
		log.info("execution ==> [{}]", execution); // 当前已执行完, 所以结果为 null.
	}

	/**
	 * messageEventReceive
	 */
	@Test
	@Deployment(resources = {"my-process-message-received.bpmn20.xml"})
	public void messageEventReceiveTest() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");

		Execution execution = runtimeService
				.createExecutionQuery()
				.messageEventSubscriptionName("my-message")
				.singleResult();
		log.info("execution ==> [{}]", execution);

		runtimeService.messageEventReceived("my-message", execution.getId());

		execution = runtimeService
				.createExecutionQuery()
				.messageEventSubscriptionName("my-message")
				.singleResult();
		log.info("execution ==> [{}]", execution);

	}


	@Test
	@Deployment(resources = {"my-process-message-start.bpmn20.xml"})
	public void startProcessByMessage(){
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("my-message");
		log.info("processInstance ==> [{}]", processInstance);
	}

}
