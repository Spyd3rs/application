<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>

<div id="page-wrapper">
	<div class="row">
		<div id="container" style="width: 700px; height: 300px"></div>
	</div>
</div>
<script>
	$(function() {

		var highchartInput = {

			chart : {
				renderTo : 'container',
				type : 'column'
			},
			yAxis : {
				title : {
					text : 'axis title',
					useHTML : true,
					style : {
						rotation : 90
					}
				}
			},
			xAxis : {
				type : 'category',
				labels : {
					rotation : -45,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			},
			title : {
				text : "Negative Sentiments"
			}

		};

		$.ajax({
			url : "neg_aspects",
			success : function(data) {
				onSuccess(data);
			}
		});

		/*
		 Handlebars.registerHelper('fullName', function(person) {
		 return person.firstName + " " + person.lastName;
		 });*/
		function onSuccess(data) {
			jsonData = $.parseJSON(data);

			highchartInput.series = [ {
				data : jsonData
			} ];
			var chart2 = new Highcharts.Chart(highchartInput);
		}

	});
</script>
<%@ include file="footer.jsp"%>
