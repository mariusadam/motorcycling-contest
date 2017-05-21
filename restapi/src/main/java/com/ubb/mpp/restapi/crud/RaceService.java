package com.ubb.mpp.restapi.crud;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.persistence.RaceRepository;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.restapi.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Marius Adam
 */
@Component
public class RaceService extends BaseCrudService<Integer, Race>{
    private RaceRepository raceRepository;

    @Autowired
    public RaceService(ValidatorInterface validator, RaceRepository raceRepository) {
        super(validator);
        this.raceRepository = raceRepository;
    }

    public List<String> suggestNames(String text) throws RepositoryException {
        return raceRepository.suggestNames(text);
    }

    public List<Race> findBy(String raceName, List<EngineCapacity> capacities) {
        return raceRepository.findByNameAndCapacities(raceName, capacities);
    }

    @Override
    protected Repository<Integer, Race> getRepository() {
        return raceRepository;
    }

    public void registerContestant(Contestant contestant, Iterable<Race> races) {
        for (Race r : races) {
            raceRepository.registerContestant(contestant, r);
        }
    }

    public int countParticipants(Race race) {
        return raceRepository.countParticipants(race);
    }
}
