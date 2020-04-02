package jdev.novid.model.radar.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.User;
import jdev.novid.model.domain.exception.UserNotFoundException;
import jdev.novid.model.query.UserQuery;
import jdev.novid.model.radar.domain.RadarService;

@Service
public class UpdateNearByUserBean implements UpdateNearByUser {

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private RadarService radarService;

    @Transactional
    public Output execute(Input input) throws UserNotFoundException {

        User user = this.userQuery.getUser(input.getSourceId());

        Optional<User> optNearByUser = this.userQuery.findUser(input.getNearByUserId());

        if (!optNearByUser.isPresent()) {

            throw new UserNotFoundException();

        }

        this.radarService.addDevice(user, optNearByUser.get(), input.collectedAt);

        return new Output(Result.SUCCESS);

    }

}
