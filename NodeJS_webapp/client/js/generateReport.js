google.load('visualization', '1.0', {
  'packages': ['corechart']
});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {

  var this_js_script = $('script[src*=generateReport]');
  var jsondata = this_js_script.attr('data-json_value');
  var cdate = this_js_script.attr('data-req_date');

  jsondata = JSON.parse(jsondata);

  Date.prototype.yyyymmdd = function() {
    var yyyy = this.getFullYear().toString();
    var mm = (this.getMonth() + 1).toString(); // getMonth() is zero-based
    var dd = this.getDate().toString();
    return yyyy + "-" + (mm[1] ? mm : "0" + mm[0]) + "-" + (dd[1] ? dd : "0" + dd[0]); // padding
  };

  var d = new Date();
  d.yyyymmdd();

  var setDate = cdate;
  var data;
  
  var now = new Date();
		var oneYr = new Date();
		oneYr.setYear(now.getFullYear() + 1);
  
  if(setDate<'2014-12-31'){
			
			alert("Hotel created on 1st Jan 2015. Enter date after this date");
		}
	  else if(setDate>oneYr.yyyymmdd()){
			
			alert("Enter date within next year");
					
		}
		else if (setDate == d.yyyymmdd()) { // present day
    if(jsondata.occupiedrooms != null)
  {
     var occupiedCount = jsondata.occupiedrooms.length;
  }
  else
  {
    var occupiedCount = 0;
  }
    var notOccupied = jsondata.notOccupiedCount[0];
    if(jsondata.reservedrooms != null)
  {
     var reserved = jsondata.reservedrooms.length;
  }
  else
  {
    var reserved = 0;
  }

    // Create the data table.
    data = new google.visualization.DataTable();
    data.addColumn('string', 'Occupancy');
    data.addColumn('number', 'Count');

    data.addRows([
      ['Rooms Occupied: ' + occupiedCount, occupiedCount],
      ['Rooms Reserved: ' + reserved, reserved],
      ['Empty Rooms: ' + notOccupied, notOccupied],
    ]);

  }
  else if (setDate > d.yyyymmdd()) { //future day
    var notOccupied = jsondata.notOccupiedCount[0];
    
    if(jsondata.reservedrooms != null)
  {
     var reserved = jsondata.reservedrooms.length;
  }
  else
  {
    var reserved = 0;
  }

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Occupancy');
    data.addColumn('number', 'Count');

    data.addRows([
      ['Rooms Reserved: ' + reserved, reserved],
      ['Empty Rooms: ' + notOccupied, notOccupied]
    ]);
  }
  else if (setDate < d.yyyymmdd()) { // past date
  if(jsondata.occupiedrooms != null)
  {
     var occupiedCount = jsondata.occupiedrooms.length;
  }
  else
  {
    var occupiedCount = 0;
  }
   
    var notOccupied = jsondata.notOccupiedCount[0];

    // Create the data table.
    data = new google.visualization.DataTable();
    data.addColumn('string', 'Occupancy');
    data.addColumn('number', 'Count');

    data.addRows([
      ['Rooms Occupied: ' + occupiedCount, occupiedCount],
      ['Empty Rooms: ' + notOccupied, notOccupied]
    ]);
  }



  // Set chart options
  var options = {
    'title': 'Hotel availability status on '+cdate,
    'width': 900,
    'height': 500
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
  chart.draw(data, options);
}