<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>

<html lang="en" class="no-js">

<head>

    <!-- meta data -->

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name=viewport content="width=device-width, initial-scale=1">

    <!-- title and favicon -->

    <title>Delicato Analysis</title>
    <link rel="icon" href="resources/img/icon/fav_icon.gif">
    

    <!--necessary stylesheets -->

    <link type="text/css" rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="assets/css/ionicons.min.css">
    <link type="text/css" rel="stylesheet" href="assets/css/popup.css">
    <link type="text/css" rel="stylesheet" href="assets/css/owl.carousel.css">
    <link type="text/css" rel="stylesheet" href="assets/css/owl.theme.css">
    <link type="text/css" rel="stylesheet" href="assets/css/style.css">    
    
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!--[if IE]>
		<style>
		    .flip-container:hover .back,
			.flip-container.hover .back {
			    backface-visibility: visible !important;
			}
		</style>
	<![endif]-->

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Degree Days</title>

<link rel="stylesheet" type="text/css" href="https://www.highcharts.com/samples/static/highslide.css" />

<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

  		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/data.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>

<!-- Additional files for the Highslide popup effect -->

<script src="https://www.highcharts.com/samples/static/highslide-full.min.js"></script>
<script src="https://www.highcharts.com/samples/static/highslide.config.js" charset="utf-8"></script>
  <script type="text/javascript">
  $(function () {
	  var chart = new Highcharts.Chart({
	        chart: {
	            type: 'spline',
	            renderTo: 'comparisioncontainer'
	        },
	        title: {
	            text: 'Snow depth at Vikjafjellet, Norway'
	        },
	        subtitle: {
	            text: 'Irregular time data in Highcharts JS'
	        },
	        xAxis: {
	            type: 'datetime',
	            dateTimeLabelFormats: { // don't display the dummy year
	                month: '%e. %b',
	                year: '%b'
	            },
	            title: {
	                text: 'Date'
	            }
	        },
	        yAxis: {
	            title: {
	                text: 'Snow depth (m)'
	            },
	            min: 0
	        },
	        tooltip: {
	            headerFormat: '<b>{series.name}</b><br>',
	            pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
	        },

	        plotOptions: {
	            spline: {
	                marker: {
	                    enabled: true
	                }
	            }
	        },

	        series: [{
	            name: 'Winter 2012-2013',
	            // Define the data points. All series have a dummy year
	            // of 1970/71 in order to be compared on the same x axis. Note
	            // that in JavaScript, months start at 0 for January, 1 for February etc.
	            data: [
	                [Date.UTC(1970, 9, 21), 0],
	                [Date.UTC(1970, 10, 4), 0.28],
	                [Date.UTC(1970, 10, 9), 0.25],
	                [Date.UTC(1970, 10, 27), 0.2],
	                [Date.UTC(1970, 11, 2), 0.28],
	                [Date.UTC(1970, 11, 26), 0.28],
	                [Date.UTC(1970, 11, 29), 0.47],
	                [Date.UTC(1971, 0, 11), 0.79],
	                [Date.UTC(1971, 0, 26), 0.72],
	                [Date.UTC(1971, 1, 3), 1.02],
	                [Date.UTC(1971, 1, 11), 1.12],
	                [Date.UTC(1971, 1, 25), 1.2],
	                [Date.UTC(1971, 2, 11), 1.18],
	                [Date.UTC(1971, 3, 11), 1.19],
	                [Date.UTC(1971, 4, 1), 1.85],
	                [Date.UTC(1971, 4, 5), 2.22],
	                [Date.UTC(1971, 4, 19), 1.15],
	                [Date.UTC(1971, 5, 3), 0]
	            ]
	        }, {
	            name: 'Winter 2013-2014',
	            data: [
	                [Date.UTC(1970, 9, 29), 0],
	                [Date.UTC(1970, 10, 9), 0.4],
	                [Date.UTC(1970, 11, 1), 0.25],
	                [Date.UTC(1971, 0, 1), 1.66],
	                [, ],
	                [, ],
	                [, ],
	                [, ]
	            ]
	        }, {
	            name: 'Winter 2014-2015',
	            data: [
	                [Date.UTC(1970, 07, 24), 2.87],
	                [Date.UTC(1970, 08, 01), 2.97],
	                [, ],
	                [, ],
	                [, ],
	                [, ]
	            ]
	        }]
	    });
	});
  </script>
  
  
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
			
			
			$.get('degreedays.csv', function(data) {
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
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			//function sravya(date){
				//alert(date);

			//chart to be displayed when date is selected
			var hourly1 = {
				chart: {
					renderTo: 'report',
					type:'spline'
					
				},
				title: {
					text: 'Hourly Graph'
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
						text: 'Units'
					}
				},
				series: []
			};	
			
			$.get('cimis2.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
					
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');

					if (lineNo==0) {

            			$.each(items, function(itemNo, item) {
								if(itemNo>0&& itemNo<6){
	                			
	                    		hourly1.series.push({
	                        	name: item,
	                        	data: []
	                   			});
	                   			
	                		}
            			});
        			} 
        			else {

            			$.each(items, function(itemNo, item) {
                			if (itemNo === 0) { /* first item containes year */
                    			hourly1.xAxis.categories.push(item);

                			} 
                			else if (itemNo===1) { /* each other value for series  + parsing prevent nulls*/
                    			hourly1.series[itemNo-1].data.push(parseInt(item));

                			}
                			else if (itemNo===2) { /* each other value for series  + parsing prevent nulls*/
                    			hourly1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===3) { /* each other value for series  + parsing prevent nulls*/
                    			hourly1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===4) { /* each other value for series  + parsing prevent nulls*/
                    			hourly1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===5) { /* each other value for series  + parsing prevent nulls*/
                    			hourly1.series[itemNo - 1].data.push(parseFloat(item));

                			}
            			});

        			}
									
				});
				
				var chart = new Highcharts.Chart(hourly1);
			});
