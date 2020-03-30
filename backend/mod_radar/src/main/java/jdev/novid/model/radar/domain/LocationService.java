package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jdev.novid.common.value.MacAddress;
import jdev.novid.model.domain.User;

public interface LocationService {

    public Location addLocation(User user, BigDecimal lat, BigDecimal lng, MacAddress mac, LocalDateTime collectedAt);

}
