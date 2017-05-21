package com.ubb.mpp.server;

import com.ubb.mpp.model.*;
import com.ubb.mpp.motocontest.generated.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Marius Adam
 */
@Component
public class Converter {
    public User toPojo(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public Contestant toPojo(ContestantDto contestantDto) {
        Contestant contestant = new Contestant();
        contestant.setId(contestantDto.getId());
        contestant.setName(contestantDto.getName());
        contestant.setEngineCapacity(toPojo(contestantDto.getEngineCapacity()));
        contestant.setTeam(toPojo(contestantDto.getTeam()));

        return contestant;
    }

    public Team toPojo(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());

        return team;
    }

    public EngineCapacity toPojo(EngineCapacityDto ecDto) {
        EngineCapacity ec = new EngineCapacity();
        ec.setId(ecDto.getId());
        ec.setUnitOfMeasurement(toPojo(ecDto.getUm()));
        ec.setCapacity(ecDto.getCapacity());

        return ec;
    }

    public EngineCapacity.UM toPojo(EngineCapacityDto.UM umDto) {
        switch (umDto) {
            case MC:
                return EngineCapacity.UM.MC;
            case CC:
                return EngineCapacity.UM.CC;
            default:
                throw new RuntimeException("Invalid unit " + umDto);
        }
    }

    public Race toPojo(RaceDto raceDto) {
        Race race = new Race();
        race.setId(raceDto.getId());
        race.setName(raceDto.getName());
        race.setStartTime(new Date(raceDto.getStartTime()));

        return race;
    }

    public UserDto toDto(User user) {
        UserDto.Builder userBuilder = UserDto.newBuilder();

        return UserDto
                .newBuilder()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .build();
    }

    public ContestantDto toDto(Contestant contestant) {
        return ContestantDto
                .newBuilder()
                .setId(contestant.getId())
                .setName(contestant.getName())
                .setTeam(toDto(contestant.getTeam()))
                .setEngineCapacity(toDto(contestant.getEngineCapacity()))
                .build();
    }

    public EngineCapacityDto toDto(EngineCapacity engineCapacity) {
        return EngineCapacityDto
                .newBuilder()
                .setId(engineCapacity.getId())
                .setCapacity(engineCapacity.getCapacity())
                .setUm(toDto(engineCapacity.getUnitOfMeasurement()))
                .build();
    }

    private EngineCapacityDto.UM toDto(EngineCapacity.UM um) {
        switch (um) {
            case MC:
                return EngineCapacityDto.UM.MC;
            case CC:
                return EngineCapacityDto.UM.CC;
            default:
                throw new RuntimeException("Invalid unit " + um);
        }
    }

    public TeamDto toDto(Team team) {
        return TeamDto
                .newBuilder()
                .setId(team.getId())
                .setName(team.getName())
                .build();
    }

    public RaceDto toDto(Race race) {
        return RaceDto
                .newBuilder()
                .setId(race.getId())
                .setName(race.getName())
                .setStartTime(race.getStartTime().getTime())
                .build();
    }

//    public com.ubb.mpp.motocontest.generated.Event.Name toDto(Event event) {
//        switch (event) {
//            case ContestantRegistered:
//                return com.ubb.mpp.motocontest.generated.Event.Name.CONTESTANT_REGISTERED;
//            case TeamAdded:
//                return com.ubb.mpp.motocontest.generated.Event.Name.TEAM_ADDED;
//            default:
//                throw new RuntimeException("Missing switch case");
//        }
//    }
//
//    public Event toPojo(com.ubb.mpp.motocontest.generated.Event.Name eventDto) {
//        switch (eventDto) {
//
//            case CONTESTANT_REGISTERED:
//                return Event.ContestantRegistered;
//            case TEAM_ADDED:
//                return Event.TeamAdded;
//            case HELLO:
//            case HELLO_AGAIN:
//            case UNRECOGNIZED:
//            default:
//                throw new RuntimeException("Missing switch branch");
//        }
//    }
}
