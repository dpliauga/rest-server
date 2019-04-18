$(document).ready( function() {
	
	loadAllVideos();
	
	$( "#createVideo" ).submit(function(event) {
		
		var video = { 'name': "'" + $("#videoName").val() + "'", 
				'url': "'" + $("#videoUrl").val() + "'" };
		
		var videoId = $("#videoId").val();
		var url = "/video"
		if(videoId > 0){
			url += "/" + videoId;
		}
		
		 $.ajax({
		        type: "POST",
		        url: url,
		        data: JSON.stringify(video),
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        statusCode: {
		            409: function() {
		            	$("#videoNameRequired").css("display", "block");
		            }
		        },
		        success: function(data){
		        	$('#createModal').modal('hide');
		        	loadAllVideos();
		        	},
		        failure: function(errMsg) {
		        }
		  });
		  event.preventDefault();
		});
	
	function loadAllVideos(){
		$.ajax({
	        type: "GET",
	        url: "/video",
	        dataType: "json",
	        statusCode: {
	            404: function() {
	            	$("#allVideos").html("<option value='' disabled>no videos found</option>");
	            }
	        },
	        success: function(videos){
	        	var html = "";
	        	videos.forEach(function(video) {	
	        		html += '<option value="' + video.id + '">' + video.name.replace(/'/g, '') + "</option>" ; 
	        		});
				$("#allVideos").html(html);
	        },
	        failure: function(errMsg) {
	        }
	  });
	}
	
	function loadVideo(id){
		$.ajax({
	        type: "GET",
	        url: "/video/" + id,
	        dataType: "json",
	        success: function(video){
	        	var videoName = video.name.replace(/'/g, '');
	        	var videoUrl = video.url.replace(/'/g, '');
	        	
	    		$("#videoName").val(videoName);
	    		$("#videoUrl").val(videoUrl);
	    		$("#videoId").val(video.id);
	    		$("#createModalLabel").html("Update video");
	    		$("#createButton").css("display", "none");
	    		$("#updateButton").css("display", "inline");
	    		$("#deleteButton").css("display", "inline");
	    		$('#createModal').modal('show');
	    		
	    		loadIframe(videoUrl);
	    		
	    		
	        },
	        failure: function(errMsg) {
	        }
	  });
		
	}
	
	$('#allVideos').click(function(){ 
	    var id = $(this).val();
	    loadVideo(id);
	});
	
	function loadIframe(videoUrl){
		if (videoUrl.indexOf("youtube") >= 0 && videoUrl.indexOf("/embed") <= 0 ){
			var rest = videoUrl.substring(0, videoUrl.lastIndexOf("/") + 1);
			var last = videoUrl.substring(videoUrl.lastIndexOf("/") + 1, videoUrl.length);
			var  restPart = last.substring(last.lastIndexOf("=") + 1, last.length);
			videoUrl = rest + "embed/" + restPart;
		}
		
		$('#videoIframe').css("height", "315px");
		$('#videoIframe').css("width", "420px");
		$('#videoIframe').css("margin", "0 auto");
		$('#videoIframe').attr("src", videoUrl);
	}
	
	$( "#videoUrl" )
	  .focusout(function() {
		  loadIframe($(this).val());
	  })
	
	$( "#deleteButton" ).click(function() {
		
		var videoId = $("#videoId").val();
		var url = "/video"
		if(videoId > 0){
			url += "/" + videoId;
			$.ajax({
		        type: "DELETE",
		        url: url,
		        contentType: "application/json; charset=utf-8",
		        dataType: "json",
		        success: function(data){
		        	$('#createModal').modal('hide');
		        	loadAllVideos();
		        	},
		        failure: function(errMsg) {
		        }
		  });
		}
	});
 });

function openCreatePopup(){
	$("#createModalLabel").html("Create a new video");
	$("#createButton").css("display", "inline");
	$("#updateButton").css("display", "none");
	$("#deleteButton").css("display", "none");
	$("#videoNameRequired").css("display", "none");
	$("#videoName").val("");
	$("#videoUrl").val("");
	$("#videoId").val("");
	$('#videoIframe').attr("src", "");
	$('#videoIframe').css("height", "0px");
	$('#videoIframe').css("width", "0px");
	$('#videoIframe').attr("src", videoUrl);
	$('#createModal').modal('show');
}