package com.git.gdsbuilder.type.simple.layer;

import java.util.Hashtable;

import com.git.gdsbuilder.type.simple.feature.FeatureList;

public class Layer {

	String layerName;
	String layerType;
	Hashtable<String, Object> layerColumns;
	FeatureList featureList;

	public Layer(String layerName, String layerType, Hashtable<String, Object> layerColumns, FeatureList featureList) {
		super();
		this.layerName = layerName;
		this.layerType = layerType;
		this.layerColumns = layerColumns;
		this.featureList = featureList;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public Hashtable<String, Object> getLayerColumns() {
		return layerColumns;
	}

	public void setLayerColumns(Hashtable<String, Object> layerColumns) {
		this.layerColumns = layerColumns;
	}

	public FeatureList getFeatureList() {
		return featureList;
	}

	public void setFeatureList(FeatureList featureList) {
		this.featureList = featureList;
	}

}
