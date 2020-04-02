package jdev.novid.model.radar.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.User;
import jdev.novid.model.query.UserQuery;
import jdev.novid.model.radar.domain.RadarService;

@Service
public class UpdateLocationBean implements UpdateLocation {

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private RadarService radarService;

    @Transactional
    public Output execute(Input input) {

        User user = this.userQuery.getUser(input.getUserId());

        this.radarService.addLocation(user, input.lat, input.lng, input.collectedAt);

        return new Output(Result.SUCCESS);

    }

}
