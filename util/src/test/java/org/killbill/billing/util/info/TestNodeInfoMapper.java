/*
 * Copyright 2014-2015 Groupon, Inc
 * Copyright 2014-2015 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.util.info;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.killbill.billing.osgi.api.PluginInfo;
import org.killbill.billing.osgi.api.PluginServiceInfo;
import org.killbill.billing.util.UtilTestSuiteNoDB;
import org.killbill.billing.util.info.json.NodeInfoModelJson;
import org.killbill.billing.util.info.json.PluginInfoModelJson;
import org.killbill.billing.util.info.json.PluginServiceInfoModelJson;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class TestNodeInfoMapper extends UtilTestSuiteNoDB {

    @Inject
    protected NodeInfoMapper nodeInfoMapper;


    @Test(groups = "fast")
    public void testBasic() throws Exception {


        final PluginServiceInfoModelJson svc = new PluginServiceInfoModelJson("typeName", "registrationName");

        final Set<PluginServiceInfoModelJson> services1 = new HashSet<PluginServiceInfoModelJson>();
        services1.add(svc);

        final List<PluginInfoModelJson> pluginInfos = new ArrayList<PluginInfoModelJson>();
        final PluginInfoModelJson info1 = new PluginInfoModelJson("sym1", "name1", "vers1", true, services1);
        pluginInfos.add(info1);
        final NodeInfoModelJson input = new NodeInfoModelJson("nodeName", clock.getUTCNow(), clock.getUTCNow(), "1.0", "1.0", "1.0", "1.0", "1.0", pluginInfos);

        final String nodeInfoStr = nodeInfoMapper.serialize(input);


        final NodeInfoModelJson res = nodeInfoMapper.deserialize(nodeInfoStr);

        Assert.assertEquals(res, input);
    }

}