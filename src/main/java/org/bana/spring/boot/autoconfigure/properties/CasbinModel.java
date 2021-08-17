package org.bana.spring.boot.autoconfigure.properties;

public enum CasbinModel {
  /**
   * ACL
   */
  BASIC_MODEL,
  /**
   * ACL with superuser
   */
  BASIC_WITH_ROOT_MODEL,
  /**
   * ACL without users
   */
  BASIC_WITHOUT_USERS_MODEL,
  /**
   * ACL without resources
   */
  BASIC_WITHOUT_RESOURCES_MODEL,
  /**
   * RBAC
   */
  RBAC_MODEL,
  /**
   * RBAC with resource roles
   */
  RBAC_WITH_RESOURCE_ROLES_MODEL,
  /**
   * RBAC with domains/tenants
   */
  RBAC_WITH_DOMAINS_MODEL,
  /**
   * ABAC
   */
  ABAC_MODEL,
  /**
   * RESTful
   */
  KEYMATCH_MODEL,
  /**
   * Deny-override
   */
  RBAC_WITH_NOT_DENY_MODEL,
  /**
   * Allow-and-deny
   */
  RBAC_WITH_DENY_MODEL,
  /**
   * Priority
   */
  PRIORITY_MODEL,
  /**
   * Explicit Priority
   */
  PRIORITY_MODEL_EXPLICIT,
  /**
   * Subject-Priority
   */
  SUBJECT_PRIORITY_MODEL
}
