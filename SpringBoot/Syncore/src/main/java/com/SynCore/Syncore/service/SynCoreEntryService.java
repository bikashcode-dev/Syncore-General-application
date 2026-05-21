package com.SynCore.Syncore.service;
import com.SynCore.Syncore.entity.SynCoreEntry;
import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.repository.SynCoreEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SynCoreEntryService {
    @Autowired
    private SynCoreEntryRepository synCoreEntryRepository;
    @Autowired
    private UserService userEntryService;

    @Transactional
    public void saveEntry(SynCoreEntry synCoreEntry, String userName) {
        try {
            User user =  userEntryService.findByUsername(userName);
            synCoreEntry.setDate(LocalDate.now());
            SynCoreEntry saved = synCoreEntryRepository.save(synCoreEntry);
            user.getSynCoreEntryList().add(saved);
            userEntryService.saveUser(user);
        }
        catch (Exception e) {
            log.error("Error while saving SynCoreEntry", e);
            throw new RuntimeException("Error Saving SynCoreEntry");
        }
    }
    public void saveEntry(SynCoreEntry synCoreEntry) {
        synCoreEntryRepository.save(synCoreEntry);
    }
    public List<SynCoreEntry> getEntryAll() {
        return synCoreEntryRepository.findAll();
    }

    public Optional<SynCoreEntry> getEntryById(ObjectId id) {
        return synCoreEntryRepository.findById(id);
    }

    @Transactional
    public void deleteEntryById(ObjectId myid,String userName) {
        try {
            User user = userEntryService.findByUsername(userName);
             boolean remove = user.getSynCoreEntryList().removeIf(x -> x.getId().equals(myid));
            if (remove) {
                userEntryService.saveUser(user);
                synCoreEntryRepository.deleteById(myid);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error Deleting SynCoreEntry");
        }
    }
}


// controller --> service --> repository --> monogoDB