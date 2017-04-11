package com.ubb.mpp.services.crud;

import com.ubb.mpp.model.Team;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.TeamRepository;
import com.ubb.mpp.services.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Marius Adam
 */
@Component
public class TeamService extends BaseCrudService<Integer, Team> {
    private TeamRepository teamRepository;

    @Autowired
    public TeamService(ValidatorInterface validator, TeamRepository teamRepository) {
        super(validator);
        this.teamRepository = teamRepository;
    }

    @Override
    protected Repository<Integer, Team> getRepository() {
        return teamRepository;
    }

    public List<String> suggestNames(String text) {
        return teamRepository.suggestNames(text);
    }
}
