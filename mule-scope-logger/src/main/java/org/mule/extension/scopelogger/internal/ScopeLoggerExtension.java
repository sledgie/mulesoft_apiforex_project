package org.mule.extension.scopelogger.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "scope-logger")
@Extension(name = "Scope Logger")
@Configurations(ScopeLoggerConfiguration.class)
public class ScopeLoggerExtension {

}
