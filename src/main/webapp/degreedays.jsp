<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Degree Days</title>
		
		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">
		$(document).ready(function() {
			
			var options = {
				chart: {
					renderTo: 'container',
					type:'spline'
					
				},
				title: {
					text: 'Degree Days'
				},
				xAxis: {
					categories: []
				},
				yAxis: {
					title: {
						text: 'Temparature(F)'
					}
				},
				series: []
			};
			
			/*
			 Load the data from the CSV file. This is the contents of the file:
			 
				Apples,Pears,Oranges,Bananas,Plums
				John,8,4,6,5
				Jane,3,4,2,3
				Joe,86,76,79,77
				Janet,3,16,13,15
				
			 */ 
			$.get('data.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');
					if (lineNo === 0) {
            			$.each(items, function(itemNo, item) {
	                		if (itemNo == 0) { // "Date" word in first line
	                    		options.series.push({
	                        	name: item,
	                        	data: []
	                   			});
	                		}
            			});
        			} 
        			else {
            			$.each(items, function(itemNo, item) {
                			if (itemNo === 0) { /* first item containes year */
                    			options.xAxis.categories.push(item);
                			} 
                			else if (parseFloat(item)) { /* each other value for series  + parsing prevent nulls*/
                    			options.series[itemNo - 1].data.push(parseFloat(item));
                			}
                			else if (item == "null") { /* adding nulls */
                    			options.series[itemNo - 1].data.push(null);
                			}
            			});

        			}
									
				});
				
				var chart = new Highcharts.Chart(options);
			});		
		});
		</script>
		
	</head>
<body>
	<div>
		<form:form action="${pageContext.request.contextPath}/degreedays" method="post" name="factors">
        	<div class="">
                                 <input type="text" class="" value="${startdate}" id="startdate" name="startdate" placeholder="Start Date">
                                   
                                    <input type="text" class="" value="${enddate}" id="enddate" name="enddate" placeholder="End Date" >
                                    
                                    <input type="text" class="" value="${minTempTheshold}" id="minTempTheshold" name="minTempTheshold"  placeholder="Min Temp Threshold">
                                   
                                    <input type="text" class="" value="${maxTempTheshold}" id="maxTempTheshold" name="maxTempTheshold"  placeholder="Max Temp Threshold">
                                    
                                    <input type="text" class="" value="${minHumTheshold}" id="minHumTheshold" name="minHumTheshold"  placeholder="Min Hum Threshold">
                                   
                                    <input type="text" class="" value="${maxWindTheshold}" id="maxWindTheshold" name="maxWindTheshold"  placeholder="Max Wind Threshold">
                                    
                                <button type="submit" class="">Search</button>
                            </div>                    
                            	                 
        </form:form>
	</div>
	<div id="container" style="width: 1200px; height: 800px; margin: 0 auto"></div>
		
	<!-- Bootstrap Core JavaScript -->
    <script src="resources/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="resources/js/classie.js"></script>
    <script src="resources/js/cbpAnimatedHeader.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="resources/js/jqBootstrapValidation.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="resources/js/agency.js"></script>
</body>
</html>
