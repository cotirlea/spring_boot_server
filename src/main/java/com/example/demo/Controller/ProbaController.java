package com.example.demo.Controller;


import com.example.demo.Persistance.RepoProba;
import com.example.demo.Utils.Proba;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;
@CrossOrigin
@RestController
@RequestMapping(path = "/proba")
public class ProbaController {
    private RepoProba repo = new RepoProba();

    @RequestMapping( method = RequestMethod.GET)
    public Vector<Proba> getProbe(){
        return this.repo.getData();
    }

    @RequestMapping(value = "get/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getProba(@PathVariable String name){
        System.out.println("name");
        int index = this.repo.get(name);
        if(index == -1)
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(this.repo.getData().get(index), HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{nume_proba}+{nume_participant}", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipant(@PathVariable String nume_proba,@PathVariable String nume_participant){
        int index = this.repo.get(nume_proba);
        if(index == -1)
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        else{
            this.repo.addParticipant(nume_proba,nume_participant);
            return new ResponseEntity<Vector<Proba>>(this.repo.getData(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/remove/{nume_proba}+{nume_participant}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipant(@PathVariable String nume_proba,@PathVariable String nume_participant){
        int index = this.repo.get(nume_proba);
        if(index == -1)
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        else{
            if(this.repo.findPart(nume_participant,index) == -1)
                return new ResponseEntity<String>("Participant not found", HttpStatus.NOT_FOUND);
            else{
                this.repo.removeParticipant(nume_proba,nume_participant);
                return new ResponseEntity<Vector<Proba>>(this.repo.getData(), HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/update/{nume_proba}+{nume_participant}+{new_participant}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateParticipant(@PathVariable String nume_proba,@PathVariable String nume_participant,@PathVariable String new_participant){
        int index = this.repo.get(nume_proba);
        if(index == -1)
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        else{
            int old = this.repo.findPart(nume_participant,index);
            if(old == -1)
                return new ResponseEntity<String>("Participant not found", HttpStatus.NOT_FOUND);
            else{
                this.repo.removeParticipant(nume_proba,nume_participant);
                this.repo.addParticipant(nume_proba,new_participant);
                return new ResponseEntity<Vector<Proba>>(this.repo.getData(), HttpStatus.OK);
            }
        }
    }


}
