package com.bootcamp.services;

import com.bootcamp.Tasks.SenderTask;
import com.bootcamp.commons.constants.DatabaseConstants;
import java.util.Timer;

/**
 * Created by Bignon on 11/27/17.
 */
public class SenderService implements DatabaseConstants {

    public void senderTimer() {
        Timer timer;
        timer = new Timer();
        timer.schedule(new SenderTask(), 1000, 5000);
    }
}
