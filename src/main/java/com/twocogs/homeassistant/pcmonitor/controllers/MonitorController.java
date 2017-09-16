package com.twocogs.homeassistant.pcmonitor.controllers;

import com.google.common.collect.ImmutableMap;
import com.twocogs.homeassistant.pcmonitor.models.StatusModel;
import org.apache.commons.lang.SystemUtils;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Contains actions for monitoring.
 */
@RestController
public class MonitorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorController.class);

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public StatusModel getStatus() throws Exception {
        Sigar sigar = new Sigar();

        return new StatusModel(sigar.getCpuPerc().getCombined(), sigar.getMem().getTotal(),
                sigar.getMem().getActualUsed(), System.getProperty("os.name"));
    }

    @RequestMapping(value = "/shutdown", method = RequestMethod.POST)
    public Map<String, Boolean> shutdown() throws Exception {
        String shutdownCommand, t = "now";

        LOGGER.info("Attempting to shutdown the PC.");

        if(SystemUtils.IS_OS_AIX)
            shutdownCommand = "shutdown -Fh " + t;
        else if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC|| SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_UNIX)
            shutdownCommand = "shutdown -h " + t;
        else if(SystemUtils.IS_OS_HP_UX)
            shutdownCommand = "shutdown -hy " + t;
        else if(SystemUtils.IS_OS_IRIX)
            shutdownCommand = "shutdown -y -g " + t;
        else if(SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS)
            shutdownCommand = "shutdown -y -i5 -g" + t;
        else if(SystemUtils.IS_OS_WINDOWS)
            shutdownCommand = "shutdown.exe -s -t 0 -d p:0:0";
        else
            return ImmutableMap.of("success", false);

        Runtime.getRuntime().exec(shutdownCommand);
        return ImmutableMap.of("success", true);
    }
}
