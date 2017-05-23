/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.type.validate.option;

import org.json.simple.JSONArray;

public class AttributeFix extends ValidatorOption {

	JSONArray attributeKey;

	public enum Type {

		ATTRIBUTEFIX("AttributeFix", "AttributeError");
		String errName;
		String errType;

		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		public String errName() {
			return errName;
		}

		public String errType() {
			return errType;
		}
	};

	public AttributeFix(JSONArray attributeKey) {
		super();
		this.attributeKey = attributeKey;
	}

	public JSONArray getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(JSONArray attributeKey) {
		this.attributeKey = attributeKey;
	}

}
