package com.cynthia.socmed.comp;


import com.cynthia.socmed.DAO.CountryDao;
import com.cynthia.socmed.DAO.EmojisDao;
import com.cynthia.socmed.DAO.ReportObjectDao;
import com.cynthia.socmed.DAO.RoleDao;
import com.cynthia.socmed.models.Country;
import com.cynthia.socmed.models.Emojis;
import com.cynthia.socmed.models.ReportObject;
import com.cynthia.socmed.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    CountryDao countryDao;

    @Autowired
    EmojisDao emojisDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    ReportObjectDao reportObjectDao;

    @Override

    public void onApplicationEvent(final ApplicationReadyEvent event) {

        List<Country> countries = (List<Country>) countryDao.findAll();
        List<Role> roles = (List<Role>) roleDao.findAll();
        List<Emojis> emojis = (List<Emojis>) emojisDao.findAll();
        List<ReportObject> reportObjects = (List<ReportObject>) reportObjectDao.findAll();
        String line;
        String line2;

        try {
            if(countries.isEmpty()) {
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ib\\socmed\\src\\main\\resources\\static/countriesOk.txt"));
                String s;
                while ((line = br.readLine()) != null) {
                    Country c = new Country();
                    c.setName(line);
                    c.setIcon("flag/png/" + line + ".png");
                    countryDao.save(c);
                }
            }

            if(emojis.isEmpty()) {
                List <String> emos = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ib\\socmed\\lol.txt"));
                while ((line2 = br.readLine()) != null) {
                    emos.add(line2);
                }
                List<String> noDuplicateList = emos.stream().distinct().collect(Collectors.toList());
                for (String s : noDuplicateList) {
                    Emojis emo = new Emojis();
                    emo.setCode(s);
                    emojisDao.save(emo);
                }
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






        if(roles.isEmpty()) {
            Role admin = new Role();
            admin.setName("ADMIN");
            roleDao.save(admin);
            Role moderator = new Role();
            moderator.setName("MODERATOR");
            roleDao.save(moderator);
            Role user = new Role();
            user.setName("USER");
            roleDao.save(user);

        }

        if(reportObjects.isEmpty()) {
            ReportObject racism = new ReportObject();
            racism.setName("Racism");
            reportObjectDao.save(racism);
            ReportObject fake = new ReportObject();
            fake.setName("Fake");
            reportObjectDao.save(fake);
            ReportObject pornography = new ReportObject();
            pornography.setName("Pornography");
            reportObjectDao.save(pornography);

        }

        return;
    }





}
