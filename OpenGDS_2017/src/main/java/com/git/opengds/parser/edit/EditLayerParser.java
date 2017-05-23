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

import java.util.Iterator;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.simple.feature.FeatureList;
import com.git.gdsbuilder.type.simple.layer.Layer;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20Layer 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:38
 */
public class EditLayerParser {

	protected static final int isNone = 0;
	protected static final int isEdited = 1;
	protected static final int isCreated = 2;
	protected static final int isDeleted = 3;

	JSONObject layerObj;
	Layer layer;
	Layer createdLayer;
	Layer editedLayer;
	Layer deletedLayer;

	/**
	 * EditLayerParser 생성자
	 * 
	 * @param layerObj
	 * @throws ParseException
	 */
	public EditLayerParser(JSONObject layerObj) throws ParseException {
		this.layerObj = layerObj;
		layerParse();
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public Layer getCreatedLayer() {
		return createdLayer;
	}

	public void setCreatedLayer(Layer createdLayer) {
		this.createdLayer = createdLayer;
	}

	public Layer getEditedLayer() {
		return editedLayer;
	}

	public void setEditedLayer(Layer editedLayer) {
		this.editedLayer = editedLayer;
	}

	public Layer getDeletedLayer() {
		return deletedLayer;
	}

	public void setDeletedLayer(Layer deletedLayer) {
		this.deletedLayer = deletedLayer;
	}

	public void layerParse() throws ParseException {

		Iterator iterator = layerObj.keySet().iterator();
		while (iterator.hasNext()) {
			String stateKey = (String) iterator.next();
			JSONObject featureObj = (JSONObject) layerObj.get(stateKey);
			if (stateKey.equals("created")) {
				FeatureList featureList = parseCreatedLayer(featureObj);
				this.createdLayer = new Layer(null, null, null, featureList);
			} else if (stateKey.equals("modified")) {
				FeatureList featureList = parseEditedLayer(featureObj);
			} else if (stateKey.equals("removed")) {
				FeatureList featureList = parseDeletedLayer(featureObj);
			}
		}
	}

	private FeatureList parseCreatedLayer(JSONObject featureObj) {

		return null;
	}

	private FeatureList parseDeletedLayer(JSONObject featureObj) {

		return null;
	}

	private FeatureList parseEditedLayer(JSONObject featureObj) {

		return null;
	}
}
