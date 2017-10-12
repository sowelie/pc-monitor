package com.twocogs.homeassistant.pcmonitor.controllers

import com.google.common.collect.ImmutableMap
import com.twocogs.homeassistant.pcmonitor.models.StatusModel
import org.apache.commons.lang.SystemUtils
import org.hyperic.sigar.Sigar
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class MonitorController {
    private val logger = LoggerFactory.getLogger(MonitorController::class.java)

    @RequestMapping(value = "/status", method = arrayOf(RequestMethod.GET))
    @Throws(Exception::class)
    fun getStatus(): StatusModel {
        val sigar = Sigar()

        return StatusModel(sigar.cpuPerc.combined, sigar.mem.total.toDouble(),
                sigar.mem.actualUsed.toDouble(), System.getProperty("os.name"))
    }

    @RequestMapping(value = "/shutdown", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    fun shutdown(): Map<String, Boolean> {
        val shutdownCommand: String
        val t = "now"

        logger.info("Attempting to shutdown the PC.")

        shutdownCommand = if (SystemUtils.IS_OS_AIX)
            "shutdown -Fh " + t
        else if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_UNIX)
            "shutdown -h " + t
        else if (SystemUtils.IS_OS_HP_UX)
            "shutdown -hy " + t
        else if (SystemUtils.IS_OS_IRIX)
            "shutdown -y -g " + t
        else if (SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS)
            "shutdown -y -i5 -g" + t
        else if (SystemUtils.IS_OS_WINDOWS)
            "shutdown.exe -s -t 0 -d p:0:0"
        else
            return ImmutableMap.of("success", false)

        Runtime.getRuntime().exec(shutdownCommand)
        return ImmutableMap.of("success", true)
    }
}