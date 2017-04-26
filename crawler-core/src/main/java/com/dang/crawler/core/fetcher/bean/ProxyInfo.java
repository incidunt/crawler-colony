package com.dang.crawler.core.fetcher.bean;

import java.io.Serializable;

public class ProxyInfo implements Serializable {
    private static final long serialVersionUID = 2873256585800658133L;
    private String host;
    private Integer port;
    private String username = "";
    private String password = "";
    private String workstation = "";
    private String domain = "";

    public ProxyInfo() {
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
