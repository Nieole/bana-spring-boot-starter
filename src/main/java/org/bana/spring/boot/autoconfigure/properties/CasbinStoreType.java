package org.bana.spring.boot.autoconfigure.properties;

/**
 * @author fangzhengjin
 * @version V1.0
 * @title: CasbinStoreType
 * @package org.casbin.spring.boot.autoconfigure.properties
 * @description:
 * @date 2019/4/2 15:31
 */
public enum CasbinStoreType {
  /**
   * Use files for storage
   */
  FILE,
  /**
   * Use JDBC for storage
   */
  JDBC,
  /**
   * use jpa for storage
   */
  JPA,
  /**
   * use mybatis for storage
   */
  MYBATIS,
  /**
   * use redis for storage
   */
  REDIS,
}
