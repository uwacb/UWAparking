 var map, heatmap;

      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 16,
          center: {lat: -31.980624, lng: 115.818479},
          mapTypeId: 'roadmap'
        });

        heatmap = new google.maps.visualization.HeatmapLayer({
          data: getPoints(),
          map: map
        });

      var contentString_9p= '<div id="content_9p">'+
          '<div id="parkNotice">'+
          '</div>'+
          '<div id="bodyContent">'+
          'CSSE south park lots, Yellow permission: 20 Red permission: 20 Ticket: 8:00am-5:00pm'+
          '</div>'+
          '</div>';

      var infowindow = new google.maps.InfoWindow({
        content:contentString_9p
      });


      


      var marker = { lat: -31.977570,  lng:115.816864};

      var icon_parking = {
       url: "/image/parking.png",
       scaledSize: new google.maps.Size(30,30) 
      };

      var marker_display = new google.maps.Marker({
        position: marker,
        map: map,
        title :' csse parking area',
        icon: icon_parking
      });


      marker_display.addListener('click', function(){
      
      infowindow.open(map, marker_display);

      });

      }

      function toggleHeatmap() {
        heatmap.setMap(heatmap.getMap() ? null : map);
      }

      function changeGradient() {
        var gradient = [
          'rgba(0, 255, 255, 0)',
          'rgba(0, 255, 255, 1)',
          'rgba(0, 191, 255, 1)',
          'rgba(0, 127, 255, 1)',
          'rgba(0, 63, 255, 1)',
          'rgba(0, 0, 255, 1)',
          'rgba(0, 0, 223, 1)',
          'rgba(0, 0, 191, 1)',
          'rgba(0, 0, 159, 1)',
          'rgba(0, 0, 127, 1)',
          'rgba(63, 0, 91, 1)',
          'rgba(127, 0, 63, 1)',
          'rgba(191, 0, 31, 1)',
          'rgba(255, 0, 0, 1)'
        ]
        heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
      }

      function changeRadius() {
        heatmap.set('radius', heatmap.get('radius') ? null : 20);
      }

      function changeOpacity() {
        heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
      }


      // Heatmap data from db
      //Google Points API
      //Heatmap data: example Points
      //function getPoints() {
      //  return [
      //    new google.maps.LatLng(37.782551, -122.445368)]

      function getPoints() {
            points= [];  // initial arrary
        
          <% for ( var i =0; i<data.length; i++) {%>   //ejs variable and render's result
            
            point_obj= new google.maps.LatLng(<%=data[i].Lat%>,<%=data[i].Lng%>);
            points.push(point_obj);  //let points collect all point_obk
         
          <% } %>
         
          return  points;  //render it to the map
          
        //];
      }

      