/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

package org.killbill.billing.util.template.translation;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DefaultTranslatorBase implements Translator {

    protected final Logger log = LoggerFactory.getLogger(DefaultTranslatorBase.class);

    private final ResourceBundle bundle;
    private final ResourceBundle defaultBundle;
    private final Map<String, String> properties;

    public DefaultTranslatorBase(@Nullable final ResourceBundle bundle,
                                 @Nullable final ResourceBundle defaultBundle) {
        this.bundle = bundle;
        this.defaultBundle = defaultBundle;
        this.properties = new HashMap<>();

        if (this.bundle != null) {
            for (Enumeration<String> e = bundle.getKeys(); e.hasMoreElements();) {
                String key = e.nextElement();
                this.properties.put(key, getTranslation(key));
            }
        }

        if (this.defaultBundle != null) {
            for (Enumeration<String> e = defaultBundle.getKeys(); e.hasMoreElements();) {
                String key = e.nextElement();
                this.properties.put(key, getTranslation(key));
            }
        }
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Override
    public String getTranslation(final String originalText) {
        if (originalText == null) {
            return null;
        }
        if ((bundle != null) && (bundle.containsKey(originalText))) {
            return bundle.getString(originalText);
        } else {
            if ((defaultBundle != null) && (defaultBundle.containsKey(originalText))) {
                return defaultBundle.getString(originalText);
            } else {
                return originalText;
            }
        }
    }
}