//}
			//intial charts with all categories
			var graph1 = {
				chart: {
					renderTo: 'cimiscontainer',
					type:'spline'
					
				},
				title: {
					text: 'Daily Graph'
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
                            	//if(this.series.name=='temparature'){
                               /*hs.htmlExpand(null, {
                                    
                                    headingText: this.series.xAxis.categories[this.x],
                                    maincontentText: Highcharts.dateFormat('%a %d %b %H:%M', this.x) + ':<br/> ' +
                                        this.y + ' visits', width: 200

                                });*/
								//to send date selected
								//sravya(this.series.xAxis.categories[this.x]);
                                var chart = new Highcharts.Chart(hourly1);
								senddate(this.series.xAxis.categories[this.x]);
								
								/* }
                            else if(this.series.name=='pressure'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else if(this.series.name=='time'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else{
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }*/

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
						text: 'Units'
					}
				},
				series: []
			};	
			
			$.get('cimis1.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
					
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');
					if (lineNo === 0) {
            			$.each(items, function(itemNo, item) {
	                		if(itemNo>0&& itemNo<6){
	                			
	                    		graph1.series.push({
	                        	name: item,
	                        	data: []
	                   			});
	                   			
	                		}
            			});
        			} 
        			else {
            			$.each(items, function(itemNo, item) {
                			if (itemNo === 0) { /* first item containes year */
                    			graph1.xAxis.categories.push(item);

                			} 
                			else if (itemNo===1) { /* each other value for series  + parsing prevent nulls*/
                    			graph1.series[itemNo-1].data.push(parseFloat(item));

                			}
                			else if (itemNo===2) { /* each other value for series  + parsing prevent nulls*/
                    			graph1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo===3) { /* each other value for series  + parsing prevent nulls*/
                    			graph1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo==4) { /* each other value for series  + parsing prevent nulls*/
                    			graph1.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			else if (itemNo==5) { /* each other value for series  + parsing prevent nulls*/
                    			graph1.series[itemNo - 1].data.push(parseFloat(item));

                			}


                			/*else if (item == "null") { /* adding nulls 
                    			graph1graph1]].series[itemNo - 1].data.push(null);
                			}*/
            			});

        			}
									
				});
				
				var chart = new Highcharts.Chart(graph1);
			});	
			var hourly2 = {
					chart: {
						renderTo: 'report1',
						type:'spline'
						
					},
					title: {
						text: 'Hourly Graph'
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
							text: 'Units'
						}
					},
					series: []
				};	
				
				$.get('cimis4.csv', function(data) {
					// Split the lines
					var lines = data.split('\n');
						
					$.each(lines, function(lineNo, line) {
						var items = line.split(',');

						if (lineNo==0) {

	            			$.each(items, function(itemNo, item) {
									if(itemNo>0&& itemNo<3){
		                			
		                    		hourly2.series.push({
		                        	name: item,
		                        	data: []
		                   			});
		                   			
		                		}
	            			});
	        			} 
	        			else {

	            			$.each(items, function(itemNo, item) {
	                			if (itemNo === 0) { /* first item containes year */
	                    			hourly2.xAxis.categories.push(item);

	                			} 
	                			else if (itemNo===1) { /* each other value for series  + parsing prevent nulls*/
	                    			hourly2.series[itemNo-1].data.push(parseFloat(item));

	                			}
	                			else if (itemNo===2) { /* each other value for series  + parsing prevent nulls*/
	                    			hourly2.series[itemNo - 1].data.push(parseFloat(item));

	                			}
	                			
	            			});

	        			}
										
					});
					
					var chart = new Highcharts.Chart(hourly2);
				});
			var graph2 = {
				chart: {
					renderTo: 'cimiscontainer2',
					type:'spline'
					
				},
				title: {
					text: 'Daily Graph'
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
                            	//if(this.series.name=='temparature'){
                               /*hs.htmlExpand(null, {
                                    
                                    headingText: this.series.xAxis.categories[this.x],
                                    maincontentText: Highcharts.dateFormat('%a %d %b %H:%M', this.x) + ':<br/> ' +
                                        this.y + ' visits', width: 200

                                });*/
								//to send date selected
								//sravya(this.series.xAxis.categories[this.x]);
                                var chart = new Highcharts.Chart(hourly2);
								senddate(this.series.xAxis.categories[this.x]);
								
								/* }
                            else if(this.series.name=='pressure'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else if(this.series.name=='time'){
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }
                            else{
                                
								sravya(this.series.xAxis.categories[this.x]);
                            }*/

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
						text: 'Units'
					}
				},
				series: []
			};	
			
			$.get('cimis3.csv', function(data) {
				// Split the lines
				var lines = data.split('\n');
					
				$.each(lines, function(lineNo, line) {
					var items = line.split(',');
					if (lineNo === 0) {
            			$.each(items, function(itemNo, item) {
	                		if(itemNo>0&& itemNo<3){
	                			
	                    		graph2.series.push({
	                        	name: item,
	                        	data: []
	                   			});
	                   			
	                		}
            			});
        			} 
        			else {
            			$.each(items, function(itemNo, item) {
                			if (itemNo === 0) { /* first item containes year */
                    			graph2.xAxis.categories.push(item);

                			} 
                			else if (itemNo===1) { /* each other value for series  + parsing prevent nulls*/
                    			graph2.series[itemNo-1].data.push(parseFloat(item));

                			}
                			else if (itemNo===2) { /* each other value for series  + parsing prevent nulls*/
                    			graph2.series[itemNo - 1].data.push(parseFloat(item));

                			}
                			

                			/*else if (item == "null") { /* adding nulls 
                    			options.series[itemNo - 1].data.push(null);
                			}*/
            			});

        			}
									
				});
				
				var chart = new Highcharts.Chart(graph2);
			});
