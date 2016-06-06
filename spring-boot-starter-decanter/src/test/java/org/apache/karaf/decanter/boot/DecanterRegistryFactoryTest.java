/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.karaf.decanter.boot;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class DecanterRegistryFactoryTest {

    @Test
    public void testBundleFilter() throws Exception {
        String bundleFilter = DecanterRegistryFactory.getBundleFilter(Arrays.asList("test", "test2"));
        FrameworkUtil.createFilter(bundleFilter);
    }

    @Test
    public void testCreate() throws Exception {
        DecanterRegistryFactory factory = new DecanterRegistryFactory();
        BundleContext context = factory.create();
        checkConfigPresent(context);
        context.getBundle().stop();
    }

    private void checkConfigPresent(BundleContext context) throws Exception {
        ServiceReference<ConfigurationAdmin> sref = context.getServiceReference(ConfigurationAdmin.class);
        ConfigurationAdmin configAdmin = context.getService(sref);
        Configuration config = configAdmin.getConfiguration("test");
        Assert.assertEquals("myvalue", config.getProperties().get("mykey"));
        context.ungetService(sref);
    }
}
