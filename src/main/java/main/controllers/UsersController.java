package main.controllers;

import main.Repositorys.*;
import main.models.*;
import main.models.Enum.JsonReturnCodes;
import main.models.Enum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/21/15.
 */
@Controller
public class UsersController {

    @RequestMapping("/createuser")
    @ResponseBody
    public JsonMessage create(@CookieValue("projectSessionId") String sessionId,
                              @RequestParam(value = "username", required = true, defaultValue = "") String username,
                              @RequestParam(value = "password", required = true, defaultValue = "") String password,
                              @RequestParam(value = "email", required = true, defaultValue = "") String email,
                              @RequestParam(value = "name", required = false, defaultValue = "") String name,
                              @RequestParam(value = "surname", required = false, defaultValue = "") String surname,
                              @RequestParam(value = "address", required = false, defaultValue = "") String address,
                              @RequestParam(value = "organisationId", required = false, defaultValue = "0") long organisationId,
                              @RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
                              @RequestParam(value = "personalNumber", required = false, defaultValue = "") String personalNumber,
                              @RequestParam(value = "type", required = true, defaultValue = "0") int type,
                              @RequestParam(value = "regionId", required = false, defaultValue = "0") long regionId,
                              @RequestParam(value = "zoneId", required = false, defaultValue = "0") long zoneId,
                              @RequestParam(value = "filialId", required = false, defaultValue = "0") long filialId) {

        if (username == "" || password == "" || email == "" || name == "" || surname == "" || address == "") {
            return new JsonMessage(JsonReturnCodes.ERROR.getCODE(), "არასაკმარისი ინფორმაცია");
        }
        if (userDao.findByUsername(username).size() > 0) {
            return new JsonMessage(JsonReturnCodes.USEREXISTS.getCODE(), "მომხმარებელი ასეთი სახელით უკვე არსებობს");
        }
        Session session = sessionDao.findOne(Long.parseLong(sessionId));

        Region region;
        Zone zone;
        Organisation organisation;
        Filial filial;
        if (session.getUser().getType() == UserType.organisation.getCODE()) {
            organisation = session.getUser().getOrganisation();
            zone = session.getUser().getZone();
            type = UserType.organisationUser.getCODE();
            region = session.getUser().getRegion();
            filial=null;
        } else {
            zone = zoneRepository.findOne(zoneId);
            region = regionRepository.findOne(regionId);
            organisation = organisationRepository.findOne(organisationId);
            filial=filialReporistory.findOne(filialId);
        }

        User user = null;
        user = new UserBuilder().setAddress(address)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setAddress(address)
                .setOrganisationId(organisation)
                .setMobile(mobile)
                .setPersonalNumber(personalNumber)
                .setType(type)
                .setRegion(region)
                .setZone(zone)
                .setFilial(filial)
                .setSessions(new ArrayList<Session>())
                .createUser();
        try {

            userDao.save(user);
        } catch (Exception ex) {

            return new JsonMessage(JsonReturnCodes.ERROR.getCODE(), ex.toString());
        }
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "მომხმარებელი შეიქმნა წარმატებით");
    }


    @RequestMapping("/getusers")
    @ResponseBody
    public Page<User> getusers(@CookieValue("projectSessionId") String sessionId, int index, String search) {

        if (sessionId != null) {
            Session session = sessionDao.findOne(Long.parseLong(sessionId));
            if (session.getUser().getType() == UserType.organisation.getCODE() || session.getUser().getType() == UserType.organisationUser.getCODE()) {
                return userDao.findByUsernameOrEmailOrAddressAndOrganisation(search, search, search, session.getUser().getOrganisation().getId(), constructPageSpecification(index));
            } else {
                if (session.getUser().getType() == UserType.sa.getCODE() || session.getUser().getType() == UserType.admin.getCODE()) {
                    return userDao.findByUsernameOrEmailOrAddress(search, search, search, constructPageSpecification(index));
                } else return null;
            }
        } else return null;
    }

    @RequestMapping("/getUsersByRegion")
    @ResponseBody
    public List<User> getUsersByR(long regionId) {
        return regionRepository.findOne(regionId).getUsers();
    }

    @RequestMapping("/edituser")
    @ResponseBody
    public User editUser(long id, User k) {
        User user = userDao.findOne(id);

        if (k.getAddress() != null) {
            user.setAddress(k.getAddress());
        }
        if (k.getEmail() != null) {
            user.setEmail(k.getEmail());
        }
        if (k.getUsername() != null) {
            user.setUsername(k.getUsername());
        }
        if (k.getPassword() != null) {
            user.setPassword(k.getPassword());
        }
        if (k.getName() != null) {
            user.setName(k.getName());
        }
        if (k.getSurname() != null) {
            user.setSurname(k.getSurname());
        }
        if (k.getMobile() != null) {
            user.setMobile(k.getMobile());
        }
        if (k.getPersonalNumber() != null) {
            user.setPersonalNumber(k.getPersonalNumber());
        }
        if (k.getType() != 0) {
            user.setType(k.getType());
        }

        try {
            userDao.save(user);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @RequestMapping("/deleteuser")
    @ResponseBody
    public String deleteUser(long id) {
        try {
            userDao.delete(id);
            return "წაიშალა წარმატებით";
        } catch (Exception e) {
            return "წაშლის დროს მოხდა შეცდომა";
        }


    }
    @RequestMapping("/getcuriersformanager")
    @ResponseBody
    public List<User> getCureiersForMAnager(@CookieValue("projectSessionId") String sessionId){
        if(sessionId!=null){
            Session session=sessionDao.findOne(Long.parseLong(sessionId));
            if(session.isIsactive()){
                return userDao.findByType(UserType.courier.getCODE());
            }else return null;
        }else return null;
    }
    @RequestMapping("/getcuriersforoperator")
    @ResponseBody
    public List<User> getCureiersForOperator(@CookieValue("projectSessionId") String sessionId){
        if(sessionId!=null){
            Session session=sessionDao.findOne(Long.parseLong(sessionId));
            if(session.isIsactive()){

                return userDao.findByFilialAndType(session.getUser().getFilial(),UserType.courier.getCODE());
            }else return null;
        }else return null;
    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }



    @Autowired
    private FilialReporistory filialReporistory;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private UserRepository userDao;
    @Autowired
    private SessionRepository sessionDao;
    @Autowired
    private OrganisationRepository organisationRepository;
}
