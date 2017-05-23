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

package com.git.gdsbuilder.type.geoserver.parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;

/**
 * JSONObject를 LayerCollection 객체로 파싱하는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:35:41
 * */
public class GeoLayerCollectionParser {

	JSONObject collectionObj;
	GeoLayerCollectionList layerCollections;
	
	/**
	 * LayerCollectionParser 생성자
	 * @param collectionObject
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerCollectionParser(JSONObject collectionObject)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionObj = collectionObject;
		collectionParser();
	}

	/**
	 * layerCollections getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:36:58
	 * @return LayerCollectionList
	 * @throws
	 * */
	public GeoLayerCollectionList getLayerCollections() {
		return layerCollections;
	}

	/**
	 * layerCollections setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:37:01
	 * @param layerCollections void
	 * @throws
	 * */
	public void setLayerCollections(GeoLayerCollectionList layerCollections) {
		this.layerCollections = layerCollections;
	}

	/**
	 * 레이어 정의정보가 저장되어있는 JSONObject를 LayerCollection 객체로 파싱하는 클래스
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:37:03
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException void
	 * @throws
	 * */
	private void collectionParser() throws FileNotFoundException, IOException, ParseException, SchemaException {

		JSONArray collectionNames = (JSONArray) collectionObj.get("collectionName");
		String neatLineLayerName = (String) collectionObj.get("neatLineLayer");
		JSONArray layerNames = (JSONArray) collectionObj.get("layers");

		this.layerCollections = new GeoLayerCollectionList();
		for (int i = 0; i < collectionNames.size(); i++) {
			// 도엽이름
			String collectionName = (String) collectionNames.get(i);
			GeoLayerCollection layerCollection = new GeoLayerCollection(collectionName);
			// 도곽선
			GeoLayerParser neatLineParser = new GeoLayerParser(collectionName, neatLineLayerName);
			GeoLayer neatLineLayer = neatLineParser.getLayer();
			layerCollection.setNeatLine(neatLineLayer);
			// 레이어
			GeoLayerParser layersParser = new GeoLayerParser(collectionName, layerNames);
			GeoLayerList layerList = layersParser.getLayerList();
			if(layerList != null) {
				layerCollection.setLayers(layerList);
				layerCollections.add(layerCollection);
			}
		}
	}
}
