package com.bootcamp.services;

import com.bootcamp.Classes.MailSender;
import com.bootcamp.classes.SmsSender;
import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.usecases.pivotone.UserWs;
import com.bootcamp.crud.PagUserCRUD;
import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Notification;
import com.bootcamp.entities.PagUser;
import com.bootcamp.entities.Preference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 * Created by Bignon on 11/27/17.
 */
public class SenderService implements DatabaseConstants {

    private String[] getMailList(List<UserWs> users){
        List<String> destinataires = new ArrayList<String>();
        
        for (UserWs user : users) {
            destinataires.add(user.getEmail());
        }
        return (String[]) destinataires.toArray();
    }
    
    private String getNumeroList(List<UserWs> users){
        String destinataires = "";
        
        for (UserWs user : users) {
            if (destinataires.isEmpty()){
                destinataires+="00229"+user.getNumero();
            }
            else{
                destinataires+=","+"00229"+user.getNumero();
            }
            
        }
        return destinataires;
    }
    
    private List<UserWs> getUsersList (String entityType, int entityId){
        List<UserWs> users = new ArrayList<UserWs>();
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("entityId", "=", entityId, "AND"));
        criterias.addCriteria(new Criteria("entityType", "=", entityType));
        List<Preference> preferences = PreferenceCRUD.read(criterias);
        
        for (Preference preference : preferences) {
            int userId = preference.getUserId();
            users.add(this.getUser(userId));
        }
        return users;
    }
    
    private UserWs getUser(int userId){
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", userId));
        PagUser user = PagUserCRUD.read(criterias).get(0);
        
        UserWs userWs = new UserWs();
        userWs.setId(user.getId());
        userWs.setNom(user.getNom());
        userWs.setEmail(user.getEmail());
        userWs.setNumero(user.getNumero());
        userWs.setUsername(user.getUsername());
        userWs.setPassword(user.getPassword());
        return userWs;
    }
    
    public void sendNotification (Notification notification){
        List<UserWs> users = this.getUsersList(notification.getEntityType(), notification.getEntityId());
        String[] destinatairesMail = this.getMailList(users);
        String destinatairesNumeros = this.getNumeroList(users);
        
        try {
            MailSender.sendMail(notification.getContenuMail(), destinatairesMail);
        } catch (MessagingException ex) {
            Logger.getLogger(SenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            SmsSender.sendSms(notification.getContenuGsm(), destinatairesNumeros);
        } catch (IOException ex) {
            Logger.getLogger(SenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
