package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.util.EnvAwareUnitTest;
import jdev.novid.foundation.config.ApplicationConfiguration;
import jdev.novid.model.domain.User;
import jdev.novid.model.query.UserQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RadarServiceUT extends EnvAwareUnitTest {

    @Autowired
    private RadarService radarService;

    @Autowired
    private UserQuery userQuery;

    @Test
    public void test_addLocation() {

        User user = this.userQuery.getUser(new UserId(666577688518656L));

        this.radarService.addDevice(user, new MacAddress("SELF"), new MacAddress("NEARBY"), "NearByDevice",
                new BigDecimal(1), new BigDecimal(1), LocalDateTime.now());

    }

}
