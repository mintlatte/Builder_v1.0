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

package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
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
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.algorithm.CentroidPoint;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class FeatureGraphicValidatorImpl implements FeatureGraphicValidator {

	@Override
	public List<ErrorFeature> validateConBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coordinates.length - 1];
		GeometryFactory geometryFactory = new GeometryFactory();

		if (!start.equals2D(end)) {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				if (geometry.intersection(aopGeom) != null) {
					Coordinate[] temp = new Coordinate[] { start, end };
					// DataConvertor convertService = new DataConvertor();
					String errFeatureID = simpleFeature.getID();
					int tempSize = temp.length;

					for (int i = 0; i < tempSize; i++) {
						Geometry returnGeom = geometryFactory.createPoint(temp[i]);
						if (Math.abs(returnGeom.distance(aopGeom)) > 0.2 || returnGeom.crosses(aopGeom)) {
							ErrorFeature errFeature = new ErrorFeature(errFeatureID, ConBreak.Type.CONBREAK.errType(),
									ConBreak.Type.CONBREAK.errName(), returnGeom);
							errFeatures.add(errFeature);
						}
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.intersects(geometryJ)) {
			Geometry returnGeom = geometryI.intersection(geometryJ);
			for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
				ErrorFeature errFeature = new ErrorFeature(simpleFeatureI.getID(),
						ConIntersected.Type.CONINTERSECTED.errType(), ConIntersected.Type.CONINTERSECTED.errName(),
						returnGeom.getGeometryN(i));

				errFeatures.add(errFeature);
			}
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateConOverDegree(SimpleFeature simpleFeature, double inputDegree)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;
		for (int i = 0; i < coorSize - 2; i++) {
			Coordinate a = coordinates[i];
			Coordinate b = coordinates[i + 1];
			Coordinate c = coordinates[i + 2];
			if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
				double angle = Angle.toDegrees(Angle.angleBetween(a, b, c));
				if (angle < inputDegree) {
					String errFeatureID = simpleFeature.getID();
					GeometryFactory geometryFactory = new GeometryFactory();
					Point errPoint = geometryFactory.createPoint(b);
					ErrorFeature errFeature = new ErrorFeature(errFeatureID, ConOverDegree.Type.CONOVERDEGREE.errType(),
							ConOverDegree.Type.CONOVERDEGREE.errName(), errPoint);
					errFeatures.add(errFeature);
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateUselessPoint(SimpleFeature simpleFeature)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coors = geometry.getCoordinates();
		int coorsSize = coors.length;

		CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
		CoordinateReferenceSystem crs = factory.createCoordinateReferenceSystem("EPSG:3857");
		for (int i = 0; i < coorsSize - 1; i++) {
			Coordinate a = coors[i];
			Coordinate b = coors[i + 1];

			// 길이 조건
			double tmpLength = a.distance(b);
			double distance = JTS.orthodromicDistance(a, b, crs);

			// double km = distance / 1000;
			// double meters = distance - (km * 1000);

			if (tmpLength == 0) {
				CentroidPoint cPoint = new CentroidPoint();
				cPoint.add(a);
				cPoint.add(b);

				Coordinate coordinate = cPoint.getCentroid();
				GeometryFactory gFactory = new GeometryFactory();
				Geometry returnGeom = gFactory.createPoint(coordinate);

				ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
						 UselessPoint.Type.USELESSPOINT.errType(), UselessPoint.Type.USELESSPOINT.errName(),
						returnGeom.getGeometryN(i));
				errFeatures.add(errFeature);
			}
			if (i < coorsSize - 2) {
				// 각도 조건
				Coordinate c = coors[i + 2];
				if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
					double tmpAngle = Angle.toDegrees(Angle.angleBetween(a, b, c));
					if (tmpAngle < 6) {
						GeometryFactory gFactory = new GeometryFactory();
						Geometry returnGeom = gFactory.createPoint(b);
						ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
								 UselessPoint.Type.USELESSPOINT.errType(), UselessPoint.Type.USELESSPOINT.errName(),
								returnGeom.getGeometryN(i));
						errFeatures.add(errFeature);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}

	}

	@Override
	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException {

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.equals(geometryJ)) {
			ErrorFeature errFeature = new ErrorFeature(simpleFeatureI.getID(),
					 EntityDuplicated.Type.ENTITYDUPLICATED.errType(), EntityDuplicated.Type.ENTITYDUPLICATED.errName(),
					geometryI.getInteriorPoint());
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public ErrorLayer validatePointDuplicated(SimpleFeatureCollection validatorLayer) throws SchemaException {
		return null;

		// ErrorLayer errLayer = new ErrorLayer();
		// DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		// List<DetailsValidatorResult> dtReports = new
		// ArrayList<DetailsValidatorResult>();
		//
		// GeometryValidator geometryValidator = new GeometryValidatorImpl();
		// SimpleFeatureIterator simpleFeatureIterator =
		// validatorLayer.features();
		// while (simpleFeatureIterator.hasNext()) {
		// SimpleFeature simpleFeature = simpleFeatureIterator.next();
		// List<ErrorFeature> errFeatures =
		// geometryValidator.pointDuplicated(simpleFeature);
		// if (errFeatures != null) {
		// for (ErrorFeature tmp : errFeatures) {
		// errSFC.add(tmp.getErrFeature());
		// dtReports.add(tmp.getDtReport());
		// }
		// } else {
		// continue;
		// }
		// }
		// if (errSFC.size() > 0 && dtReports.size() > 0) {
		// errLayer.setErrFeatureCollection(errSFC);
		// errLayer.setDetailsValidatorReport(dtReports);
		// return errLayer;
		// } else {
		// return null;
		// }

	}

	@Override
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		String geomIType = geometryI.getGeometryType();
		Geometry returnGeom = null;
		if (geomIType.equals("Point") || geomIType.equals("MultiPoint")) {
			returnGeom = selfEntityPoint(geometryI, geometryJ);
		}
		if (geomIType.equals("LineString") || geomIType.equals("MultiLineString")) {
			returnGeom = selfEntityLineString(geometryI, geometryJ);
		}
		if (geomIType.equals("Polygon") || geomIType.equals("MultiPolygon")) {
			returnGeom = selfEntityPolygon(geometryI, geometryJ);
		}
		if (returnGeom != null) {
			for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
				ErrorFeature errFeature = new ErrorFeature(simpleFeatureI.getID(), SelfEntity.Type.SELFENTITY.errType(),
						SelfEntity.Type.SELFENTITY.errName(),
						 returnGeom.getGeometryN(i).getInteriorPoint());
				errFeatures.add(errFeature);
			}
			return errFeatures;
		} else {
			return null;
		}
	}

	private Geometry selfEntityPoint(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.intersects(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.intersects(geometryJ) || geometryI.touches(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityLineString(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}

		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {

			if (geometryI.crosses(geometryJ) && !geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.crosses(geometryJ.getBoundary()) || geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ.getBoundary());
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityPolygon(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.getBoundary().crosses(geometryJ) || geometryI.contains(geometryJ)) {
				returnGeom = geometryI.getBoundary().intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (!geometryI.equals(geometryJ)) {
				if (geometryI.overlaps(geometryJ) || geometryI.within(geometryJ) || geometryI.contains(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
				}
			}
		}
		return returnGeom;
	}

	@Override
	public ErrorFeature validateSmallArea(SimpleFeature simpleFeature, double inputArea) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (geometry.getGeometryType().equals("Polygon")) {
			double area = geometry.getArea();
			if (area < inputArea) {
				String errFeatureID = simpleFeature.getID();
				ErrorFeature errFeature = new ErrorFeature(errFeatureID, 
						SmallArea.Type.SMALLAREA.errType(), SmallArea.Type.SMALLAREA.errName(), geometry.getInteriorPoint());
				return errFeature;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature validateSmallLength(SimpleFeature simpleFeature, double inputLength) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		double length = geometry.getLength();
		if (length < inputLength) {
			String errFeatureID = simpleFeature.getID();
			ErrorFeature errFeature = new ErrorFeature(errFeatureID, 
					SmallLength.Type.SMALLLENGTH.errType(), SmallLength.Type.SMALLLENGTH.errName(), geometry.getInteriorPoint());
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature validateOutBoundary(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature)
			throws SchemaException {

		Geometry geometryI = (Geometry) simpleFeature.getDefaultGeometry();
		Geometry geometryJ = (Geometry) relationSimpleFeature.getDefaultGeometry();

		if (geometryI.overlaps(geometryJ)) {
			Geometry result = geometryI.intersection(geometryJ);
			if (!result.equals(geometryJ) || result == null || result.getNumPoints() == 0) {
				Geometry returnGome = geometryJ.getInteriorPoint();
				ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
						OutBoundary.Type.OUTBOUNDARY.errType(), OutBoundary.Type.OUTBOUNDARY.errName(),  returnGome);
				return errFeature;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateOverShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry(); // feature
		SimpleFeatureIterator iterator = aop.features();
		while (iterator.hasNext()) {
			SimpleFeature simpleFeatureJ = iterator.next();
			Geometry relationGeometry = (Geometry) simpleFeatureJ.getDefaultGeometry(); // aop
			Geometry aopBuffer = relationGeometry.buffer(tolerence); // tolerence
																		// : 사용자
			// 입력 파라미터
			Geometry envelope = relationGeometry.getEnvelope();
			GeometryFactory factory = new GeometryFactory();
			Coordinate[] featureCoor = geometry.getCoordinates();
			for (int i = 0; i < featureCoor.length; i++) {
				Geometry featurePt = factory.createPoint(featureCoor[i]);
				if (!aopBuffer.contains(featurePt)) {
					if (!envelope.contains(featurePt)) {
						ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
								OverShoot.Type.OVERSHOOT.errType(), OverShoot.Type.OVERSHOOT.errName(),  featurePt);
						errFeatures.add(errFeature);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateUnderShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry(); // feature
		SimpleFeatureIterator iterator = aop.features();
		while (iterator.hasNext()) {
			SimpleFeature relationSimpleFeature = iterator.next();
			Geometry geometryRelation = (Geometry) relationSimpleFeature.getDefaultGeometry(); // aop
			Geometry aopBuffer = geometryRelation.buffer(tolerence); // tolerence
																		// : 사용자
			// 입력 파라미터
			Geometry envelope = geometryRelation.getEnvelope();
			GeometryFactory factory = new GeometryFactory();

			if (envelope.contains(geometry)) {
				Coordinate[] featureCoor = geometry.getCoordinates();
				Geometry featurePt1 = factory.createPoint(featureCoor[0]);
				Geometry featurePt2 = factory.createPoint(featureCoor[featureCoor.length - 1]);
				if (!featurePt1.equals(featurePt2)) {
					if (!aopBuffer.contains(featurePt1)) {
						ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
								UnderShoot.Type.UNDERSHOOT.errType(),	UnderShoot.Type.UNDERSHOOT.errName(),  featurePt1);
						errFeatures.add(errFeature);
					}
					if (!aopBuffer.contains(featurePt2)) {
						ErrorFeature errFeature = new ErrorFeature(simpleFeature.getID(),
								UnderShoot.Type.UNDERSHOOT.errType(),	UnderShoot.Type.UNDERSHOOT.errName(),  featurePt1);
						errFeatures.add(errFeature);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}
}
