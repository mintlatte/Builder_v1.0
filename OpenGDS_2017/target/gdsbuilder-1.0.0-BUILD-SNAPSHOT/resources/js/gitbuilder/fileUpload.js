// 슬기
	// ==========================================================================================================
	/*코딩시작*/

var gitbuilder = {};
//변수객체
gitbuilder.variable = {};
gitbuilder.variable.elementid = {};
gitbuilder.ui = {};
gitbuilder.ui.elementid = {};
gitbuilder.method ={};
	/**
	 * @description myDropzone 변수 선언
	 */
	gitbuilder.variable.myDropzone ={};
	
	/**
	 * @description myDropzone 변수 선언
	 */
	gitbuilder.variable.uploadFlag = false;
	
	
	gitbuilder.ui.NewGeneralizationV2Window = function NewGeneralizationV2Window(flag){
	    if (!gitbuilder.variable.elementid.generalizationV2Window) {
		var generalizationV2WindowId = "generalizationV2Window";
		gitbuilder.variable.elementid.generalizationV2Window = generalizationV2WindowId;
		
		
		var generDiaContent = "<div class='modal fade' id='"+generalizationV2WindowId+"' tabindex='-1' data-focus-on='input:first' role='dialog'>";
		generDiaContent += '<div class="modal-dialog modal-lg">';
		generDiaContent += '<div class="modal-content">';
		generDiaContent += '<div class="modal-header">';
		generDiaContent += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		generDiaContent += '<h4 class="modal-title">Generalization</h4>';
		generDiaContent += '</div>';
		generDiaContent += '<div class="modal-body" style="height: 37%;overflow-y: scroll;display: block;">';
		generDiaContent += '<div class="tbList">';
		generDiaContent += '<table class="table table-hover table-bordered text-center">';
		generDiaContent += '<colgroup><col width="5%"/><col width="20%"/><col width="20%"/><col width="15%"/><col width="19%"/><col width="21%"/>';
		generDiaContent += '<thead>';
		generDiaContent += '<tr>';
		generDiaContent += '<th style="text-align: center;"><div class="checkbox checkbox-primary checkbox-single " style="margin-top: 0px;margin-bottom: 0px;"><input type="checkbox" id="allGV2Chkbox" name="allGV2Chkbox"><label></label></div></th>';
		generDiaContent += '<th style="text-align: center;"><strong>Layer Type</strong></th>';
		generDiaContent += '<th style="text-align: center;"><strong>Layer Name</strong></th>';
		generDiaContent += '<th style="text-align: center;"><strong>Step</strong></th>';
		generDiaContent += '<th style="text-align: center;"><strong>Order</strong></th>';
		generDiaContent += '<th style="text-align: center;"><strong>Detail Information</strong></th>';
		generDiaContent += '</tr>';
		generDiaContent += '</thead>';
		generDiaContent += '<tbody id="genLayerManage2TableBody">';
		generDiaContent += '</tbody>';
		generDiaContent += '</table>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		generDiaContent += '<div class="modal-footer">';
		generDiaContent += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
		generDiaContent += '<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="del()">Delete</button>';
		generDiaContent += '<button type="button" class="btn btn-primary" data-dismiss="modal">Request</button>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		generDiaContent += '</div>';
		
		$("body").append(generDiaContent);
	    }
	    if(flag!="A"){
		$('#' + gitbuilder.variable.elementid.generalizationV2Window).modal('show');
	    }
	}
	
	
	
	
	/**
	 * @description 파일 업로드 Window 추가
	 */
	gitbuilder.ui.NewFileWindow = function NewFileWindow(){
		if (!gitbuilder.variable.elementid.fileWindow){
			var fileWindowId = "fileWindow";
			gitbuilder.variable.elementid.fileWindow = fileWindowId;

			var fileWindow = "<div class='modal fade' id='" + fileWindowId + "' tabindex='-1' role='dialog'>";
			fileWindow += '<div class="modal-dialog" style="width: 800px;">';
			fileWindow += '<div class="modal-content">';
			fileWindow += '<div class="modal-header">';
			fileWindow += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
			fileWindow += '<h4 class="modal-title">File Upload</h4>';
			fileWindow += '</div>';
			fileWindow += '<div class="modal-body" >';
			
			/*파일 업로드 상위 버튼 추가*/
			fileWindow += '<div id="actions" class="row">';
			fileWindow += '<div class="col-lg-12 text-right">';
			fileWindow += '<select class="form-control col-xs-2" style="padding-bottom: 1%; width: 16.7%" id="filetype">';
			fileWindow += '<option value="dxf">DXF</option>';	
			fileWindow += '<option value="ngi">NGI</option>';	
			fileWindow += '<option value="shp">SHP</option>';	
			fileWindow += '</select>';
			//중복파일 
		/*	fileWindow += '<div class="checkbox checkbox-primary checkbox-single" >';
			fileWindow += '<input type="checkbox" id="isdupbox"><label>Do you want to overwrite duplicate files?</label>';
			fileWindow += '</div>'*/
        	fileWindow += '<!-- The fileinput-button span is used to style the file input field as button -->';
        	fileWindow += '<span id="addfileBtn" class="btn btn-success fileinput-button">';
        	fileWindow += '<i class="glyphicon glyphicon-plus"></i>';
        	fileWindow += '<span>Add files...</span>';
        	fileWindow += '</span>';
        	fileWindow += '<button id="submitBtn" type="submit" class="btn btn-primary start">';
        	fileWindow += '<i class="glyphicon glyphicon-upload"></i>';
        	fileWindow += '<span>Start upload</span>';
        	fileWindow += '</button>';
        	fileWindow += '<button id="resetBtn" type="reset" class="btn btn-warning cancel">';
        	fileWindow += '<i class="glyphicon glyphicon-ban-circle"></i>';
        	fileWindow += '<span>Cancel upload</span>';
        	fileWindow += '</button>';
        	fileWindow += '</div>';
        	fileWindow += '<div>';
        	fileWindow += '</div>';
        	fileWindow += '<div class="col-lg-12" style="padding-top:15px;">';
        	fileWindow += '<!-- The global file processing state -->';
        	fileWindow += '<span class="fileupload-process">';
        	fileWindow += '<div id="total-progress" class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">';
        	fileWindow += '<div id="total-progress-in" class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress></div>';
        	fileWindow += '</div>';
        	fileWindow += '</span>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	
        	/*파일 업로드 테이블*/
        	fileWindow += '<div id="uploadborder">';
        	fileWindow += '<div class="file-message">';
        	fileWindow += '<h2>Drop files here or "Add files" click to upload.</h2>';
        	fileWindow += '</div>';
            fileWindow += '<div class="table table-striped files" id="previews">';
        	fileWindow += '<div id="template" class="file-row">';
        	fileWindow += '<!-- This is used as the file preview template -->';
        	fileWindow += '<div style="width : 10%">';
        	fileWindow += '<span class="preview"><img data-dz-thumbnail /></span>';
        	fileWindow += '</div>';
        	fileWindow += '<div style="width:65%">';
        	fileWindow += '<p class="name" data-dz-name></p>';
        	fileWindow += '<strong class="error text-danger" data-dz-errormessage></strong>';
        	fileWindow += '<strong class="success text-success" data-dz-successmessage></strong>';
        	fileWindow += '<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">';
        	fileWindow += '<div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress></div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '<div style="width:10%">';
        	fileWindow += '<p class="size" data-dz-size></p>';
        	fileWindow += '</div>';
        	fileWindow += '<div style="width:15%">';
        	fileWindow += '<button class="btn btn-primary start" style="display: none;">';
        	fileWindow += '<i class="glyphicon glyphicon-upload"></i>';
        	fileWindow += '<span>Start</span>';
        	fileWindow += '</button>';
        	fileWindow += '<button data-dz-remove class="btn btn-warning cancel">';
        	fileWindow += '<i class="glyphicon glyphicon-ban-circle"></i>';
        	fileWindow += '<span>Cancel</span>';
        	fileWindow += '</button>';
        	fileWindow += '<button data-dz-remove class="btn btn-danger delete">';
        	fileWindow += '<i class="glyphicon glyphicon-trash"></i>';
        	fileWindow += '<span>Delete</span>';
        	fileWindow += '</button>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	
        	fileWindow += '<div class="modal-footer">';
        	fileWindow += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
        	fileWindow += '</div>';
			$("body").append(fileWindow);
			
			
			// Get the template HTML and remove it from the doument
			var previewNode = document.querySelector("#template");
			previewNode.id = "";
			var previewTemplate = previewNode.parentNode.innerHTML;
			previewNode.parentNode.removeChild(previewNode);
			
			// Show the total progress bar when upload starts
	        document.querySelector("#total-progress").style.opacity = "1";

			var myDropzone = new Dropzone(document.body, { // Make the whole body a dropzone
				url: "http://localhost:8080/opengds/file/fileUpload.do", // Set the url
				thumbnailWidth: 80,
			        thumbnailHeight: 80,
			        parallelUploads: 20,
			        maxFiles: 100,
			        maxFilesize: 100,
			        previewTemplate: previewTemplate,
			        acceptedFiles:".dxf",
			        autoQueue: false, // Make sure the files aren't queued until manually added
			        previewsContainer: "#previews", // Define the container to display the previews
			        clickable: ".fileinput-button" // Define the element that should be used as click trigger to select files.
			      });
			      
			      gitbuilder.variable.myDropzone = myDropzone;

			      myDropzone.on("addedfile", function(file) {
				if(!file.type.match(/image.*/)){
				    var beforestr = file.name.toLocaleLowerCase();
				    if(/dxf$/.test(beforestr)){
					myDropzone.emit("thumbnail", file, "http://localhost:8080/opengds/resources/img/dxf.png");
				    }
				    if(/ngi$/.test(beforestr)){
					myDropzone.emit("thumbnail", file, "http://localhost:8080/opengds/resources/img/ngi.png");
				    }
				    if(/nda$/.test(beforestr)){
					myDropzone.emit("thumbnail", file, "http://localhost:8080/opengds/resources/img/nda.png");
				    }
				    if(/shp$/.test(beforestr)){
					myDropzone.emit("thumbnail", file, "http://localhost:8080/opengds/resources/img/shp.png");
				    }
				}else{
			        // Hookup the start button
			        file.previewElement.querySelector(".start").onclick = function() { myDropzone.enqueueFile(file); };
				}
			    });

			      // Update the total progress bar
			      myDropzone.on("totaluploadprogress", function(progress) {
			        document.querySelector("#total-progress .progress-bar").style.width = progress + "%";
			      });

			      myDropzone.on("sending", function(file) {
			        // And disable the start button
			        file.previewElement.querySelector(".start").setAttribute("disabled", "disabled");
			      });

			      // Hide the total progress bar when nothing's uploading anymore
			     /* myDropzone.on("queuecomplete", function(progress) {
			        document.querySelector("#total-progress").style.opacity = "0";
			      });*/

			      // Setup the buttons for all transfers
			      // The "add files" button doesn't need to be setup because the config
			      // `clickable` has already been specified.
			      document.querySelector("#actions .start").onclick = function() {
			        myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED));
			      };
			      document.querySelector("#actions .cancel").onclick = function() {
			        myDropzone.removeAllFiles(true);
			      };
			
			      
				
				$('#filetype').on('change', function(){
				    //Dropzone 파일포맷 유효성 검사옵션 추가
				    var selectType = $("#filetype option:selected").val();
				    if(selectType == 'dxf'){
						gitbuilder.variable.myDropzone.options.acceptedFiles = ".dxf";
						gitbuilder.variable.myDropzone.hiddenFileInput.setAttribute("accept",".dxf");
				    }
				    else if(selectType =='ngi'){
						gitbuilder.variable.myDropzone.options.acceptedFiles = ".ngi,.nda";
						gitbuilder.variable.myDropzone.hiddenFileInput.setAttribute("accept",".ngi,.nda");
				    }
				    else if(selectType =='shp'){
						gitbuilder.variable.myDropzone.options.acceptedFiles = ".shp,.dbf,.shx,.prj";
						gitbuilder.variable.myDropzone.hiddenFileInput.setAttribute("accept",".shp,.dbf,.shx,.prj");
				    }
				    gitbuilder.variable.myDropzone.initFileTypeChange();
				});
			      $(".file-message").show();
		}
		$('#' + gitbuilder.variable.elementid.fileWindow).modal('show');
	}
	

	gitbuilder.method.fileDuplicateCheck = function(fileName) {
		var returnFlag = false;
		if (fileName != null) {
			var url = CONTEXT + "/file/fileNameDupCheckAjax.ajax";
			var params = {
				fileName : fileName
			};
			sendJsonRequest(url, params, result);
		} else {
			throw new Error("File name error.");
		}
}
	

	
	