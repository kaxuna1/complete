package main.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.Repositorys.FormatRepository;
import main.Repositorys.SessionRepository;
import main.models.Enum.UserType;
import main.models.Format;
import main.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by KGelashvili on 11/2/2015.
 */
@Controller
public class FormatController {
    @RequestMapping("/createformat")
    @ResponseBody
    public Format createFormat(@CookieValue("projectSessionId") String sessionId,
                               @RequestParam(value="name",required = true, defaultValue="")String name,
                               @RequestParam(value="price",required = true, defaultValue="")float price){

        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                Format format =new Format(name,price);
                formatRepository.save(format);
                return format;
            }else return null;
        }else return null;
    }

    @RequestMapping("/getformats")
    @ResponseBody
    public List<Format> getFormats(){
        return formatRepository.findAll();
    }

    @RequestMapping("/deleteformats")
    @ResponseBody
    public boolean deleteFormats(@CookieValue("projectSessionId") String sessionId,long id){
        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                formatRepository.delete(id);
                return true;
            }else return false;
        }else return false;
    }
    @RequestMapping("/editformats")
    @ResponseBody
    public Format editFormat(@CookieValue("projectSessionId") String sessionId,long editId,Format formatE){

        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                Format format=formatRepository.findOne(editId);
                if(formatE.getName()!=null){
                    format.setName(formatE.getName());
                }
                if(formatE.getPrice()!=0){
                    format.setPrice(formatE.getPrice());
                }
                formatRepository.save(format);

                return format;
            }else return null;
        }else return null;

    }
    @RequestMapping("/getformatsforselect")
    @ResponseBody
    public String getFormatsForSelect(){
        JsonObject jsonObject=new JsonObject();
        List<Format> formats=formatRepository.findAll();
        for(int i=0;i<formats.size();i++){
            jsonObject.addProperty(formats.get(i).getId()+"", formats.get(i).getName());
        }

        return jsonObject.toString();
    }

    @Autowired
    private FormatRepository formatRepository;
    @Autowired
    private SessionRepository sessionRepository;
}
