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

		var chart2 = new Highcharts.Chart({

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
				},
				max : 1,
				min : -1
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
			series : [ {
				data : [ { name:'Staff', y:-1 }, { name:'Management', y:0 }, { name:'Pool', y:-1 },
						{ name:'Presentation', y:1 } ]
			} ],
			title:{
				text:"Sentimental Visualization"
			}

		});
	});
</script>
<%@ include file="footer.jsp"%>
