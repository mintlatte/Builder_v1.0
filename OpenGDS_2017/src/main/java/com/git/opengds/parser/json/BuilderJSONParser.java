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

package com.git.opengds.parser.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.parser.GeoLayerCollectionParser;
import com.git.gdsbuilder.type.simple.collection.LayerCollectionList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.opengds.parser.validate.ValidateTypeParser;

/**
 * ValidateJSON을 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:08:24
 */
public class BuilderJSONParser {

	/**
	 * JSONObject를 ValidateLayerTypeList, LayerCollectionList로 파싱 @author
	 * DY.Oh @Date 2017. 4. 18. 오후 4:08:26 @param j @return
	 * HashMap<String,Object> @throws FileNotFoundException @throws
	 * IOException @throws ParseException @throws SchemaException @throws
	 */
	public static HashMap<String, Object> parseValidateObj(String j)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		System.out.println("이건?");

		HashMap<String, Object> validateMap = new HashMap<String, Object>();

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(j);

		// 타입검수 파싱
		JSONArray typeValidates = (JSONArray) jsonObj.get("typeValidate");
		ValidateTypeParser validateTypeParser = new ValidateTypeParser(typeValidates);
		ValidateLayerTypeList validateLayerTypeList = validateTypeParser.getValidateLayerTypeList();

		// 도엽들 파싱
		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		GeoLayerCollectionParser collectionParser = new GeoLayerCollectionParser(layerCollections);
		GeoLayerCollectionList collectionList = collectionParser.getLayerCollections();
		if (collectionList.size() == 0 && validateLayerTypeList.size() == 0) {
			return null;
		} else {
			validateMap.put("typeValidate", validateLayerTypeList);
			validateMap.put("collectionList", collectionList);
			return validateMap;
		}
	}

	public static HashMap<String, Object> parseEditObj(JSONObject collectionListObj) {

		LayerCollectionList collectionList = new LayerCollectionList();
		JSONArray editCollection = (JSONArray) collectionListObj.get("editCollections");

		return collectionListObj;
	}
}
