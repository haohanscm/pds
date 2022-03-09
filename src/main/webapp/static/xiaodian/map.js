/**
 * Created by zgw on 2018/1/8.
 */
var map, district, polygons = [], citycode;
var citySelect = document.getElementById('city');
var districtSelect = document.getElementById('district');
var areaSelect = document.getElementById('street');
map = new AMap.Map('mapContainer', {
    resizeEnable: true,
    center: [116.30946, 39.937629],
    zoom: 3
});
AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
    map = new AMap.Map('mapContainer', {
        zoom: 16,
        scrollWheel: false
    })
    var positionPicker = new PositionPicker({
        mode: 'dragMap',
        map: map
    });

    positionPicker.on('success', function(positionResult) {
        document.getElementById('lnglat').innerHTML = positionResult.position;
        document.getElementById('address').innerHTML = positionResult.address;
        document.getElementById('nearestJunction').innerHTML = positionResult.nearestJunction;
        document.getElementById('nearestRoad').innerHTML = positionResult.nearestRoad;
        document.getElementById('nearestPOI').innerHTML = positionResult.nearestPOI;
    });
    positionPicker.on('fail', function(positionResult) {
        document.getElementById('lnglat').innerHTML = ' ';
        document.getElementById('address').innerHTML = ' ';
        document.getElementById('nearestJunction').innerHTML = ' ';
        document.getElementById('nearestRoad').innerHTML = ' ';
        document.getElementById('nearestPOI').innerHTML = ' ';
    });
    var onModeChange = function(e) {
        positionPicker.setMode(e.target.value)
    }
    var startButton = document.getElementById('start');
    var stopButton = document.getElementById('stop');
    var dragMapMode = document.getElementsByName('mode')[0];
    var dragMarkerMode = document.getElementsByName('mode')[1];
    AMap.event.addDomListener(startButton, 'click', function() {
        positionPicker.start(map.getBounds().getSouthWest())
    })
    AMap.event.addDomListener(stopButton, 'click', function() {
        positionPicker.stop();
    })
    AMap.event.addDomListener(dragMapMode, 'change', onModeChange)
    AMap.event.addDomListener(dragMarkerMode, 'change', onModeChange);
    positionPicker.start();
    map.panBy(0, 1);

    map.addControl(new AMap.ToolBar({
        liteStyle: true
    }))
});

//行政区划查询
var opts = {
    subdistrict: 1,   //返回下一级行政区
    showbiz:false  //最后一级返回街道信息
};
district = new AMap.DistrictSearch(opts);//注意：需要使用插件同步下发功能才能这样直接使用
district.search('中国', function(status, result) {
    if(status=='complete'){
        getData(result.districtList[0]);
    }
});
function getData(data,level) {
    var bounds = data.boundaries;
    if (bounds) {
        for (var i = 0, l = bounds.length; i < l; i++) {
            var polygon = new AMap.Polygon({
                map: map,
                strokeWeight: 1,
                strokeColor: '#CC66CC',
                fillColor: '#CCF3FF',
                fillOpacity: 0,
                path: bounds[i]
            });

            polygons.push(polygon);
        }
        map.setFitView();//地图自适应
    }


    //清空下一级别的下拉列表
    if (level === 'province') {
        citySelect.innerHTML = '';
        districtSelect.innerHTML = '';
        areaSelect.innerHTML = '';
    } else if (level === 'city') {
        districtSelect.innerHTML = '';
        areaSelect.innerHTML = '';
    } else if (level === 'district') {
        areaSelect.innerHTML = '';
    }

    var subList = data.districtList;
    if (subList) {
        var contentSub = new Option('--请选择--');
        var curlevel = subList[0].level;
        var curList =  document.querySelector('#' + curlevel);
        curList.add(contentSub);
        for (var i = 0, l = subList.length; i < l; i++) {
            var name = subList[i].name;
            var levelSub = subList[i].level;
            var cityCode = subList[i].citycode;
            contentSub = new Option(name);
            contentSub.setAttribute("value", levelSub);
            contentSub.center = subList[i].center;
            contentSub.adcode = subList[i].adcode;
            curList.add(contentSub);
        }
    }

}
function search(obj) {
    //清除地图上所有覆盖物
    for (var i = 0, l = polygons.length; i < l; i++) {
        polygons[i].setMap(null);
    }
    var option = obj[obj.options.selectedIndex];
    var keyword = option.text; //关键字
    var adcode = option.adcode;
    district.setLevel(option.value); //行政区级别
    district.setExtensions('all');
    //行政区查询
    //按照adcode进行查询可以保证数据返回的唯一性
    district.search(adcode, function(status, result) {
        if(status === 'complete'){
            getData(result.districtList[0],obj.id);
        }
    });
}
function setCenter(obj){
    map.setCenter(obj[obj.options.selectedIndex].center)
}