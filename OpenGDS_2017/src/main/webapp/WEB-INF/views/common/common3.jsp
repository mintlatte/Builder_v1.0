<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%-- 제이쿼리 --%>
<script src="${ctx}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<%-- 공통함수 --%>
<script src="${ctx}/resources/js/common/common.js"></script> 

<%-- 업데이트중 - SG.Lee --%>
<script src="${ctx}/resources/js/gitbuilder/fileUpload.js"></script>

<%-- 제이쿼리UI --%>
<script src="${ctx}/resources/js/jqueryui/jquery-ui.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/jqueryui/jquery-ui.min.css">

<%-- 스펙트럼 --%>
<script src="${ctx}/resources/js/spectrum/spectrum.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/spectrum/spectrum.css">

<%-- 오픈 레이어즈3 --%>
<script src="${ctx}/resources/js/ol3/ol-debug.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/ol3/ol.css">

<%-- PROS4JS --%>
<script src="${ctx}/resources/js/proj4js/dist/proj4-src.js"></script>

<%-- JSTS --%>
<script src="${ctx}/resources/js/jsts/jsts.min.js"></script>

<%-- 부트스트랩 --%>
<script src="${ctx}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/bootstrap/css/bootstrap.min.css">

<%-- 데이터 테이블 --%>
<script type="text/javascript" src="${ctx}/resources/js/datatables/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/datatables/css/jquery.dataTables.min.css"/>

<%-- 폰트어썸 --%>
<link rel="stylesheet" href="${ctx}/resources/css/fontawesome/css/font-awesome.min.css"/>

<%-- jsTree openlayers3--%>
<script type="text/javascript" src="${ctx}/resources/js/jsTree-openlayers3/jstree.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jsTree-openlayers3/themes/default/style.css"/>

<%-- jsTree geoserver--%>
<script type="text/javascript" src="${ctx}/resources/js/jsTree-geoserver/jstree.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jsTree-geoserver/themes/default/style.css"/>

<%-- 베이스맵 변경 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/changebase.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder2017.css">

<%-- 레이어 코드 정의 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerdefinition.js"></script>

<%-- 검수 옵션 정의 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/optiondefinition.js"></script>

<%-- 검수창 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/validation.js"></script>
<script src="${ctx}/resources/js/gitbuilder/changebase.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/gitbuilder/gitbuilder2017.css">

<%-- 검수 편집 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/qaedit.js"></script>

<%-- 편집툴 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/editingtool.js"></script>

<%-- wms편집이력 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/edithistory.js"></script>

<%-- 커스텀 인터랙션 --%>
<script src="${pageContext.request.contextPath}/resources/js/gitbuilder/interaction.js"></script>

<%-- 파일업로드 --%>
<script src="${ctx}/resources/js/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="${ctx}/resources/css/fileupload/jquery.fileupload-ui.css">

<%-- 지오코더 --%>
<link href="${ctx}/resources/js/ol3-geocoder/ol3-geocoder.css" rel="stylesheet"/>
<script src="${ctx}/resources/js/ol3-geocoder/ol3-geocoder.js"></script>

<%-- 알림 --%>
<link rel="stylesheet" href="${ctx}/resources/js/sweetalert/sweetalert.css">
<script src="${ctx}/resources/js/sweetalert/sweetalert.min.js"></script>
<script src="${ctx}/resources/js/sweetalert/sweetalert.js"></script>

<!-- sockjs -->
<%-- <script src="${ctx}/resources/js/sockjs/sockjs.min.js"></script> --%>

<!-- stomp -->
<%-- <script src="${ctx}/resources/js/stomp/stomp.min.js"></script> --%>

<script type="text/javascript">
var CONTEXT = "${pageContext.request.contextPath}";
</script>