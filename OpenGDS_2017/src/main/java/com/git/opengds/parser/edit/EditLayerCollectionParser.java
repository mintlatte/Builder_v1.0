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

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.parser.edit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.simple.collection.LayerCollection;
import com.git.gdsbuilder.type.simple.layer.Layer;
import com.git.gdsbuilder.type.simple.layer.LayerList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollection 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:22
 */
public class EditLayerCollectionParser {

	protected static final int none = 0;
	protected static final int isEdited = 1;
	protected static final int isCreated = 2;
	protected static final int isDeleted = 3;

	JSONObject collectionObj;
	LayerCollection layerCollection;
	LayerCollection editedLayerCollection;
	LayerCollection createdLayerCollection;
	LayerCollection deletedLayerCollection;

	/**
	 * EditLayerCollectionParser 생성자
	 * 
	 * @param collectionObject
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditLayerCollectionParser(JSONObject collectionObject)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionObj = collectionObject;
		collectionParser();
	}

	public LayerCollection getLayerCollection() {
		return layerCollection;
	}

	public void setLayerCollection(LayerCollection layerCollection) {
		this.layerCollection = layerCollection;
	}

	public LayerCollection getEditedLayerCollection() {
		return editedLayerCollection;
	}

	public void setEditedLayerCollection(LayerCollection editedLayerCollection) {
		this.editedLayerCollection = editedLayerCollection;
	}

	public LayerCollection getCreatedLayerCollection() {
		return createdLayerCollection;
	}

	public void setCreatedLayerCollection(LayerCollection createdLayerCollection) {
		this.createdLayerCollection = createdLayerCollection;
	}

	public LayerCollection getDeletedLayerCollection() {
		return deletedLayerCollection;
	}

	public void setDeletedLayerCollection(LayerCollection deletedLayerCollection) {
		this.deletedLayerCollection = deletedLayerCollection;
	}

	/**
	 * JSONObject를 QA20LayerCollection 객체로 파싱 
	 * @author DY.Oh 
	 * @Date 2017. 3. 11. 오후 2:16:10 
	 * @throws ParseException void 
	 * @throws
	 */
	public void collectionParser() throws ParseException {

		LayerList layerList = new LayerList();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String layerName = (String) iterator.next();
			JSONObject layerObj = (JSONObject) collectionObj.get(layerName);
			EditLayerParser layerParser = new EditLayerParser(layerObj);
			Layer layer = layerParser.getLayer();
			layerList.add(layer);
		}
		this.layerCollection = new LayerCollection("", layerList);
	}
}
