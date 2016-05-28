package main.controllers;

import com.google.gson.JsonObject;
import main.Repositorys.OrganisationRepository;
import main.Repositorys.RegionRepository;
import main.Repositorys.SessionRepository;
import main.Repositorys.UserRepository;
import main.models.*;
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

import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/22/15.
 */
@Controller
public class OrganisationController {

    @RequestMapping("/createorganisation")
    @ResponseBody
    public Organisation createorganisation(@CookieValue("projectSessionId") String sessionId,
                                           @RequestParam(value="name",required = true, defaultValue="")String name,
                                           @RequestParam(value="address",required = true, defaultValue="")String address,
                                           @RequestParam(value="mobileNumber",required = true, defaultValue="")String mobileNumber,
                                           @RequestParam(value="email",required = true, defaultValue="")String email,
                                           @RequestParam(value="regionId",required = true, defaultValue="0")long regionId){

        if(sessionId!=null){
            Session session=sessionDao.findOne(Long.parseLong(sessionId));
            if(session.getUser().getType()== UserType.admin.getCODE()||session.getUser().getType()==UserType.sa.getCODE()){
                Region region=regionRepository.findOne(regionId);
                Organisation organisation=null;
                organisation= new OrganisationBuilder().setAddress(address).setEmail(email).setMobileNumber(mobileNumber).setName(name)
                        .setRegion(region).createOrganisation();
                try {
                    organisationRepository.save(organisation);
                }
                catch (Exception e){
                    return null;
                }

                return organisation;
            }else return null;
        }else return null;
    }
    @RequestMapping("/getorganisations")
    @ResponseBody
    public Page<Organisation> getOrganisations(int index,String search){

        return organisationRepository.findByNameOrEmailOrAddress(search,search,search,constructPageSpecification(index));

    }
    @RequestMapping("/getallorganisations")
    @ResponseBody
    public List<Organisation> getAllOrganisations(){
        return organisationRepository.findAll();
    }
    @RequestMapping("/editorganisation")
    @ResponseBody
    public Organisation editOrganisation(long id,Organisation organisationEdit){
        Organisation organisation=organisationRepository.findOne(id);
        if(organisationEdit.getAddress()!=null){
            organisation.setAddress(organisationEdit.getAddress());
        }
        if(organisationEdit.getEmail()!=null){
            organisation.setEmail(organisationEdit.getEmail());
        }
        if(organisationEdit.getName()!=null){
            organisation.setName(organisationEdit.getName());
        }
        if(organisationEdit.getMobileNumber()!=null){
            organisation.setMobileNumber(organisationEdit.getMobileNumber());
        }
        try {
            organisationRepository.save(organisation);
        }catch (Exception e){
            return null;
        }


        return organisation;
    }

    @RequestMapping("/getorganisation")
    @ResponseBody
    public Organisation getOrganisation(long id){
        return organisationRepository.findOne(id);
    }


    @RequestMapping("/getorganisationforselect")
    @ResponseBody
    public String getFormatsForSelect(){
        JsonObject jsonObject=new JsonObject();
        List<Organisation> organisations=organisationRepository.findAll();
        for(int i=0;i<organisations.size();i++){
            jsonObject.addProperty(organisations.get(i).getId()+"", organisations.get(i).getName());
        }

        return jsonObject.toString();
    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SessionRepository sessionDao;
    @Autowired
    private OrganisationRepository organisationRepository;
}
