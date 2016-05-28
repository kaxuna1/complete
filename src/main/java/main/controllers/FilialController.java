package main.controllers;

import main.Repositorys.FilialReporistory;
import main.Repositorys.RegionRepository;
import main.Repositorys.SessionRepository;
import main.models.*;
import main.models.Enum.JsonReturnCodes;
import main.models.Enum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by kaxa on 5/24/16.
 */

@Controller
public class FilialController {
    @RequestMapping("/createfilial")
    @ResponseBody
    public JsonMessage createeFilial(@CookieValue("projectSessionId") String sessionId,
                                     @RequestParam(value="name",required = true, defaultValue="")String name,
                                     @RequestParam(value="address",required = true, defaultValue="")String address,
                                     @RequestParam(value = "regionId", required = false, defaultValue = "0") long regionId){
        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                Region region=regionRepository.findOne(regionId);
                Filial filial=new Filial(name,address,region);
                filialReporistory.save(filial);
                return new JsonMessage(JsonReturnCodes.Ok.getCODE(),"ჩანაწერი შეიქმნა წარმატებით");
            }else return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION.getCODE(),"არ გაქვთ ჩანაწერის შექმნის უფლება");
        }else return new JsonMessage(JsonReturnCodes.NOTLOGGEDIN.getCODE(),"გთხოვთ შეხვიდეთ სისტემაში");

    }

    @RequestMapping("/getfilials")
    @ResponseBody
    public List<Filial> getFilials(){
        return filialReporistory.findAll();
    }
    @RequestMapping("/getregionfilials")
    @ResponseBody
    public List<Filial> getregionFilials(long id){
        return filialReporistory.findByRegion(regionRepository.findOne(id));
    }


    @Autowired
    private FilialReporistory filialReporistory;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SessionRepository sessionRepository;
}
