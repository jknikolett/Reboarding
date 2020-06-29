package com.grape.reboarding.boarding.service;

import com.grape.reboarding.boarding.dto.Chair;
import com.grape.reboarding.boarding.dto.ChairStatus;
import com.grape.reboarding.boarding.dto.Desk;
import com.grape.reboarding.boarding.entity.Register;
import com.grape.reboarding.boarding.entity.RegisterStatus;
import com.grape.reboarding.boarding.repository.RegisterRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
public class OfficeService {

    public static final int LEVEL_SIZE = 10;
    public static final Chair EMPTY_CHAIR = Chair.builder().position(new Point()).build();

    @Autowired
    private GsonService gsonService;

    @Autowired
    private RegisterRepository registerRepository;

    @Value("${reboarding.level}")
    private Integer level;

    public Chair getEmptyChair(LocalDate registerDate) {
        List<Desk> desks = getDesks();
        List<Register> registers = registerRepository.findByRegisterDateAndXGreaterThanAndYGreaterThanAndStatusInOrderByPosition(registerDate,0,0, Arrays.asList(RegisterStatus.QUEUED, RegisterStatus.ACCEPTED));
        return desks.stream()
                .filter(Desk::isAvailable)
                .peek(d->markReservation(d,registers))
                .peek(d->markUnBookable(d,desks))
                .flatMap(d->d.getChairs().stream())
                .filter(Chair::isBookable)
                .findFirst()
                .orElse(EMPTY_CHAIR);
    }

    private List<Desk> getDesks() {
        List<Desk> desks = gsonService.getOffice();
        setBookable(desks);
        return desks;
    }

    private void setBookable(List<Desk> desks){
        desks.stream().forEach(d->{
            d.getChairs().stream().forEach(c->c.setStatus(ChairStatus.BOOKABLE));
        });
    }

    private void markReservation(Desk desk, List<Register> registers){
        registers.stream()
                .map(Register::getChairPosition)
                .forEach(desk::markReservation);
    }

    private void markUnBookable(Desk desk, List<Desk> desks){
        if(desk.isAvailable()) {
            List<Chair> relatedChairs = getRelatedBookableChairs(desk, desks);
            desk.getReservedChairs().forEach(c -> markUnBookable(c, relatedChairs));
        }
    }

    private List<Chair> getRelatedBookableChairs(Desk desk, List<Desk> desks){
        List<Integer> relatedDesks = new ArrayList<>(desk.getRelatedDesks());
        relatedDesks.add(desk.getId());
        return desks.stream()
                .filter(d->relatedDesks.contains(d.getId()))
                .flatMap(d->d.getChairs().stream())
                .filter(Chair::isBookable)
                .collect(Collectors.toList());
    }

    private void markUnBookable(Chair chair, List<Chair> relatedChairs){
        relatedChairs.stream()
                .filter(c->chair.isInRange(c, level * LEVEL_SIZE))
                .forEach(c->c.setStatus(ChairStatus.UN_BOOKABLE));
    }

    public List<Desk> getLayout(LocalDate registerDate) {
        List<Desk> desks = getDesks();
        List<Point> queued = mapPoints(registerRepository.findByRegisterDateAndStatus(registerDate, RegisterStatus.QUEUED));
        List<Point> accepted = mapPoints(registerRepository.findByRegisterDateAndStatus(registerDate, RegisterStatus.ACCEPTED));
        return desks.stream()
                .peek(this::markUnavailable)
                .peek(d->this.markAccepted(d,accepted))
                .peek(d->this.markQueued(d,queued))
                .peek(d->markUnBookable(d,desks))
                .collect(Collectors.toList());
    }

    private List<Point> mapPoints(List<Register> registers){
        return registers.stream().map(r->new Point(r.getX(), r.getY())).collect(Collectors.toList());
    }

    private void markUnavailable(Desk desk){
        if(!desk.isAvailable()){
            desk.getChairs().forEach(c->c.setStatus(ChairStatus.UN_BOOKABLE));
        }
    }

    private void markAccepted(Desk desk, List<Point> accepted){
        if(desk.isAvailable()){
            desk.getChairs().forEach(c->{
                if(c.getStatus() != null && accepted.contains(c.getPosition())){
                    c.setStatus(ChairStatus.OCCUPIED);
                }
            });
        }
    }

    private void markQueued(Desk desk, List<Point> queued){
        if(desk.isAvailable()){
            desk.getChairs().forEach(c->{
                if(c.getStatus() != null && queued.contains(c.getPosition())){
                    c.setStatus(ChairStatus.RESERVED);
                }
            });
        }
    }

}
