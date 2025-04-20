package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import util.Ability;

public class User implements Ability {
    private String userId;
    private String userName;
    private int maxHealth;
    public User() {
    }

    public User(String userId, String userName, int maxHealth) {
        this.userId = userId;
        this.userName = userName;
        this.maxHealth = maxHealth;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @JsonIgnore
    @Override
    public int health() {
        return maxHealth;
    }

    @JsonIgnore
    @Override
    public int orbsFound() {
        return maxHealth+10;
    }

    @JsonIgnore
    @Override
    public int powerEarned() {
        return maxHealth+100;
    }
}
