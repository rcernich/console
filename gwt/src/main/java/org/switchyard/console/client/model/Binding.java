/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.console.client.model;

/**
 * Binding
 * 
 * Represents a gateway binding on a service.
 * 
 * @author Rob Cernich
 */
public interface Binding {
    
    /**
     * @return the type of binding (e.g. soap)
     */
    public String getType();
    
    /**
     * @param type the type of binding (e.g. soap)
     */
    public void setType(String type);
    
    /**
     * @return the raw configuration of the binding
     */
    public String getConfiguration();
    
    /**
     * @param configuration the raw configuration of the binding.
     */
    public void setConfiguration(String configuration);
    
    /**
     * @return true if this binding is up and running.
     */
    public Boolean getIsRunning();

    /**
     * @param isRunning true if this binding is up and running.
     */
    public void setIsRunning(Boolean isRunning);
}
