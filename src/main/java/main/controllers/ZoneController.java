package main.controllers;

import main.Repositorys.RegionRepository;
import main.Repositorys.SessionRepository;
import main.Repositorys.ZoneRepository;
import main.models.Enum.JsonReturnCodes;
import main.models.Enum.UserType;
import main.models.JsonMessage;
import main.models.Region;
import main.models.Session;
import main.models.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by KGelashvili on 11/3/2015.
 */
@Controller
public class ZoneController {
    @RequestMapping("/createzone")
    @ResponseBody
    public JsonMessage createZone(@CookieValue("projectSessionId") String sessionId,
                                  @RequestParam(value = "zoneName", required = true) String zoneName,
                                  @RequestParam(value = "regionId", required = true,defaultValue = "0") long regionId) {
        if (sessionId != null) {
            Session session = sessionDao.findOne(Long.parseLong(sessionId));
            if (session.isIsactive()) {
                if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()==UserType.admin.getCODE()){
                    Region region=regionRepository.findOne(regionId);
                    if(region==null){
                        return new JsonMessage(JsonReturnCodes.REGIONDOESNOTEXIST.getCODE(),"ასეთი რეგიონი არ არსებობს");
                    }else{
                        Zone zone=new Zone(zoneName,region);
                        zoneRepository.save(zone);
                        return new JsonMessage(JsonReturnCodes.Ok.getCODE(),"ჩანაწერი შეიქმნა წარმატებით");
                    }
                }else return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION.getCODE(),"არ გაქვთ შესაბამისი უფლება");
            } else {
                return new JsonMessage(JsonReturnCodes.SESSIONEXPIRED.getCODE(), "სესიას გაუვიდა ვადა");
            }
        }else{
            return new JsonMessage(JsonReturnCodes.NOTLOGGEDIN.getCODE(),"საჭიროა სისტემაში შესვლა");
        }

    }
    @RequestMapping("/getZones")
    @ResponseBody
    public List<Zone> getZones(){
        return zoneRepository.findAll();
    }
    @RequestMapping("/getzonesformanager")
    @ResponseBody
    public List<Zone> getZonesForMAnager(@CookieValue("projectSessionId") String sessionId){
        if(sessionId!=null){
            Session session=sessionDao.findOne(Long.parseLong(sessionId));
            if(session.isIsactive()){
                return session.getUser().getRegion().getZones();
            }else return null;
        }else return null;
    }

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SessionRepository sessionDao;
    @Autowired
    private ZoneRepository zoneRepository;
}
