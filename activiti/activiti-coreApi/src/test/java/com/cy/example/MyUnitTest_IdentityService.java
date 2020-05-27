package com.cy.example;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 身份管理服务
 * 	1. 管理用户和用户组的关系
 */
@Slf4j
public class MyUnitTest_IdentityService {

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
	public void run(){
		IdentityService identityService = activitiRule.getIdentityService();

		User user1 = identityService.newUser("user1");
		User user2 = identityService.newUser("user2");
		identityService.saveUser(user1);
		identityService.saveUser(user2);

		Group group1 = identityService.newGroup("group1");
		Group group2 = identityService.newGroup("group2");
		identityService.saveGroup(group1);
		identityService.saveGroup(group2);

		identityService.createMembership("user1", "group1");
		identityService.createMembership("user2", "group1");
		identityService.createMembership("user1", "group2");

		List<User> userList = identityService.createUserQuery().memberOfGroup("group1").listPage(0, 100);
		userList.forEach(user -> {
			log.info("the user in group1 ==> [{}]", toString(user));
		});


		List<Group> groupList = identityService.createGroupQuery().groupMember("user1").listPage(0, 100);
		groupList.forEach(group -> {
			log.info("the group of user1 ==> [{}]", toString(group));
		});
	}

	public String toString(Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.JSON_STYLE);
	}

}
