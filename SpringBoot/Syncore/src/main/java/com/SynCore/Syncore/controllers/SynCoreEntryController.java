package com.SynCore.Syncore.controllers;
import com.SynCore.Syncore.entity.SynCoreEntry;
import com.SynCore.Syncore.entity.User;
import com.SynCore.Syncore.service.SynCoreEntryService;
import com.SynCore.Syncore.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/SynCore")
public class SynCoreEntryController {

    @Autowired
    private SynCoreEntryService synCoreEntryService;

    @Autowired
    private UserService userEntryService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryService.findByUsername(userName);
        List<SynCoreEntry> SynCoreAllEntry = user.getSynCoreEntryList();
        if( SynCoreAllEntry != null && !SynCoreAllEntry.isEmpty() ) {
          return new ResponseEntity<>(SynCoreAllEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(SynCoreAllEntry, HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<SynCoreEntry> createEntry(@RequestBody SynCoreEntry myEntry, @PathVariable String userName)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            synCoreEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

   @GetMapping("id/{myId}")
   public ResponseEntity<SynCoreEntry> getSynCoreEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

         User user = userEntryService.findByUsername(username);
         List<SynCoreEntry> collect = user.getSynCoreEntryList().stream().filter(entry -> entry.getId().equals(myId)).collect(Collectors.toList());
         if(!collect.isEmpty()) {
             Optional<SynCoreEntry> synCoreEntry = synCoreEntryService.getEntryById(myId);
             if(synCoreEntry.isPresent()) {
                 return new ResponseEntity<>(synCoreEntry.get(), HttpStatus.OK);
             }
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

    @DeleteMapping("/{myid}")
    public ResponseEntity<?> deleteSynCoreEntryById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        synCoreEntryService.deleteEntryById(myid,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<SynCoreEntry> updateSynCoreEntryBYId(@PathVariable ObjectId id, @RequestBody SynCoreEntry newEntry, @PathVariable String userName){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userEntryService.findByUsername(username);
        List<SynCoreEntry> SynCoreAllEntry = user.getSynCoreEntryList().stream().filter(entry -> entry.getId().equals(id)).collect(Collectors.toList());
        if(!SynCoreAllEntry.isEmpty()) {
            Optional<SynCoreEntry> synCoreEntry = synCoreEntryService.getEntryById(id);
            if(synCoreEntry.isPresent()) {
                SynCoreEntry oldEntry = synCoreEntry.get();
                oldEntry.setDescription(newEntry.getDescription() != null ? newEntry.getDescription() : oldEntry.getDescription());
                oldEntry.setTitle(newEntry.getTitle());
                synCoreEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}