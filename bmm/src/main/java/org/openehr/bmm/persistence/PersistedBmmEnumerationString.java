package org.openehr.bmm.persistence;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.bmm.core.BmmEnumeration;
import org.openehr.bmm.core.BmmEnumerationInteger;
import org.openehr.bmm.core.BmmEnumerationString;
import org.openehr.bmm.core.BmmModel;

import java.io.Serializable;

/**
 * String-based enumeration type.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmEnumerationString extends PersistedBmmEnumeration<String> implements Serializable {

    public static final String P_BMM_ENUMERATION_STRING = "P_BMM_ENUMERATION_STRING";

    public PersistedBmmEnumerationString() {
        super();
    }

    public PersistedBmmEnumerationString(String name) {
        super(name);
    }

    @Override
    public void createBmmClass() {
        setBmmClass(new BmmEnumerationString(getName()));
        getBmmClass().setDocumentation(getDocumentation());
        getBmmClass().setAbstract(isAbstract());
        getBmmClass().setSourceSchemaId(getSourceSchemaId());
    }

    @Override
    public void populateBmmClass(BmmModel schema) {
        super.populateBmmClass(schema);
        if(getBmmClass() != null) {
            ((BmmEnumerationString)getBmmClass()).setItemNames(getItemNames());
            ((BmmEnumerationString)getBmmClass()).setItemValues(getItemValues());
        }
    }
}
