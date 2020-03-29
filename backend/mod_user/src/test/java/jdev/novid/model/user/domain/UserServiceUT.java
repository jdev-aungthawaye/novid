package jdev.novid.model.user.domain;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.util.EnvAwareUnitTest;
import jdev.novid.foundation.config.ApplicationConfiguration;
import jdev.novid.model.domain.UserService;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceUT extends EnvAwareUnitTest {

    
    @Autowired
    private UserService userService;
    
    @Test
    public void test_createUser() throws MobileAlreadyTakenException {
        
        this.userService.createUser(new Mobile("09777773363"), "Aung Thaw Aye", "763714");
    }
}
