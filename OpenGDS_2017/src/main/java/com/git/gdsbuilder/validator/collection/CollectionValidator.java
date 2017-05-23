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

package com.git.gdsbuilder.validator.collection;

import java.util.List;

import org.geotools.feature.SchemaException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.type.validate.option.Z_ValueAmbiguous;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;

/**
 * ValidateLayerCollectionList를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:30:17
 */
public class CollectionValidator {

	ValidateLayerCollectionList validateLayerCollectionList;
	ErrorLayerList errLayerList;

	/**
	 * CollectionValidator 생성자
	 * 
	 * @param validateLayerCollectionList
	 * @throws NoSuchAuthorityCodeException
	 * @throws SchemaException
	 * @throws FactoryException
	 * @throws TransformException
	 */
	public CollectionValidator(ValidateLayerCollectionList validateLayerCollectionList)
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException {
		this.validateLayerCollectionList = validateLayerCollectionList;
		collectionValidate();
	}

	/**
	 * validateLayerCollectionList getter @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:23 @return ValidateLayerCollectionList @throws
	 */
	public ValidateLayerCollectionList getValidateLayerCollectionList() {
		return validateLayerCollectionList;
	}

	/**
	 * validateLayerCollectionList setter @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:24 @param validateLayerCollectionList void @throws
	 */
	public void setValidateLayerCollectionList(ValidateLayerCollectionList validateLayerCollectionList) {
		this.validateLayerCollectionList = validateLayerCollectionList;
	}

	/**
	 * errLayerList getter @author DY.Oh @Date 2017. 4. 18. 오후 3:30:26 @return
	 * ErrorLayerList @throws
	 */
	public ErrorLayerList getErrLayerList() {
		return errLayerList;
	}

	/**
	 * errLayerList setter @author DY.Oh @Date 2017. 4. 18. 오후 3:30:30 @param
	 * errLayerList void @throws
	 */
	public void setErrLayerList(ErrorLayerList errLayerList) {
		this.errLayerList = errLayerList;
	}

	// typeValidate

	/**
	 * validateLayerCollectionList를 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:30:31
	 * @throws SchemaException
	 * @throws NoSuchAuthorityCodeException
	 * @throws FactoryException
	 * @throws TransformException void
	 * @throws
	 * */
	public void collectionValidate()
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException {

		this.errLayerList = new ErrorLayerList();
		ValidateLayerTypeList types = validateLayerCollectionList.getValidateLayerTypeList();
		GeoLayerCollectionList layerCollections = validateLayerCollectionList.getLayerCollectionList();
		
		for (int j = 0; j < layerCollections.size(); j++) {
			GeoLayerCollection collection = layerCollections.get(j);
			GeoLayer neatLayer = collection.getNeatLine();
			ErrorLayer errLayer = new ErrorLayer();
			for (int i = 0; i < types.size(); i++) {
				// getType
				ValidateLayerType type = types.get(i);
				// getTypeLayers
				GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), collection);
				// getTypeOption
				List<ValidatorOption> options = type.getOptionList();
				// typeValidate
				ErrorLayer typeErrorLayer = null;
				for (int k = 0; k < options.size(); k++) {
					ValidatorOption option = options.get(k);
					// typeLayerValidate
					for (int a = 0; a < typeLayers.size(); a++) {
						GeoLayer typeLayer = typeLayers.get(a);
						System.out.println(typeLayer.getLayerName());
						LayerValidatorImpl layerValidator = new LayerValidatorImpl(typeLayer);
						if (option instanceof ConBreak) {
							typeErrorLayer = layerValidator.validateConBreakLayers(neatLayer);
						}
						if (option instanceof ConIntersected) {
							typeErrorLayer = layerValidator.validateConIntersected();
						}
						if (option instanceof ConOverDegree) {
							double degree = ((ConOverDegree) option).getDegree();
							typeErrorLayer = layerValidator.validateConOverDegree(degree);
						}
						if (option instanceof Z_ValueAmbiguous) {
							String key = ((Z_ValueAmbiguous) option).getAttributeKey();
							typeErrorLayer = layerValidator.validateZ_ValueAmbiguous(key);
						}
						if (option instanceof UselessPoint) {
							typeErrorLayer = layerValidator.validateUselessPoint();
						}
						if (option instanceof EntityDuplicated) {
							typeErrorLayer = layerValidator.validateEntityDuplicated();
						}
						if (option instanceof OutBoundary) {
							List<String> relationNames = ((OutBoundary) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateOutBoundary(
										validateLayerCollectionList.getTypeLayers(relationNames.get(r), collection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof SmallArea) {
							double area = ((SmallArea) option).getArea();
							typeErrorLayer = layerValidator.validateSmallArea(area);
						}
						if (option instanceof SmallLength) {
							double length = ((SmallLength) option).getLength();
							typeErrorLayer = layerValidator.validateSmallLength(length);
						}
						if (option instanceof SelfEntity) {
							List<String> relationNames = ((SelfEntity) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateSelfEntity(
										validateLayerCollectionList.getTypeLayers(relationNames.get(r), collection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof OverShoot) {
							double tolerence = ((OverShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateOverShoot(neatLayer, tolerence);
						}
						if (option instanceof UnderShoot) {
							double tolerence = ((UnderShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateUnderShoot(neatLayer, tolerence);
						}
						if (typeErrorLayer != null) {
							errLayer.mergeErrorLayer(typeErrorLayer);
						}
					}
				}
			}
			errLayer.setCollectionName(collection.getCollectionName());
			errLayerList.add(errLayer);
	}
	}
	// closeValidate

}
