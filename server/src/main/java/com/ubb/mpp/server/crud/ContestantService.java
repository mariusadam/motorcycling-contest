package com.ubb.mpp.server.crud;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.persistence.ContestantRepository;
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

    @Autowired
    public ContestantService(ValidatorInterface validator, ContestantRepository contestantRepository, TeamRepository teamRepository) {
        super(validator);
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    protected Repository<Integer, Contestant> getRepository() {
        return contestantRepository;
    }

    public List<Contestant> findBy(String teamName) {
        return contestantRepository.findByTeamName(teamName);
    }

    public Contestant create(String cname, String tname) {
        Contestant contestant = new Contestant();
        contestant.setName(cname);
        contestant.setTeam(teamRepository.findOneBy("name", tname));
        validator.validate(contestant);
        contestantRepository.insert(contestant);

        return contestantRepository.findOneBy("name", contestant.getName());
    }
}