function senddate(id){
				
				
				//alert(id);
				document.getElementById('givendate').value = id;

				document.forms["hiddenfield"].submit();
	
				}
		});
			
		
		</script>
		
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
    
    var x1 = ${predValueBudBreakOne};
    var x2 = ${predValueBudBreakTwo};
    var x3 = ${predValueBudBreakThree};
    var x4 = ${predValueBudBreakFour};
    var x5 = ${predValueBudBreakFive};
    var x6 = ${predValueBudBreakSix};
    var x7 = ${predValueBudBreakSeven};
    var x8 = ${predValueBudBreakEight};
    var x9 = ${predValueBudBreakNine};
    var x10 = ${predValueBudBreakTen};
    
    google.load("visualization", "1", {packages:["corechart"]});
    
      google.setOnLoadCallback(drawChartBB);
      google.setOnLoadCallback(drawChartB);
      google.setOnLoadCallback(drawChartS);
      google.setOnLoadCallback(drawChartC);
      google.setOnLoadCallback(drawChartV);
      google.setOnLoadCallback(drawChartH);
      
      function drawChartBB() {

        var dataBB = google.visualization.arrayToDataTable([
          ['Budbreak', 'Number'],
          ['0 to 0.5',     x1],
          ['0.5 to 1',      x2],
          ['1 to 1.5',  x3],
          ['1.5 to 2', x4],
          ['2 to 2.5',  x5],
          ['2.5 to 3',     x6],
          ['3 to 3.5',      x7],
          ['3.5 to 4',  x8],
          ['4 to 4.5', x9],
          ['4.5 to 5',  x10]
        ]);

        var optionsBB = {
                title: 'Budbreak Prediction',
                is3D: true,
              };

              var chartBB = new google.visualization.PieChart(document.getElementById('budbreakchart'));
              chartBB.draw(dataBB, optionsBB);
            }
    
    var x1b = ${predValueBloomOne};
    var x2b = ${predValueBloomTwo};
    var x3b = ${predValueBloomThree};
    var x4b = ${predValueBloomFour};
    var x5b = ${predValueBloomFive};
    var x6b = ${predValueBloomSix};
    var x7b = ${predValueBloomSeven};
    var x8b = ${predValueBloomEight};
    var x9b = ${predValueBloomNine};
    var x10b = ${predValueBloomTen};
    
      
      function drawChartB() {

        var dataB = google.visualization.arrayToDataTable([
          ['Bloom', 'Number'],
          ['0 to 0.5',     x1b],
          ['0.5 to 1',      x2b],
          ['1 to 1.5',  x3b],
          ['1.5 to 2', x4b],
          ['2 to 2.5',  x5b],
          ['2.5 to 3',     x6b],
          ['3 to 3.5',      x7b],
          ['3.5 to 4',  x8b],
          ['4 to 4.5', x9b],
          ['4.5 to 5',  x10b]
        ]);

        var optionsB = {
                title: 'Bloom Prediction',
                is3D: true,
              };

              var chartB = new google.visualization.PieChart(document.getElementById('bloomchart'));
              chartB.draw(dataB, optionsB);
            }
    
    var x1s = ${predValueSetOne};
    var x2s = ${predValueSetTwo};
    var x3s = ${predValueSetThree};
    var x4s = ${predValueSetFour};
    var x5s = ${predValueSetFive};
    var x6s = ${predValueSetSix};
    var x7s = ${predValueSetSeven};
    var x8s = ${predValueSetEight};
    var x9s = ${predValueSetNine};
    var x10s = ${predValueSetTen};
      
      function drawChartS() {

        var dataS = google.visualization.arrayToDataTable([
          ['Set', 'Number'],
          ['0 to 0.5',     x1s],
          ['0.5 to 1',      x2s],
          ['1 to 1.5',  x3s],
          ['1.5 to 2', x4s],
          ['2 to 2.5',  x5s],
          ['2.5 to 3',     x6s],
          ['3 to 3.5',      x7s],
          ['3.5 to 4',  x8s],
          ['4 to 4.5', x9s],
          ['4.5 to 5',  x10s]
        ]);

        var optionsS = {
                title: 'Set Prediction',
                is3D: true,
              };

              var chartS = new google.visualization.PieChart(document.getElementById('setchart'));
              chartS.draw(dataS, optionsS);
            }
    
    var x1v = ${predValueClosureOne};
    var x2v = ${predValueClosureTwo};
    var x3v = ${predValueClosureThree};
    var x4v = ${predValueClosureFour};
    var x5v = ${predValueClosureFive};
    var x6v = ${predValueClosureSix};
    var x7v = ${predValueClosureSeven};
    var x8v = ${predValueClosureEight};
    var x9v = ${predValueClosureNine};
    var x10v = ${predValueClosureTen};
    
      
      function drawChartC() {

        var dataC = google.visualization.arrayToDataTable([
          ['Closure', 'Number'],
          ['0 to 0.5',     x1v],
          ['0.5 to 1',      x2v],
          ['1 to 1.5',  x3v],
          ['1.5 to 2', x4v],
          ['2 to 2.5',  x5v],
          ['2.5 to 3',     x6v],
          ['3 to 3.5',      x7v],
          ['3.5 to 4',  x8v],
          ['4 to 4.5', x9v],
          ['4.5 to 5',  x10v]
        ]);

        var optionsC = {
                title: 'Closure Prediction',
                is3D: true,
              };

              var chartC = new google.visualization.PieChart(document.getElementById('closurechart'));
              chartC.draw(dataC, optionsC);
            }
    
    var x1c = ${predValueVeraisonOne};
    var x2c = ${predValueVeraisonTwo};
    var x3c = ${predValueVeraisonThree};
    var x4c = ${predValueVeraisonFour};
    var x5c = ${predValueVeraisonFive};
    var x6c = ${predValueVeraisonSix};
    var x7c = ${predValueVeraisonSeven};
    var x8c = ${predValueVeraisonEight};
    var x9c = ${predValueVeraisonNine};
    var x10c = ${predValueVeraisonTen};
    
      
      function drawChartV() {

        var dataV = google.visualization.arrayToDataTable([
          ['Veraison', 'Number'],
          ['0 to 0.5',     x1c],
          ['0.5 to 1',      x2c],
          ['1 to 1.5',  x3c],
          ['1.5 to 2', x4c],
          ['2 to 2.5',  x5c],
          ['2.5 to 3',     x6c],
          ['3 to 3.5',      x7c],
          ['3.5 to 4',  x8c],
          ['4 to 4.5', x9c],
          ['4.5 to 5',  x10c]
        ]);

        var optionsV = {
                title: 'Veraison Prediction',
                is3D: true,
              };

              var chartV = new google.visualization.PieChart(document.getElementById('veraisonchart'));
              chartV.draw(dataV, optionsV);
            }
    
    var x1h = ${predValueHarvestOne};
    var x2h = ${predValueHarvestTwo};
    var x3h = ${predValueHarvestThree};
    var x4h = ${predValueHarvestFour};
    var x5h = ${predValueHarvestFive};
    var x6h = ${predValueHarvestSix};
    var x7h = ${predValueHarvestSeven};
    var x8h = ${predValueHarvestEight};
    var x9h = ${predValueHarvestNine};
    var x10h = ${predValueHarvestTen};
    
      
      function drawChartH() {

        var dataH = google.visualization.arrayToDataTable([
          ['Harvest', 'Number'],
          ['0 to 0.5',     x1h],
          ['0.5 to 1',      x2h],
          ['1 to 1.5',  x3h],
          ['1.5 to 2', x4h],
          ['2 to 2.5',  x5h],
          ['2.5 to 3',     x6h],
          ['3 to 3.5',      x7h],
          ['3.5 to 4',  x8h],
          ['4 to 4.5', x9h],
          ['4.5 to 5',  x10h]
        ]);

        var optionsH = {
                title: 'Harvest Prediction',
                is3D: true,
              };

              var chartH = new google.visualization.PieChart(document.getElementById('harvestchart'));
              chartH.draw(dataH, optionsH);
            }
          </script>
