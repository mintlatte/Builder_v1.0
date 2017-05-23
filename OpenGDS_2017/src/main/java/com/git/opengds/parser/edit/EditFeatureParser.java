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

package com.git.opengds.parser.edit;

import java.util.Hashtable;
import java.util.Iterator;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.simple.feature.Feature;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * JSONObject를 QA20Feature 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:49:32
 */
public class EditFeatureParser {

	protected static final int none = 0;
	protected static final int isEdited = 1;
	protected static final int isCreated = 2;
	protected static final int isDeleted = 3;

	JSONObject featureObj;
	Feature feature;
	Feature editedFeature;
	Feature createdFeature;
	Feature deletedFeature;

	public EditFeatureParser(JSONObject featureObj, int state) throws ParseException {
		this.featureObj = featureObj;
		featureParse();
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Feature getEditedFeature() {
		return editedFeature;
	}

	public void setEditedFeature(Feature editedFeature) {
		this.editedFeature = editedFeature;
	}

	public Feature getCreatedFeature() {
		return createdFeature;
	}

	public void setCreatedFeature(Feature createdFeature) {
		this.createdFeature = createdFeature;
	}

	public Feature getDeletedFeature() {
		return deletedFeature;
	}

	public void setDeletedFeature(Feature deletedFeature) {
		this.deletedFeature = deletedFeature;
	}

	public void featureParse() throws ParseException {

		// DTNGIFeature
		String featureID = null;
		String featureType = null;
		String numparts = null;
		String coordinateSize = null;
		Geometry geom = null;
		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		Iterator iterator = featureObj.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (key.equals("feature_id")) {
				featureID = (String) featureObj.get(key);
			} else if (key.equals("feature_type")) {
				featureType = (String) featureObj.get(key);
			} else if (key.equals("geom")) {
				WKTReader reader = new WKTReader();
				geom = reader.read((String) featureObj.get(key));
			} else if (key.equals("num_rings")) {
				numparts = (String) featureObj.get(key);
			} else if (key.equals("num_vertexes")) {
				coordinateSize = (String) featureObj.get(key);
			} else {
				properties.put(key, featureObj.get(key));
			}
		}
		if (properties.size() == 0) {
			properties = null;
		}
		Feature feature = new Feature(featureID, featureType, "", geom, properties);
	}
}
