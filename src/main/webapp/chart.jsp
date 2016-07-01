<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
		
		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<!-- Additional files for the Highslide popup effect -->
<script src="https://www.highcharts.com/samples/static/highslide-full.min.js"></script>
<script src="https://www.highcharts.com/samples/static/highslide.config.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="https://www.highcharts.com/samples/static/highslide.css" />

		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">
		$(document).ready(function() {
			//function for receving  the date selected
			function sravya(date){
				alert(date);

			//chart to be displayed when date is selected
			var options1 = {
				chart: {
					renderTo: 'report',
					type:'spline'
					
				},
				title: {
					text: 'Temparature analysis'
				},
				xAxis: {
				        
					categories: []
				},
				tooltip: {
                shared: true,
                crosshairs: true,
              
            },
				 
				yAxis: {
					title: {
						text: 'Temparature'
					}
				},
				series: []
			};	
			
			$.get('temp.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
					
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');

					if (lineNo==0) {

            			$.each(items, function(itemNo, item) {
	                		if(itemNo==0){
	                    		options1.series.push({
	                        	name: item,
	                        	data: []
	                   			});
	                   			
	                		}
	                		
	                		
            			});
            			
        			} 
        			else {

            			$.each(items, function(itemNo, item) {
                			if (itemNo === 0) { /* first item containes year */
                    			options1.xAxis.categories.push(item);

                			} 
                			else if(itemNo===1) {  //each other value for series  + parsing prevent nulls*/
                    					options1.series[itemNo-1].data.push(parseFloat(item));
                    				
                				
                			}
                			else if(item == "null") { /* adding nulls */
                    			options1.series[itemNo - 1].data.push(null);
                			}
            			});

        			}
									
				});
				
				var chart = new Highcharts.Chart(options1);
			});
}
			//intial charts with all categories
			var options = {
				chart: {
					renderTo: 'container',
					type:'spline'
					
				},
				title: {
					text: 'Temparature analysis'
				},
				xAxis: {
				        
					categories: []
				},
				tooltip: {
                shared: true,
                crosshairs: true,
           
                
            },
				 plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function (e) {
                            	if(this.series.name=='temparature'){
                               /* hs.htmlExpand(null, {
                                    
                                    headingText: this.series.xAxis.categories[this.x],
                                    maincontentText: Highcharts.dateFormat('%a %d %b %H:%M', this.x) + ':<br/> ' +
                                        this.y + ' visits', width: 200

                                });*/
								//to send date selected
								sravya(this.series.xAxis.categories[this.x]);
								//var chart = new Highcharts.Chart(options1);
                            }
                            else if(this.series.name=='pressure'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else if(this.series.name=='time'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else{
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }

                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },

				yAxis: {
					title: {
						text: 'Temparature'
					}
				},
				series: []
			};	
			
			$.get('data1.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
					
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');
					if (lineNo === 0) {
            			$.each(items, function(itemNo, item) {
	                		if(itemNo>0){
	                			
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
                			else if (itemNo===1) { /* each other value for series  + parsing prevent nulls*/
                    			options.series[itemNo-1].data.push(parseFloat(item));

                			}
                			else if (itemNo===2) { /* each other value for series  + parsing prevent nulls*/
                    			options.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===3) { /* each other value for series  + parsing prevent nulls*/
                    			options.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===4) { /* each other value for series  + parsing prevent nulls*/
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

		
		
		<!-- 3. Add the container -->
		<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>
		<div  id="report"></div>
				
	</body>
</html>