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

package com.git.opengds.geoserver.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.opengds.geoserver.service.GeoserverLayerProxyService;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.geoserver.service.GeoserverServiceImpl;


/** 
* @ClassName: GeoserverController 
* @Description: Geoserver 관련된 요청을 처리한다.
* @author JY.Kim 
* @date 2017. 4. 3. 오후 2:16:03 
*  
*/
@Controller("geoserver2Controller")
@RequestMapping("/geoserver2")
public class GeoserverController{

	@Autowired
	private GeoserverService geoserverService = new GeoserverServiceImpl();
	
	@Autowired
	private GeoserverLayerProxyService proService = new GeoserverLayerProxyService();
	
	/**
	 * 트리 형태의 GeoLayerCollection 객체 생성
	 * @author JY.Kim
	 * @Date 2017. 4. 7. 오후 5:31:59
	 * @return JSONObject - 트리 형태의 GeoLayerCollection 객체
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGeolayerCollectionTree.ajax")
	@ResponseBody
	public JSONArray getGeolayerCollectionTree(HttpServletRequest request){
		JSONArray array = geoserverService.getGeoserverLayerCollectionTree();
		return array;
	}
 
 	/**
 	 * WMS레이어 요청
 	 * @author SG.Lee
 	 * @Date 2017. 4
 	 * @param request
 	 * @param response
 	 * @throws ServletException
 	 * @throws IOException void
 	 * @throws
 	 * */
 	@RequestMapping(value="geoserverWMSLayerLoad.do")
	@ResponseBody
	public void geoserverWMSLoad(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		proService.requestWMSLayer(request, response);
	}
 	
 	@RequestMapping(value="geoserverWFSLayerLoad.do")
	@ResponseBody
	public void geoserverWFSLoad(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		proService.requestWFSLayer(request, response);
	}
	
	/**
	 * Geoserver Layer 조회
	 * @author SG.Lee
	 * @Date 2017. 4
	 * @param request
	 * @param jsonObject
	 * @return DTGeoLayerList
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getGeoLayerInfoList.ajax")
	@ResponseBody
	public DTGeoLayerList getGeoLayerList(HttpServletRequest request , @RequestBody JSONObject jsonObject){
		List<String> geoLayerList = new ArrayList<String>();
		geoLayerList = (ArrayList<String>) jsonObject.get("geoLayerList");
		if(geoLayerList.size()==0){
			return null;
		}
		else
			return geoserverService.getGeoLayerList((ArrayList<String>) geoLayerList);
	}
	
	
	/**
	 * Geoserver Group레이어 조회
	 * @author SG.Lee
	 * @Date 2017. 4
	 * @param request
	 * @param jsonObject
	 * @return DTGeoLayerList
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getGeoGroupLayerInfoList.ajax")
	@ResponseBody
	public DTGeoGroupLayerList getGeoGroupLayerInfoList(HttpServletRequest request , @RequestBody JSONObject jsonObject){
		List<String> geoLayerList = new ArrayList<String>();
		geoLayerList = (ArrayList<String>) jsonObject.get("geoLayerList");
		if(geoLayerList.size()==0){
			return null;
		}
		else
			return geoserverService.getGeoGroupLayerList((ArrayList<String>) geoLayerList);
	}
}
