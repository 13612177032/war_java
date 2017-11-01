package com.chale.wolf.role;

/**
 * Created by liangchaolei on 2017/8/14.
 */
public class Hunter extends Roler implements GodRoler{
    
    public Hunter() {
        super("猎人");
    }


    @Override
    public void dead() {
        super.dead();
        kill();
    }

    private void kill() {
    }
}
