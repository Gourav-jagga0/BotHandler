package com.gj.miclrn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_properties")
public class TaskProperties {
//    @Column(name = "IDENTITIY_ID")
//    long identityid;
    @Column(name = "CONTENT")

    String content;
    @Column(name = "SCHEDULE")

    String schedule;

//    public long getIdentityid() {
//        return identityid;
//    }
//
//    public void setIdentityid(long identityid) {
//        this.identityid = identityid;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

}