</head>


<body>
    
    <!-- Preloader -->
    
    <div id="preloader">
        <div class="loader">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
    </div>
    
    <!-- home-page -->
    
    <div class="home-page">
        
        <!-- Introduction -->
        
        <div class="introduction">
            <!-- <div class="mask">
            </div> -->
            <div class="intro-content">
                <!-- <h1>HELLO<br>
                I'M <span>JOHN</span> DOE</h1> -->
                <h1>
                    Delicato
                </h1> <br><br>
                <span>Analytics</span>
                <p class="slogan-text text-capitalize">Family Vineyards</p>

                <div class="social-media hidden-xs">
                    <a href="#" class="fa fa-facebook" data-toggle="tooltip" title="Facebook"></a>
                    <a href="#" class="fa fa-twitter" data-toggle="tooltip" title="Twitter"></a>
                    <a href="#" class="fa fa-plus" data-toggle="tooltip" title="Google+"></a>
                    <a href="#" class="fa fa-linkedin" data-toggle="tooltip" title="Linkedin"></a>
                    <a href="#" class="fa fa-behance" data-toggle="tooltip" title="Behance"></a>
                    <a href="#" class="fa fa-flickr" data-toggle="tooltip" title="Flicker"></a>
                    <a href="#" class="fa fa-instagram" data-toggle="tooltip" title="Instagram"></a>
                </div>
            </div>
            
            <!-- Social Media Icons [ END ] -->
        </div>
        
        <!-- Navigation Menu -->
        
        <div class="menu">
            <div data-url_target="dyostem" class="profile-btn menu_button">
                <img alt="" src="resources/img/about.jpg" style="width:100%; height:100%;">
                <div class="mask">
                </div>
                <div class="heading">
                    <i class="ion-ios-people-outline hidden-xs"></i>
                    <h2>Dyostem</h2>
                </div>
            </div>
            
            <!-- Single Navigation Menu Button -->
            
            <div data-url_target="cimis" class="portfolio-btn menu_button">
                <img alt="" src="resources/img/portfolio.jpg">
                <div class="mask">
                </div>
                <div class="heading">
                    <i class="ion-ios-briefcase-outline hidden-xs"></i>
                    <h2>Cimis</h2>
                </div>
            </div>
            
            <!-- Single Navigation Menu Button [ END ]  -->
            
            <div data-url_target="degreedays" class="service-btn menu_button">
                <img alt="" src="resources/img/service.jpg">
                <div class="mask">
                </div>
                <div class="heading">
                    <i class="ion-ios-lightbulb-outline hidden-xs"></i>
                    <h2>Degree Days</h2>
                </div>
            </div>
            
            <!-- Single Navigation Menu Button [ END ]  -->
            
            <div data-url_target="comparisions" class="contact-btn menu_button">
                <img alt="" src="resources/img/contact.jpg">
                <div class="mask">
                </div>
                <div class="heading">
                    <i class="ion-ios-chatboxes-outline hidden-xs"></i>
                    <h2>Comparisions</h2>
                </div>
            </div>
            
            <!-- Single Navigation Menu Button [ END ]  -->
            
        </div>
    </div>
    
    <!--
    4 ) Close Button
    -->
    
    <div class="close-btn"></div>
    
    <!--
    5 ) Profile Page
    -->

   


    <div id="dyostem" class="portfolio-page container-fluid page">

        <div class="row">

            <!--( a ) Portfolio Page Fixed Image Portion -->
            
            <div class="image-container col-md-3 col-sm-12">
                <div class="mask">
                </div>
                <div class="main-heading">
                    <h1>Dyostem</h1>
                </div>
            </div>

            <!--( b ) Portfolio Page Content -->
            
            <div class="content-container col-md-9 col-sm-12">
                
                <!--( A ) Portfolio -->
                
                <div class="portfolio clearfix full-height">
                    <h2 class="small-heading">DYOSTEM</h2>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="project-container">
                                <div class="row">
                                   
                                    
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                            
                </div>
                
                <!-- Footer -->
                
                <div class="footer clearfix">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-6 col-sm-12 col-xs-12">
                                    <p class="copyright">Copyright &copy; 2016 
                                        <a href="www.delicato.com">Delicato</a>
                                    </p>
                                </div>

                            </div>      
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   <!--  cimis data-->
 <div id="cimis" class="profile-page container-fluid page">
        <div class="row">
            <!--( a ) Profile Page Fixed Image Portion -->

            <div class="image-container col-md-3 col-sm-12">
                <div class="mask">
                </div>
                <div class="main-heading">
                    <h1>Cimis</h1>
                </div>
            </div>

            <!--( b ) Profile Page Content -->
	<div class="content-container col-md-9 col-sm-12">
	
	<div class="portfolio clearfix full-height">
                    <h2 class="small-heading">CIMIS DATA</h2>
                   
				<div style="width:80%; height:80%; display: block; margin-left: auto; margin-right: auto; margin-top: 20px">
			<form:form action="${pageContext.request.contextPath}/cimis#cimis" method="post" name="factors">
	        	<div class="">
	        			<input type="text" id="datepickerStartDate" value="${startdate}" name="startdate" placeholder="Start Date">
	        			<input type="text" id="datepickerEndDate" value="${enddate}" name="enddate" placeholder="End Date" >
	        			<!--<input type="text" class="" value="${getdate}" id="givendate" name="givendate" placeholder="get Date" >-->
	        		
	                    <button type="submit" class="">Search</button>
	                <br><br>
	             </div>                    
	               <div id="cimiscontainer" style="width: 50%; height: 80%; margin: 0 auto; float:left"></div>
				   <div id="cimiscontainer2" style="width: 50%; height: 80%; margin: 0 auto;float: right"></div>
	                            <br><br>
	         </form:form>     
	         <form:form action="${pageContext.request.contextPath}/cimissubgraph#cimis" method="post" name="hiddenfield" id="hiddenfield">     
				
				
				           
	                <div  id="report" style="width: 50%; height: 80%; margin: 0 auto; float:left"></div> 
	                <div  id="report1" style="width: 50%; height: 80%; margin: 0 auto; float:right"></div> 
	               <br><br>
	               <input type="hidden" id="givendate" name="givendate"/>
	               <input type="hidden" id="startdate" name="startdate"/>
	               <input type="hidden" id="enddate" name="enddate"/> 
	         </form:form>    
				     	                 
	       
		</div>
        </div>     

				
         
                <!--( D ) Footer -->

                <div class="footer clearfix">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-6 col-sm-12 col-xs-12">
                                    <p class="copyright">Copyright &copy; 2016 
                                        <a href="www.delicato.com">Delicato</a>
                                    </p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--
    7 ) Service Page
    -->
    
    <div id="degreedays" class="service-page container-fluid page">
     	 <div class="row">
            <!--( a ) Contact Page Fixed Image Portion -->
            
            <div class="image-container col-md-3 col-sm-12">
                <div class="mask">
                </div>
                <div class="main-heading">
                    <h1>Degree Days</h1>
                </div>
            </div>
            
            <!--( b ) Contact Page Content -->
            
            <div class="content-container col-md-9 col-sm-12">
                
                <!--( A ) Contact Form -->
                
                <div class="clearfix full-height">
                    <h2 class="small-heading">DEGREE DAYS</h2>
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div style="width:80%; height:80%; display: block; margin-left: auto; margin-right: auto; margin-top: 20px">
								<form:form action="${pageContext.request.contextPath}/degreedays#degreedays" method="post" name="factors">
						        	<div class="">
						        		<div style="float: left; width: 25%;">
						        			<input type="text" value="${startdate}" id="datepickerStartDateDD" name="startdate" placeholder="Start Date">
						        			<input type="text" value="${enddate}" id="datepickerEndDateDD" name="enddate" placeholder="End Date" >
						        		
						        		</div>
						        		<div style="float: left; width: 25%;">
						                                 
						                    <input type="text" class="" value="${minTempTheshold}" id="minTempTheshold" name="minTempTheshold"  placeholder="Min Temp Threshold">               
						                    <input type="text" class="" value="${maxTempTheshold}" id="maxTempTheshold" name="maxTempTheshold"  placeholder="Max Temp Threshold">
						                </div>
						                                    
						                <div style="float: left; width: 25%;">
						                    <input type="text" class="" value="${minHumTheshold}" id="minHumTheshold" name="minHumTheshold"  placeholder="Min Hum Threshold">
						                    <input type="text" class="" value="${maxWindTheshold}" id="maxWindTheshold" name="maxWindTheshold"  placeholder="Max Wind Threshold">
						                                    
						                </div>
						                <div style="float: left; width: 25%;">
						                    <button type="submit" class="">Search</button>
						                </div>
						             </div>                    
						                
										<br><br>            	                 
						        </form:form>
							</div>
							<div id="container" style="width:80%; height:80%; display: block; margin-left: auto; margin-right: auto"></div>

                            
                        </div>
                    </div>
                </div>
       

                <!-- Footer -->
                
                <div class="footer clearfix">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-6 col-sm-12 col-xs-12">
                                    <p class="copyright">Copyright &copy; 2016 
                                        <a href="HTTP://www.delicato.com">Delicato</a>
                                    </p>
                                </div>

                            </div>      
                        </div>
                    </div>
                </div>
            </div>
        </div>  
    </div>
    
    <!--
    8 ) Contact Page
    -->
    
    <div id="comparisions" class="contact-page container-fluid page">
        <div class="row">
            <!--( a ) Contact Page Fixed Image Portion -->
            
            <div class="image-container col-md-3 col-sm-12">
                <div class="mask">
                </div>
                <div class="main-heading">
                    <h1>Comparisions</h1>
                </div>
            </div>
            
            <!--( b ) Contact Page Content -->
            
            <div class="content-container col-md-9 col-sm-12">
                
                <!--( A ) Contact Form -->
                
                <div class="clearfix full-height">
                    <h2 class="small-heading">Comparision charts for the Block : ${blockC}</h2>
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="contact-info">
                                
                                    
                                <form:form action="${pageContext.request.contextPath}/comparisions#comparisions" method="post" name="comp">
						        	Select a Block to Compare:
						        	<select name="block" onchange="this.form.submit()">	
						        		<c:if  test="${!empty blocks}">
										<c:forEach items="${blocks}" var="block">
							         		<option value="${block}">${block}</option>
							         	
							            </c:forEach>
							            </c:if>
									</select>				        		
						        			<%-- <input type="text" value="${startdate}" id="datepickerStartDateDD" name="startdate" placeholder="Start Date">
						        			<input type="text" value="${enddate}" id="datepickerEndDateDD" name="enddate" placeholder="End Date" > --%>
						        		
						        		</form:form>  
						        		<div id="comparisioncontainer" style="width:80%; height:80%; "></div><br>
                                    
                            </div>
							<div class="contact-info">
                               <h2 class="small-heading">Predictions for the Block :   ${blockP}</h2>
                                    
                                <form:form action="${pageContext.request.contextPath}/predictions#comparisions" method="post" name="pred">
						        	Select a Block to Predict:
						        	<select name="block" onchange="this.form.submit()">	
						        		<c:if  test="${!empty blocks}">
										<c:forEach items="${blocks}" var="block">
							         		<option value="${block}">${block}</option>
							         	
							            </c:forEach>
							            </c:if>
									</select>				        		
						        			<%-- <input type="text" value="${startdate}" id="datepickerStartDateDD" name="startdate" placeholder="Start Date">
						        			<input type="text" value="${enddate}" id="datepickerEndDateDD" name="enddate" placeholder="End Date" > --%>
						        		
						        		</form:form> 
						        		
						        		<div id="budbreakchart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
						        		<p>Predicted Rating for Bud Break : ${predValueBudBreak}</p> 
						        		</c:if> 
						        		<div id="bloomchart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
						        		<p>Predicted Rating for Bloom : ${predValueBloom}</p>  
						        		</c:if>
						        		<div id="setchart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
						        		<p>Predicted Rating for Set : ${predValueSet}</p>
						        		</c:if>
						        		<div id="closurechart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
						        		<p>Predicted Rating for Closure : ${predValueClosure}</p> 
						        		</c:if>
						        		<div id="veraisonchart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
                               			<p>Predicted Rating for Veraison : ${predValueVeraison}</p> 
                               			</c:if>
						        		<div id="harvestchart" style="width:80%; height:80%;"></div>
						        		<c:if  test="${!empty predValueBudBreak}">
                              			<p>Predicted Rating for Harvest : ${predValueHarvest}</p> 
                              			</c:if>
                                   
                                    
                            </div>
                            
                        </div>
                    </div>
                </div>
                
                <!-- ( D ) Footer -->
                
                <div class="footer clearfix">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1">
                            <div class="row">
                                <div class="col-md-6 col-sm-12 col-xs-12">
                                    <p class="copyright">Copyright &copy; 2016 
                                        <a href="http://www.delicato.com">Delicato</a>
                                    </p>
                                </div>

                            </div>      
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!--  
    9 ) Javascript
    - -->
    
    <script type="text/javascript" src="assets/js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="assets/js/modernizr.js"></script>
    <script type="text/javascript" src="assets/js/jquery.easing.min.js"></script>
    <script type="text/javascript" src="assets/js/jquery.mixitup.min.js"></script>
    <script type="text/javascript" src="assets/js/jquery.popup.min.js"></script>
    <script type="text/javascript" src="assets/js/owl.carousel.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js"></script>
    <script type="text/javascript" src="assets/js/contact.js"></script>
    <script type="text/javascript" src="assets/js/script.js"></script>
    
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    
    <script>
  $(document).ready(function() {
    $("#datepickerStartDate").datepicker();
  });
  $(document).ready(function() {
	    $("#datepickerEndDate").datepicker();
	  });
  $(document).ready(function() {
	    $("#datepickerStartDateDD").datepicker();
	  });
	  $(document).ready(function() {
		    $("#datepickerEndDateDD").datepicker();
		  });
  </script>

</body>
</html>
