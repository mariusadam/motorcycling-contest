package com.ubb.mpp.restapi;

import com.ubb.mpp.model.Race;
import com.ubb.mpp.persistence.RaceRepository;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.restapi.crud.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author Marius Adam
 */
@RestController
@RequestMapping("/contest/race")
public class RaceController {
    private RaceService raceService;
    private RaceRepository raceRepository;

    @Autowired
    public RaceController(RaceService raceService, RaceRepository raceRepository) {
        this.raceService = raceService;
        this.raceRepository = raceRepository;
    }

    @RequestMapping(value = "/{raceId}", method = RequestMethod.GET)
    public ResponseEntity<?> race(@PathVariable Integer raceId) {
        try {
            return new ResponseEntity<>(raceRepository.findById(raceId), HttpStatus.OK);
        } catch (NoSuchElementException | RepositoryException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @RequestMapping(value = "/{raceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer raceId) {
        try {
            raceRepository.delete(raceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RepositoryException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Race> getAll() {
        return raceRepository.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Race race) {
        raceRepository.insert(race);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Race update(@RequestBody Race race, @PathVariable Integer id) {
        race.setId(id);
        try {
            raceRepository.update(race);
            return raceRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ResourceNotFoundException(e);
        }
    }
}
