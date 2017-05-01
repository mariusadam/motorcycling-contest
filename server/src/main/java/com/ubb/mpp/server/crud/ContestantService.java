package com.ubb.mpp.server.crud;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Team;
import com.ubb.mpp.persistence.ContestantRepository;
import com.ubb.mpp.persistence.EngineCapacityRepository;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.TeamRepository;
import com.ubb.mpp.server.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Marius Adam
 */
@Component
public class ContestantService extends BaseCrudService<Integer, Contestant> {
    private ContestantRepository contestantRepository;
    private TeamRepository teamRepository;
    private EngineCapacityRepository engineCapacityRepository;

    @Autowired
    public ContestantService(ValidatorInterface validator, ContestantRepository contestantRepository, TeamRepository teamRepository, EngineCapacityRepository engineCapacityRepository) {
        super(validator);
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
        this.engineCapacityRepository = engineCapacityRepository;
    }

    @Override
    protected Repository<Integer, Contestant> getRepository() {
        return contestantRepository;
    }

    public List<Contestant> findBy(String teamName) {
        return contestantRepository.findBy(teamName);
    }

    public Contestant create(String contestantName, Team team, EngineCapacity ec) {
        Contestant contestant = new Contestant();
        contestant.setName(contestantName);
        contestant.setTeam(team);
        contestant.setEngineCapacity(ec);
        validator.validate(contestant);
        contestantRepository.insert(contestant);

        return contestantRepository.findOneBy("name", contestant.getName());
    }
}
