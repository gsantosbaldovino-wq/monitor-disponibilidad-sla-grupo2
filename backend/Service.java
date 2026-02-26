Service.java
package com.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Table(name = "services")
public class Service implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Constantes para validación
    private static final String[] VALID_TYPES = {"http", "https", "ping", "port"};
    private static final String[] VALID_METHODS = {"GET", "POST", "HEAD"};
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    );
    private static final Pattern IP_PATTERN = Pattern.compile(
        "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$"
    );
    
    // Columnas principales
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "url", length = 500, nullable = false)
    private String url;
    
    @Column(name = "type", length = 20, nullable = false)
    private String type;
    
    @Column(name = "method", length = 10)
    private String method;
    
    @Column(name = "expected_status")
    private Integer expectedStatus;
    
    @Column(name = "expected_response", columnDefinition = "TEXT")
    private String expectedResponse;
    
    @Column(name = "port")
    private Integer port;
    
    @Column(name = "check_interval", nullable = false)
    private Integer checkInterval = 60;
    
    @Column(name = "timeout", nullable = false)
    private Integer timeout = 30;
    
    @Column(name = "retries", nullable = false)
    private Integer retries = 2;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "notifications_enabled", nullable = false)
    private Boolean notificationsEnabled = true;
    
    @Column(name = "sla_target", precision = 5, scale = 2, nullable = false)
    private Double slaTarget = 99.9;
    
    @Column(name = "sla_response_time")
    private Integer slaResponseTime;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructores
    public Service() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Service(String name, String url, String type) {
        this();
        this.name = name;
        this.url = url;
        setType(type); // Usa setter para validación
    }
    
    // Getters y Setters con validaciones
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
        validateType(); // Validar al setear
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
        validateMethod(); // Validar al setear
    }
    
    public Integer getExpectedStatus() {
        return expectedStatus;
    }
    
    public void setExpectedStatus(Integer expectedStatus) {
        this.expectedStatus = expectedStatus;
    }
    
    public String getExpectedResponse() {
        return expectedResponse;
    }
    
    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }
    
    public Integer getPort() {
        return port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
        validatePort(); // Validar al setear
    }
    
    public Integer getCheckInterval() {
        return checkInterval;
    }
    
    public void setCheckInterval(Integer checkInterval) {
        this.checkInterval = checkInterval;
    }
    
    public Integer getTimeout() {
        return timeout;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public Integer getRetries() {
        return retries;
    }
    
    public void setRetries(Integer retries) {
        this.retries = retries;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }
    
    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
    
    public Double getSlaTarget() {
        return slaTarget;
    }
    
    public void setSlaTarget(Double slaTarget) {
        this.slaTarget = slaTarget;
    }
    
    public Integer getSlaResponseTime() {
        return slaResponseTime;
    }
    
    public void setSlaResponseTime(Integer slaResponseTime) {
        this.slaResponseTime = slaResponseTime;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
}
