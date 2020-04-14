package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UnitFacade {

    private UserService userService;

    private UnitService unitService;

    @Autowired
    public UnitFacade(UserService userService, UnitService unitService){
        this.userService = userService;
        this.unitService = unitService;
    }

    public Unit createUnit(UUID userUuid, Unit unit) {
        log.info("event=createUnitFacadeInvoked, userUuid={}, unit={}", userUuid, unit);
        Unit unitSaved = unitService.saveOrUpdateUnit(unit);
        log.info("event=unitSaved, unitSaved={}", unitSaved);
        User user = userService.findUser(userUuid);
        log.info("event=userFound, user={}", user);
        user.setUnit(unitSaved);
        userService.saveOrUpdateUser(userUuid, user);
        log.info("event=userSaved, user={}", user);
        return unitSaved;
    }

    public Unit createUnitReference(UUID userId, Long unitId, Map<String, String> unit){
        log.info("event=createUnitReferenceInvoked, userId={}, unitId={} unit={}", userId, unitId, unit);
        User user = userService.findUser(userId);
        Hibernate.initialize(user.getUnit());
        if (user.getUnit().getId() == unitId){
            return unitService.setUnitReference(unitId, unit);
        }
        throw new RuntimeException("No pertenece a la casa");
    }
}
