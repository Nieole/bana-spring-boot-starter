package org.bana.spring.boot.autoconfigure.properties;

public enum CasbinDataSourceInitializationMode {
  /**
   * Automatically create data tables
   */
  CREATE,
  /**
   * Automatically update data tables
   */
  UPDATE,
  /**
   * Do not automatically build tables
   */
  NEVER
}
