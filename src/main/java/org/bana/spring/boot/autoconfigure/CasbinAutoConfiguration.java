package org.bana.spring.boot.autoconfigure;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import org.bana.exception.CasbinModelConfigNotFoundException;
import org.bana.jpa.repository.JpaRuleRepository;
import org.bana.mybatis.repository.MybatisRuleRepository;
import org.bana.spring.boot.autoconfigure.properties.CasbinProperties;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.main.SyncedEnforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(value = {"org.bana.jpa.entity"})
@EnableJpaRepositories(value = {"org.bana.jpa"})
@ComponentScan(value = {"org.bana"})
@EnableConfigurationProperties({CasbinProperties.class})
@ConditionalOnExpression("${casbin.enableCasbin:true}")
public class CasbinAutoConfiguration {

  private final static Logger logger = LoggerFactory.getLogger(CasbinAutoConfiguration.class);

  @Bean
  @ConditionalOnMissingBean
  public Enforcer enforcer(CasbinProperties casbinProperties, Adapter adapter) {
    Model model = new Model();
    try {
      String modelContext = Resources.toString(Resources.getResource(casbinProperties.getModel()),
          Charsets.UTF_8);
//      String modelContext = casbinProperties.getModelContext();
      model.loadModelFromText(modelContext);
    } catch (CasbinModelConfigNotFoundException e) {
      // if the local model file address is not set or the file is not found in the default path, the default rbac configuration is used
      if (!casbinProperties.getUseDefaultModelIfModelNotSetting()) {
        throw e;
      }
      logger.info("Con't found model config file, use default model config");
      // request definition
      model.addDef("r", "r", "sub, obj, act");
      // policy definition
      model.addDef("p", "p", "sub, obj, act");
      // role definition
      model.addDef("g", "g", "_, _");
      // policy effect
      model.addDef("e", "e", "some(where (p.eft == allow))");
      // matchers
      model.addDef("m", "m", "g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act");
    } catch (IOException | IllegalArgumentException e) {
      e.printStackTrace();

      // if the local model file address is not set or the file is not found in the default path, the default rbac configuration is used
      if (!casbinProperties.getUseDefaultModelIfModelNotSetting()) {
        throw new RuntimeException(e);
      }
      logger.info("Con't found model config file, use default model config");
      // request definition
      model.addDef("r", "r", "sub, obj, act");
      // policy definition
      model.addDef("p", "p", "sub, obj, act");
      // role definition
      model.addDef("g", "g", "_, _");
      // policy effect
      model.addDef("e", "e", "some(where (p.eft == allow))");
      // matchers
      model.addDef("m", "m", "g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act");
    }
    Enforcer enforcer;
    if (casbinProperties.getUseSyncedEnforcer()) {
      enforcer = new SyncedEnforcer(model, adapter);
      logger.info("Casbin use SyncedEnforcer");
    } else {
      enforcer = new Enforcer(model, adapter);
    }
    enforcer.enableAutoSave(casbinProperties.isAutoSave());
    return enforcer;
  }

//  @Bean
//  @ConditionalOnProperty(name = "casbin.storeType",havingValue = "file")
//  @ConditionalOnMissingBean
//  public Adapter autoConfigFileAdapter(CasbinProperties casbinProperties){
//    // if the file storage is chosen and the policy file location is set correctly, then create a file adapter
//    if (!StringUtils.hasText(casbinProperties.getPolicy())) {
//      try (InputStream policyInputStream = casbinProperties.getPolicyInputStream()) {
//        return new FileAdapter(policyInputStream);
//      } catch (Exception ignored) {
//      }
//    }
//    throw new CasbinAdapterException("Cannot create file adapter, because policy file is not set");
//  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public Adapter autoConfigJpaAdapter(
      JpaRuleRepository jpaRuleRepository
  ) {
    return new org.bana.jpa.adapter.RuleAdapter(jpaRuleRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.adapter.UserAdapter jpaUserAdapter(
      JpaRepository<org.bana.jpa.entity.User, String> repository
  ) {
    return new org.bana.jpa.adapter.UserAdapter(repository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.adapter.RoleAdapter jpaRoleAdapter(
      JpaRepository<org.bana.jpa.entity.Role, String> repository
  ) {
    return new org.bana.jpa.adapter.RoleAdapter(repository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.adapter.PermissionAdapter jpaPermissionAdapter(
      JpaRepository<org.bana.jpa.entity.Permission, String> repository
  ) {
    return new org.bana.jpa.adapter.PermissionAdapter(repository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.service.RoleService roleService(
      org.bana.jpa.adapter.RoleAdapter jpaRoleAdapter,
      Enforcer enforcer,
      org.bana.jpa.service.PermissionService permissionService
  ) {
    return new org.bana.jpa.service.RoleService(jpaRoleAdapter, enforcer, permissionService);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.service.PermissionService permissionService(
      org.bana.jpa.adapter.PermissionAdapter jpaPermissionAdapter,
      Enforcer enforcer
  ) {
    return new org.bana.jpa.service.PermissionService(jpaPermissionAdapter, enforcer);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "jpa")
  @ConditionalOnMissingBean
  public org.bana.jpa.service.UserService userService(
      org.bana.jpa.adapter.UserAdapter jpaUserAdapter,
      Enforcer enforcer,
      org.bana.jpa.service.RoleService roleService
  ) {
    return new org.bana.jpa.service.UserService(jpaUserAdapter, enforcer, roleService);
  }


  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public Adapter autoConfigMybatisAdapter(
      MybatisRuleRepository mybatisRuleRepository
  ) {
    return new org.bana.mybatis.adapter.RuleAdapter(mybatisRuleRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.adapter.RuleAdapter mybatisRuleAdapter(
      MybatisRuleRepository mybatisRuleRepository
  ){
    return new org.bana.mybatis.adapter.RuleAdapter(mybatisRuleRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.adapter.RoleAdapter mybatisRoleAdapter(
      org.bana.mybatis.repository.RoleRepository roleRepository
  ){
    return new org.bana.mybatis.adapter.RoleAdapter(roleRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.adapter.UserAdapter mybatisUserAdapter(
      org.bana.mybatis.repository.UserRepository userRepository
  ){
    return new org.bana.mybatis.adapter.UserAdapter(userRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.adapter.PermissionAdapter mybatisPermissionAdapter(
      org.bana.mybatis.repository.PermissionRepository permissionRepository
  ){
    return new org.bana.mybatis.adapter.PermissionAdapter(permissionRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.service.UserService mybatisUserService(
      org.bana.mybatis.adapter.UserAdapter userAdapter,
      Enforcer enforcer,
      org.bana.mybatis.service.RoleService roleService
  ){
    return new org.bana.mybatis.service.UserService(userAdapter,enforcer,roleService);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.service.RoleService mybatisRoleService(
      org.bana.mybatis.adapter.RoleAdapter roleAdapter,
      Enforcer enforcer,
      org.bana.mybatis.service.PermissionService permissionService
  ){
    return new org.bana.mybatis.service.RoleService(roleAdapter,enforcer,permissionService);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.service.PermissionService mybatisPermissionService(
      org.bana.mybatis.adapter.PermissionAdapter permissionAdapter,
      Enforcer enforcer
  ){
    return new org.bana.mybatis.service.PermissionService(permissionAdapter,enforcer);
  }

  @Bean
  @ConditionalOnProperty(name = "casbin.storeType", havingValue = "mybatis")
  @ConditionalOnMissingBean
  public org.bana.mybatis.entity.XidIdGenerator xidIdGenerator(){
    return new org.bana.mybatis.entity.XidIdGenerator();
  }

}
