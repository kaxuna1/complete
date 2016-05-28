package main.controllers;

import main.Repositorys.RegionRepository;
import main.Repositorys.SessionRepository;
import main.models.Enum.UserType;
import main.models.Region;
import main.models.Session;
import main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/23/15.
 */
@Controller
public class RegionController {
    @RequestMapping("/createregion")
    @ResponseBody
    public Region create(@CookieValue("projectSessionId") String sessionId, String name) {
        if (sessionId != null) {
            Session session = sessionRepository.findOne(Long.parseLong(sessionId));
            if (session.isIsactive()) {
                if (session.getUser().getType() == UserType.admin.getCODE() || session.getUser().getType() == UserType.sa.getCODE()) {
                    Region region = new Region(name);
                    regionRepository.save(region);
                    return region;
                } else return null;
            } else return null;
        } else return null;
    }

    @RequestMapping("/editregion")
    @ResponseBody
    public Region edit(@CookieValue("projectSessionId") String sessionId, long id, Region region2) {
        if (sessionId != null) {
            Session session = sessionRepository.findOne(Long.parseLong(sessionId));
            if (session.isIsactive()) {
                if (session.getUser().getType() == UserType.admin.getCODE() || session.getUser().getType() == UserType.sa.getCODE()) {
                    Region region = regionRepository.findOne(id);
                    if (region2.getName() != null) {
                        region.setName(region2.getName());
                        try {
                            regionRepository.save(region);
                        } catch (Exception e) {
                            return null;
                        }
                    }
                    return region;
                } else return null;
            } else return null;
        } else return null;

    }

    @RequestMapping("/deleteregion")
    @ResponseBody
    public boolean deleteRegion(@CookieValue("projectSessionId") String sessionId, long id) {
        if (sessionId != null) {
            Session session = sessionRepository.findOne(Long.parseLong(sessionId));
            if (session.isIsactive()) {
                if (session.getUser().getType() == UserType.admin.getCODE() || session.getUser().getType() == UserType.sa.getCODE()) {
                    regionRepository.delete(id);
                    return true;
                } else return false;
            } else return false;
        } else return false;
    }

    @RequestMapping("getregions")
    @ResponseBody
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }


    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SessionRepository sessionRepository;
}
