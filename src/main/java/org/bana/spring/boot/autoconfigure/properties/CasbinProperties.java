package org.bana.spring.boot.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("casbin")
public class CasbinProperties {

  /**
   * Enable Casbin
   */
  private boolean enableCasbin = true;
  /**
   * Whether to use a synchronized Enforcer
   */
  private boolean useSyncedEnforcer = false;
  /**
   * Local model file
   */
  private String model = "casbin/model.conf";
  /**
   * Local policy file
   */
  private String policy = "casbin/policy.csv";
  /**
   * Storage strategy
   */
  private CasbinStoreType storeType = CasbinStoreType.JDBC;
  /**
   * Casbin model
   */
  private CasbinModel casbinModel = CasbinModel.RBAC_MODEL;
  /**
   * Watcher synchronization strategy
   */
  private CasbinWatcherType watcherType = CasbinWatcherType.REDIS;
  /**
   * Data table initialization strategy
   */
  private CasbinDataSourceInitializationMode initializeSchema = CasbinDataSourceInitializationMode.CREATE;
  /**
   * Whether to use Watcher for strategy synchronization
   */
  private boolean enableWatcher = false;
  /**
   * The configuration will only take effect if the adapter supports this function Can be manually
   * switched through enforcer.enableAutoSave(true)
   */
  private boolean autoSave = true;
  /**
   * If the local model file address is not set or the file is not found in the default path Use
   * default rbac configuration
   */
  private boolean useDefaultModelIfModelNotSetting = true;

//  public String getPolicy() {
//    return null;
//  }
//
//  public InputStream getPolicyInputStream() {
//    return null;
//  }

  public boolean getEnableCasbin() {
    return enableCasbin;
  }

  public void setEnableCasbin(boolean enableCasbin) {
    this.enableCasbin = enableCasbin;
  }

  public boolean getUseSyncedEnforcer() {
    return useSyncedEnforcer;
  }

  public void setUseSyncedEnforcer(boolean useSyncedEnforcer) {
    this.useSyncedEnforcer = useSyncedEnforcer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }

  public CasbinStoreType getStoreType() {
    return storeType;
  }

  public void setStoreType(CasbinStoreType storeType) {
    this.storeType = storeType;
  }

  public CasbinModel getCasbinModel() {
    return casbinModel;
  }

  public void setCasbinModel(CasbinModel casbinModel) {
    this.casbinModel = casbinModel;
  }

  public CasbinWatcherType getWatcherType() {
    return watcherType;
  }

  public void setWatcherType(CasbinWatcherType watcherType) {
    this.watcherType = watcherType;
  }

  public CasbinDataSourceInitializationMode getInitializeSchema() {
    return initializeSchema;
  }

  public void setInitializeSchema(
      CasbinDataSourceInitializationMode initializeSchema) {
    this.initializeSchema = initializeSchema;
  }

  public boolean isEnableWatcher() {
    return enableWatcher;
  }

  public void setEnableWatcher(boolean enableWatcher) {
    this.enableWatcher = enableWatcher;
  }

  public boolean isAutoSave() {
    return autoSave;
  }

  public void setAutoSave(boolean autoSave) {
    this.autoSave = autoSave;
  }

  public boolean getUseDefaultModelIfModelNotSetting() {
    return useDefaultModelIfModelNotSetting;
  }

  public void setUseDefaultModelIfModelNotSetting(boolean useDefaultModelIfModelNotSetting) {
    this.useDefaultModelIfModelNotSetting = useDefaultModelIfModelNotSetting;
  }
}
