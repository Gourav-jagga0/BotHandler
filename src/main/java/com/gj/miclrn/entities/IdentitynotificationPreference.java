package com.gj.miclrn.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IdentitynotificationPreference extends BaseEntity {
    @Column(name = "CHAT_ID")

    private String chatId;

    @OneToMany(targetEntity = TaskProperties.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskProperties> tasks = new ArrayList<>();

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<TaskProperties> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskProperties> tasks) {
        this.tasks = tasks;
    }
    
}