package com.chale.wolf.role;

/**
 * Created by liangchaolei on 2017/8/14.
 */
public abstract class Roler {

    private String roleName;

    private User user;

    private boolean dead = false;

    public Roler(String roleName) {
        this.roleName = roleName;
    }

    public boolean isDead() {
        return dead;
    }

    public void dead() {
        this.dead = true;
    }

    public String getRoleName() {
        return roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
