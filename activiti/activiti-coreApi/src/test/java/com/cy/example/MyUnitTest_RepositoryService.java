package com.cy.example;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * 流程存储服务
 */
@Slf4j
public class MyUnitTest_RepositoryService {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	/**
	 * 部署流程定义文件
	 */
	@Test
	public void deployTest() {
		RepositoryService repositoryService = activitiRule.getRepositoryService();

		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.name("第一次部署: 一次部署多个");
		deploymentBuilder.addClasspathResource("my-process.bpmn20.xml");
		deploymentBuilder.addClasspathResource("second_approve.bpmn20.xml");
		Deployment deploy1 = deploymentBuilder.deploy();

		DeploymentBuilder deploymentBuilder2 = repositoryService.createDeployment();
		deploymentBuilder2.name("第二次部署: 一次部署多个");
		deploymentBuilder2.addClasspathResource("my-process.bpmn20.xml");
		deploymentBuilder2.addClasspathResource("second_approve.bpmn20.xml");
		Deployment deploy2 = deploymentBuilder2.deploy();

		List<Deployment> deployments = repositoryService
				.createDeploymentQuery()
//				.deploymentId(deploy.getId())
				.orderByDeploymenTime().asc()
				.listPage(0, 100);
		deployments.forEach(deployment -> {
			log.info("deployment ==> [{}]", deployment);
		});
		log.info("deployments size ==> [{}]", deployments.size());

		List<ProcessDefinition> processDefinitions = repositoryService
				.createProcessDefinitionQuery()
//				.deploymentId(deploy.getId())
				.orderByProcessDefinitionKey().asc()
				.listPage(0, 100);
		processDefinitions.forEach(processDefinition -> {
			log.info("processDefinition ==> id: [{}], name: [{}], key: [{}], version: [{}]",
					processDefinition.getId(),
					processDefinition.getName(),
					processDefinition.getKey(),
					processDefinition.getVersion());
		});
	}

	/**
	 * 维护用户和用户组的关系
	 * 	将用户和用户组关联起来
	 */
	@Test
	@org.activiti.engine.test.Deployment(resources = "my-process.bpmn20.xml")
	public void candidateTest(){
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();

		// 添加用户和用户组
		repositoryService.addCandidateStarterUser(processDefinition.getId(), "user");
		repositoryService.addCandidateStarterGroup(processDefinition.getId(), "groupM");

		List<IdentityLink> identityLinks = repositoryService
				.getIdentityLinksForProcessDefinition(processDefinition.getId());
		identityLinks.forEach(identityLink -> {
			log.info("identityLink ==> [{}]", identityLink);
		});
	}

}
