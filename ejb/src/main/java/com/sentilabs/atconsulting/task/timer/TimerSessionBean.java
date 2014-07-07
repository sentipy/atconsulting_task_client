package com.sentilabs.atconsulting.task.timer;

import com.sentilabs.atconsulting.task.entity.Atct2Entity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sentipy on 07/07/14.
 */
@Singleton
@Startup
public class TimerSessionBean {

    @Resource
    TimerService timerService;

    @PersistenceContext(name = "persistenceUnit")
    EntityManager em;

    private class Atct1Entity {
        private int column1;
        private String column2;

        public int getColumn1() {
            return column1;
        }

        public void setColumn1(int column1) {
            this.column1 = column1;
        }

        public String getColumn2() {
            return column2;
        }

        public void setColumn2(String column2) {
            this.column2 = column2;
        }
    }

    private List<Atct1Entity> getAll(){
        List<Atct1Entity> atct1Entities = new ArrayList<>();
        Client client = ClientBuilder.newClient().register(JsonArray.class);
        JsonArray jsonArray = client.target("http://localhost:8080/atct-ws/api/get/all").request(MediaType.APPLICATION_JSON)
                .get(JsonArray.class);
        for (int i = 0; i < jsonArray.size(); ++i){
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            Atct1Entity atct1Entity = new Atct1Entity();
            atct1Entity.setColumn1(jsonObject.getInt("c1"));
            atct1Entity.setColumn2(jsonObject.getString("c2"));
            atct1Entities.add(atct1Entity);
        }
        return atct1Entities;
    }

    public void setTimer(long intervalDuration) {
        Timer timer = timerService.createTimer(intervalDuration,
                "ATCT");
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        System.out.println("Occurred at " + (new Date()));
        List<Atct1Entity> atct1Entities = this.getAll();
        for (Atct1Entity atct1Entity : atct1Entities) {
            Atct2Entity atct2Entity = new Atct2Entity();
            atct2Entity.setColumn1(atct1Entity.getColumn1());
            atct2Entity.setColumn2(atct1Entity.getColumn2());
            atct2Entity.setColumn3(new Date());
            em.merge(atct2Entity);
        }
        this.setTimer(5000);
    }

    @PostConstruct
    public void initialize() {
        if (timerService.getTimers().size() == 0){
            Timer timer = timerService.createTimer(5000, "ATCT");
        }
    }
}
