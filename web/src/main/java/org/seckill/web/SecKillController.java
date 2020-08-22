package org.seckill.web;

import org.seckill.bean.SecKill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.dto.SecKillResult;
import org.seckill.enums.SecKillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    private SecKillService secKillService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
        List<SecKill> secKillList = secKillService.getSecKillList();
        map.put("list", secKillList);
        return "list";
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "id", required = false) Long secKillId, Map<String, Object> map) {
        if (secKillId == null) {
            return "redirect:/secKill/list";
        }
        SecKill secKill = secKillService.getById(secKillId);
        if (secKill == null) {
            return "redirect:/secKill/list";
        }
        map.put("detail", secKill);
        return "detail";
    }

    @RequestMapping(value = "/{id}/exposer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable(value = "id", required = false) Long secKillId) {
        SecKillResult<Exposer> secKillResult = null;
        try {
            Exposer exposer = secKillService.exportSecKillURL(secKillId);
            secKillResult = new SecKillResult<>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            secKillResult = new SecKillResult<>(false, e.getMessage());
        }
        return secKillResult;
    }

    @PostMapping(value = "/{id}/{md5}/execution")
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable(value = "id", required = false) Long secKillId,
                                                   @PathVariable(value = "md5", required = false) String md5,
            /*@Valid @NotNull*/@CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SecKillResult<>(false, "not register");
        }
        return new SecKillResult<>(true, secKillService.executeSecKillProcedure(secKillId, userPhone, md5));
    }

    /*@RequestMapping(value = "/{id}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable(value = "id", required = false) Long secKillId,
                                                   @PathVariable(value = "md5", required = false) String md5,
            *//*@Valid @NotNull*//*@CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SecKillResult<>(false, "not register");
        }
        try {
            return new SecKillResult<>(true, secKillService.executeSecKill(secKillId, userPhone, md5));
        } catch (SecKillCloseException e) {
            return new SecKillResult<>(true, new SecKillExecution(secKillId, SecKillState.END));
        } catch (RepeatKillException e) {
            return new SecKillResult<>(true, new SecKillExecution(secKillId, SecKillState.RePEAT_KILL));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SecKillResult<>(true, new SecKillExecution(secKillId, SecKillState.INNER_ERROR));
        }
    }*/

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SecKillResult<Long> getTime(){
        return new SecKillResult<>(true,new Date().getTime());
    }
}
