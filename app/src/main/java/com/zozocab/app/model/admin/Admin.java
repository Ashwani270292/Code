package com.zozocab.app.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Admin {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("realm")
    @Expose
    private String realm;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("collaborators")
    @Expose
    private List<String> collaborators = new ArrayList<String>();
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("emailVerified")
    @Expose
    private Boolean emailVerified;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("callbackUrls")
    @Expose
    private List<String> callbackUrls = new ArrayList<String>();
    @SerializedName("permissions")
    @Expose
    private List<String> permissions = new ArrayList<String>();
    @SerializedName("clientKey")
    @Expose
    private String clientKey;
    @SerializedName("javaScriptKey")
    @Expose
    private String javaScriptKey;
    @SerializedName("restApiKey")
    @Expose
    private String restApiKey;
    @SerializedName("windowsKey")
    @Expose
    private String windowsKey;
    @SerializedName("masterKey")
    @Expose
    private String masterKey;
    @SerializedName("pushSettings")
    @Expose
    private PushSettings pushSettings;
    @SerializedName("authenticationEnabled")
    @Expose
    private Boolean authenticationEnabled;
    @SerializedName("anonymousAllowed")
    @Expose
    private Boolean anonymousAllowed;
    @SerializedName("authenticationSchemes")
    @Expose
    private List<AuthenticationScheme> authenticationSchemes = new ArrayList<AuthenticationScheme>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * @param realm The realm
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return The collaborators
     */
    public List<String> getCollaborators() {
        return collaborators;
    }

    /**
     * @param collaborators The collaborators
     */
    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The emailVerified
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     * @param emailVerified The emailVerified
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The callbackUrls
     */
    public List<String> getCallbackUrls() {
        return callbackUrls;
    }

    /**
     * @param callbackUrls The callbackUrls
     */
    public void setCallbackUrls(List<String> callbackUrls) {
        this.callbackUrls = callbackUrls;
    }

    /**
     * @return The permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions The permissions
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return The clientKey
     */
    public String getClientKey() {
        return clientKey;
    }

    /**
     * @param clientKey The clientKey
     */
    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    /**
     * @return The javaScriptKey
     */
    public String getJavaScriptKey() {
        return javaScriptKey;
    }

    /**
     * @param javaScriptKey The javaScriptKey
     */
    public void setJavaScriptKey(String javaScriptKey) {
        this.javaScriptKey = javaScriptKey;
    }

    /**
     * @return The restApiKey
     */
    public String getRestApiKey() {
        return restApiKey;
    }

    /**
     * @param restApiKey The restApiKey
     */
    public void setRestApiKey(String restApiKey) {
        this.restApiKey = restApiKey;
    }

    /**
     * @return The windowsKey
     */
    public String getWindowsKey() {
        return windowsKey;
    }

    /**
     * @param windowsKey The windowsKey
     */
    public void setWindowsKey(String windowsKey) {
        this.windowsKey = windowsKey;
    }

    /**
     * @return The masterKey
     */
    public String getMasterKey() {
        return masterKey;
    }

    /**
     * @param masterKey The masterKey
     */
    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    /**
     * @return The pushSettings
     */
    public PushSettings getPushSettings() {
        return pushSettings;
    }

    /**
     * @param pushSettings The pushSettings
     */
    public void setPushSettings(PushSettings pushSettings) {
        this.pushSettings = pushSettings;
    }

    /**
     * @return The authenticationEnabled
     */
    public Boolean getAuthenticationEnabled() {
        return authenticationEnabled;
    }

    /**
     * @param authenticationEnabled The authenticationEnabled
     */
    public void setAuthenticationEnabled(Boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }

    /**
     * @return The anonymousAllowed
     */
    public Boolean getAnonymousAllowed() {
        return anonymousAllowed;
    }

    /**
     * @param anonymousAllowed The anonymousAllowed
     */
    public void setAnonymousAllowed(Boolean anonymousAllowed) {
        this.anonymousAllowed = anonymousAllowed;
    }

    /**
     * @return The authenticationSchemes
     */
    public List<AuthenticationScheme> getAuthenticationSchemes() {
        return authenticationSchemes;
    }

    /**
     * @param authenticationSchemes The authenticationSchemes
     */
    public void setAuthenticationSchemes(List<AuthenticationScheme> authenticationSchemes) {
        this.authenticationSchemes = authenticationSchemes;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     * @param modified The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

}








