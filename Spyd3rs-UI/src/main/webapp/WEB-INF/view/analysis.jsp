<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>
<style>
.center-polarity {
	text-align: center;
	color: black;
	font-weight: bolder;
}

.color-code--1 {
	background: #FF7F50;
}

.color-code-0 {
	background: #6495ED;
}

.color-code-1 {
	background: #7FFFD4;
}
</style>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Aspect Information</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div>Semantic analysis</div>
					<div class="input-group custom-search-form">
						<input type="text" class="form-control" id="searchField" placeholder="HotelID Search...">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" id="searchButton">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</div>

				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="dataTable_wrapper" id="tableDiv"></div>
					<!-- /.table-responsive -->

				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
</div>
<!-- /#page-wrapper -->

<script id="aspect-table-template" type="text/x-handlebars-template"> 
<table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Aspect</th>
                                            <th>Polarity</th>
                                        </tr>
                                    </thead>
                                    <tbody>
									{{#aspects}}
                                        <tr class="odd gradeX">
                                            <td>{{aspect}}</td>
                                            <td class="center-polarity color-code-{{polarity}}">{{polarity}}</td>
                                        </tr>
									{{/aspects}}                      
                                    </tbody>
                                </table>
</script>
<script>
	var source = $("#aspect-table-template").html();
	var template = Handlebars.compile(source);

	var data = {
		aspects : [ {
			aspect : "presentation",
			polarity : 1
		}, {
			aspect : "hotel",
			polarity : -1
		}, {
			aspect : "staff",
			polarity : -1
		} , {
			aspect : "pool",
			polarity : 0
		} ]
	};

	function getAnalysisData(hotelId){
		$.ajax({url: "analysis_data/"+hotelId, success: function(data){
			onSuccess(data);
	    }});
	}
	$("#searchButton").click(function(e){
		var hotelId = $("#searchField").val();
		getAnalysisData(hotelId);
		$("#dataTables-example").remove();
	});
	
	
	/*
	 Handlebars.registerHelper('fullName', function(person) {
	 return person.firstName + " " + person.lastName;
	 });*/
	function onSuccess(data){
		 jsonData = $.parseJSON(data);
		$('#tableDiv').append(template(jsonData)); 
	 }
</script>
<%@ include file="footer.jsp"%>