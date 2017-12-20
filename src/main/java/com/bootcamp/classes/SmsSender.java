/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.classes;

import com.bootcamp.client.OctoPushClient;
import java.io.IOException;

/**
 *
 * @author Bello
 */
public class SmsSender {

    public static void sendSms(String message, String destinataires) throws IOException {
        OctoPushClient octopushClient = new OctoPushClient();
        octopushClient.sendSms(message, destinataires);
    }
}