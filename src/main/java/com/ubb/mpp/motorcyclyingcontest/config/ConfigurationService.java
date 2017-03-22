package com.ubb.mpp.motorcyclyingcontest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Marius Adam
 */
@Service
public class ConfigurationService {
    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.user}")
    private String databaseUser;
    @Value("${database.pass}")
    private String databasePass;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabasePass() {
        return databasePass;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }
}
