package com.ubb.mpp.server.crud;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.persistence.ContestantRepository;
import com.ubb.mpp.persistence.Repository;
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

    @Autowired
    public ContestantService(ValidatorInterface validator, ContestantRepository contestantRepository) {
        super(validator);
        this.contestantRepository = contestantRepository;
    }

    @Override
    protected Repository<Integer, Contestant> getRepository() {
        return contestantRepository;
    }

    public List<Contestant> findByTeamName(String teamName) {
        return contestantRepository.findByTeamName(teamName);
    }
}
