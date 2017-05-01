package com.ubb.mpp.server;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.model.User;
import com.ubb.mpp.motocontest.generated.*;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.server.crud.*;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Marius Adam
 */
@Component
public class MotoContestImpl extends MotoContestGrpc.MotoContestImplBase {
    final private Logger logger = Logger.getLogger(MotoContestImpl.class.getName());
    private EventDispatcher eventDispatcher;
    private UserService userService;
    private ContestantService contestantService;
    private Converter converter;
    private RaceService raceService;
    private TeamService teamService;
    private EngineCapacityService ecService;

    @Autowired
    public MotoContestImpl(EventDispatcher eventDispatcher, UserService userService, ContestantService contestantService, Converter converter, RaceService raceService, TeamService teamService, EngineCapacityService ecService) {
        this.eventDispatcher = eventDispatcher;
        this.userService = userService;
        this.contestantService = contestantService;
        this.converter = converter;
        this.raceService = raceService;
        this.teamService = teamService;
        this.ecService = ecService;
    }

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        eventDispatcher.dispatch(
                Event.newBuilder()
                        .setName(Event.Name.HELLO)
                        .build()
        );
    }

    @Override
    public void sayHelloAgain(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello again " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        eventDispatcher.dispatch(
                Event.newBuilder()
                        .setName(Event.Name.HELLO_AGAIN)
                        .build()
        );
    }

    @Override
    public void subscribe(SubscribeRequest request, StreamObserver<Event> responseObserver) {
        logger.info(request.toString());
        Set<Event.Name> requestSet = new HashSet<>(request.getEventNameList());
        logger.info("Received events :");
        requestSet.forEach(name -> logger.info(name.toString()));
        requestSet.forEach(
                name -> eventDispatcher.addListener(name, responseObserver)
        );
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginReply> responseObserver) {
        logger.info(request.toString());
        LoginReply.Builder replyBuilder = LoginReply.newBuilder();
        try {
            User u = userService.getUser(request.getEmail(), request.getPassword());
            if (u != null) {
                replyBuilder.setStatus(Status.OK);
                replyBuilder.setMessage("User " + u.getEmail() + " logged in.");
            } else {
                replyBuilder.setStatus(Status.UNAUTHORIZED);
                replyBuilder.setMessage("Invalid username or password");
            }
        } catch (RepositoryException e) {
            logger.warning(e.getMessage());
            replyBuilder.setMessage(e.getMessage());
            replyBuilder.setStatus(Status.UNAUTHORIZED);
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getContestants(GetContestantsRequest request, StreamObserver<GetContestantsReply> responseObserver) {
        GetContestantsReply.Builder crb = GetContestantsReply.newBuilder();
        try {
            contestantService.getAll().forEach(
                    contestant -> crb.addContestant(converter.toDto(contestant))
            );
            crb.setStatus(Status.OK);
        } catch (Exception e) {
            crb.setStatus(Status.ERROR);
            crb.setMessage(e.getMessage());
        }

        responseObserver.onNext(crb.build());
        responseObserver.onCompleted();
    }

    @Override
    public void search(SearchContestantsRequest request, StreamObserver<SearchContestantsReply> responseObserver) {
        SearchContestantsReply.Builder scrb = SearchContestantsReply.newBuilder();
        try {
            contestantService.findBy(request.getTeamName()).forEach(
                    contestant -> scrb.addContestant(converter.toDto(contestant))
            );
        } catch (Exception e) {
            scrb.setStatus(Status.ERROR);
            scrb.setMessage(e.getMessage());
        }

        responseObserver.onNext(scrb.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getRaces(Empty request, StreamObserver<GetRacesReply> responseObserver) {
        GetRacesReply.Builder replyBuilder = GetRacesReply.newBuilder();
        try {
            raceService.getAll().forEach(
                    race -> replyBuilder.addRace(converter.toDto(race))
            );
            replyBuilder.setStatus(Status.OK);
        } catch (Exception e) {
            replyBuilder.setStatus(Status.ERROR);
            replyBuilder.setMessage(e.getMessage());
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTeams(Empty request, StreamObserver<GetTeamsReply> responseObserver) {
        GetTeamsReply.Builder replyBuilder = GetTeamsReply.newBuilder();
        try {
            teamService.getAll().forEach(
                    obj -> replyBuilder.addTeam(converter.toDto(obj))
            );
            replyBuilder.setStatus(Status.OK);
        } catch (Exception e) {
            replyBuilder.setStatus(Status.ERROR);
            replyBuilder.setMessage(e.getMessage());
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getEngineCapacities(Empty request, StreamObserver<GetEngineCapacitiesReply> responseObserver) {
        GetEngineCapacitiesReply.Builder replyBuilder = GetEngineCapacitiesReply.newBuilder();
        try {
            ecService.getAll().forEach(
                    obj -> replyBuilder.addEngineCapacity(converter.toDto(obj))
            );
            replyBuilder.setStatus(Status.OK);
        } catch (Exception e) {
            replyBuilder.setStatus(Status.ERROR);
            replyBuilder.setMessage(e.getMessage());
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void addTeam(AddTeamRequest request, StreamObserver<SimpleReply> responseObserver) {
        SimpleReply.Builder replyBuilder = SimpleReply.newBuilder();
        Boolean doDispatch = false;
        try {
            teamService.create(request.getTeamName());
            replyBuilder.setStatus(Status.OK);
            doDispatch = true;
        } catch (Exception e) {
            replyBuilder.setStatus(Status.ERROR);
            replyBuilder.setMessage(e.getMessage());
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
        if (doDispatch) {
            eventDispatcher.dispatch(Event.Name.TEAM_ADDED);
        }
    }

    @Override
    public void registerContestant(RegisterContestantRequest request, StreamObserver<SimpleReply> responseObserver) {
        SimpleReply.Builder replyBuilder = SimpleReply.newBuilder();
        Boolean doDispatch = false;
        try {
            Contestant c = contestantService.create(
                    request.getContestantName(),
                    converter.toPojo(request.getTeam()),
                    converter.toPojo(request.getEngineCapacity())
            );
            raceService.registerContestant(
                    c,
                    request.getRaceList()
                            .stream()
                            .map(
                                    raceDto -> converter.toPojo(raceDto)
                            ).collect(Collectors.toList())
            );

            replyBuilder.setStatus(Status.OK);
            doDispatch = true;
        } catch (Exception e) {
            replyBuilder.setStatus(Status.ERROR);
            replyBuilder.setMessage(e.getMessage() == null ? e.getClass().getName() : e.getMessage());
        }

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
        if (doDispatch) {
            eventDispatcher.dispatch(Event.Name.CONTESTANT_REGISTERED);
        }
    }

    @Override
    public void getRacesParticipants(Empty request, StreamObserver<RaceParticipants> responseObserver) {
        List<Race> races = raceService.getAll();
        races.forEach(race -> {
            try {
                RaceParticipants.Builder replyBuilder = RaceParticipants.newBuilder();
                replyBuilder.setRace(converter.toDto(race));
                replyBuilder.setNumberOfParticipants(raceService.countParticipants(race));
                responseObserver.onNext(replyBuilder.build());
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        });

        responseObserver.onCompleted();
    }
}