/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.Tasks;

import com.bootcamp.Classes.MailSender;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Bignon
 */
public class SenderTask extends TimerTask{
    @Override
    public void run() {
        try {
            MailSender.sender("sendMail");
        } catch (MessagingException ex) {
            Logger.getLogger(SenderTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SenderTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
