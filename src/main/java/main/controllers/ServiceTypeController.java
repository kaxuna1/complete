package main.controllers;

import com.google.gson.JsonObject;
import main.Repositorys.ServiceTypeRepository;
import main.Repositorys.SessionRepository;
import main.models.Enum.UserType;
import main.models.Format;
import main.models.Organisation;
import main.models.ServiceType;
import main.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by KGelashvili on 11/2/2015.
 */
@Controller
public class ServiceTypeController {
    @RequestMapping("/createservicetype")
    @ResponseBody
    public ServiceType createServiceType(@CookieValue("projectSessionId") String sessionId,
                                         @RequestParam(value="name",required = true, defaultValue="")String name,
                                         @RequestParam(value="price",required = true, defaultValue="")float price){
        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                ServiceType serviceType =new ServiceType(name,price);
                serviceTypeRepository.save(serviceType);
                return serviceType;
            }else return null;
        }else return null;
    }

    @RequestMapping("/getservicetypes")
    @ResponseBody
    public List<ServiceType> getFormats(){
        return serviceTypeRepository.findAll();
    }

    @RequestMapping("/getservicetypesforselect")
    @ResponseBody
    public String getFormatsForSelect(){
        JsonObject jsonObject=new JsonObject();
        List<ServiceType> serviceTypes=serviceTypeRepository.findAll();
        for(int i=0;i<serviceTypes.size();i++){
            jsonObject.addProperty(serviceTypes.get(i).getId()+"", serviceTypes.get(i).getName());
        }

        return jsonObject.toString();
    }

    @RequestMapping("/deleteservicetype")
    @ResponseBody
    public boolean deleteServiceType(@CookieValue("projectSessionId") String sessionId,long id){
        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                serviceTypeRepository.delete(id);
                return true;
            }else return false;
        }else return false;
    }
    @RequestMapping("/editservicetype")
    @ResponseBody
    public ServiceType editServiceType(@CookieValue("projectSessionId") String sessionId,long editId,ServiceType serviceTypeE){

        if(sessionId!=null){
            Session session=sessionRepository.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){
                ServiceType serviceType=serviceTypeRepository.findOne(editId);
                if(serviceTypeE.getName()!=null){
                    serviceType.setName(serviceTypeE.getName());
                }
                if(serviceTypeE.getPricePlus()!=0){
                    serviceType.setPricePlus(serviceTypeE.getPricePlus());
                }
                serviceTypeRepository.save(serviceType);

                return serviceType;
            }else return null;
        }else return null;

    }



    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private SessionRepository sessionRepository;
}
