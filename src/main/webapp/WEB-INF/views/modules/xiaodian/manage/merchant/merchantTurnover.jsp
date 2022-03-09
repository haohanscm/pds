<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家营业额</title>
	<meta name="decorator" content="default"/>
    <script src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    resetTip();
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            var dataList = {
                date: formatData('${dateList}'),
                data: formatData('${amountList}')
            }
            // 指定图表的配置项和数据
            var option = {
                xAxis: {
                    type: 'category',
                    name: '日期',
                    boundaryGap: false,
                    data: dataList.date
                },
                yAxis: {
                    name:'金额',
                    type: 'value'
                },
                series: [{
                    data: dataList.data,
                    type: 'line',
                    smooth: true,
                    areaStyle: {}
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

            function formatData (str) {
                var list = str.slice(1, -2).split(',')
                list = list.map(function(item) {
                    return item.trim()
                })
                return list.reverse()
            }

            // function getVirtulData(value) {
            //     var today = new Date().getTime()
            //     var dayTime = 3600 * 24 * 1000;
            //
            //     var date = today - dayTime * value
            //     var end = today + dayTime * value
            //     var data = [];
            //     for (var time = date; time < end; time += dayTime) {
            //         data.push(echarts.format.formatTime('dd', time))
            //     }
            //     return data;
            // }

            function sliceArray(value, data) {
                if (!Array.isArray(data)) return;
                if (value > data.length) {
                    value = data.length
                }
                return data.slice(-value)
            }
            $('.btn').click(function(e) {
                if ($(this).hasClass('active')) return
                var target = e.target
                var value = target.value
                $( ".btn" ).toggleClass('active', false);
                $(this).addClass('active');
                option.xAxis.data = sliceArray(value, dataList.date)
                option.series[0].data = sliceArray(value, dataList.data)
                myChart.setOption(option);
            })
		});

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
		function toReset() {
            $("#userName").val("");
            $("#merchantId").val("");
        }
	</script>
    <style type="text/css">
        .well{
            background-color:#fff;
            margin-bottom: 10px;
            overflow: hidden;
        }
        body {
            background-color:#f3f3f4;
        }

        .itembox>ul{
            display: flex;
            justify-content: space-between;
            margin: 0 auto;
        }
        .itembox>ul>li{
            width: 30%;
            border: 1px solid #f5f5f5;
            list-style: none;
        }
        .item-title{
            height: 38px;
            background: #00bfff;
            font-size: 14px;
            color: #fff;
            line-height: 38px;
            text-align: center;
        }
        .item-cont{
            height: 60px;
            font-size: 20px;
            color: #333;
            text-align: center;
            line-height: 60px;
            border: 1px solid #e7e7eb;
        }
        .deal-cont{
            display: flex;
            justify-content: space-between;
        }
        .deal-cont-item{
            width: 30%;
            height: 220px;
            border: solid 1px #cccccc;
            border-radius: 4px;
        }
        .cont-item-title{
            width: 100%;
            height: 42px;
            border-bottom: 1px solid #cccccc;
            color: #ffffff;
            line-height: 42px;
            text-align: center;
            font-size: 14px;
            margin-bottom: 10px;
        }
        .cont-item-cont{
            height: 160px;
            width: 100%;
            float: left;
        }
        .cont-item-cont>p{
            color: #808080;
            line-height: 24px;
            text-align: center;
        }
        .btn-box {
            margin: 10px 0;
        }
        .btn-box>.btn {
            margin-right: 10px;
        }
    </style>
</head>
<body>
<%--<div class="well">--%>
    <%--首页--%>
<%--</div>--%>
<div class="well">
    <div class="itembox">
        <h4 style="margin-bottom: 10px;">今日数据</h4>
        <ul>
            <li class="item">
                <div class="item-title">今日交易金额</div>
                <div class="item-cont">${merchantPay1.totalGoodsAmount}</div>
            </li>
            <li class="item">
                <div class="item-title">今日交易笔数</div>
                <div class="item-cont">${merchantPay1.orderCount}</div>
            </li>
            <li class="item">
                <div class="item-title">今日客均单价</div>
                <div class="item-cont">${merchantPay1.averageAmount}</div>
            </li>
        </ul>
    </div>
</div>
<sys:message content="${message}"/>
<div class="well block-deal">
    <h4 style="margin-bottom: 10px;">近期交易情况</h4>
    <div class="deal-cont">
        <div class="deal-cont-item">
            <div class="cont-item-title" style="background: #50e3c2;">
                今日
            </div>
            <div class="cont-item-cont">
                <p>交易总金额：${merchantPay1.totalGoodsAmount}</p>
                <p>优惠总金额：${merchantPay1.discountAmount}</p>
                <p>实际交易金额：${merchantPay1.totalPayAmount}</p>
                <p>交易总笔数：${merchantPay1.orderCount}</p>
                <p>交易客单价：${merchantPay1.averageAmount}</p>
            </div>
        </div>
        <div class="deal-cont-item">
            <div class="cont-item-title"  style="background: #f5a623;">
                最近七天
            </div>
            <div class="cont-item-cont">
                <p>交易总金额：${merchantPay7.totalGoodsAmount}</p>
                <p>优惠总金额：${merchantPay7.discountAmount}</p>
                <p>实际交易金额：${merchantPay7.totalPayAmount}</p>
                <p>交易总笔数：${merchantPay7.orderCount}</p>
                <p>交易客单价：${merchantPay7.averageAmount}</p>
            </div>
        </div>
        <div class="deal-cont-item">
            <div class="cont-item-title" style="background: #fc5555;">
                最近一个月
            </div>
            <div class="cont-item-cont">
                <p>交易总金额：${merchantPay30.totalGoodsAmount}</p>
                <p>优惠总金额：${merchantPay30.discountAmount}</p>
                <p>实际交易金额：${merchantPay30.totalPayAmount}</p>
                <p>交易总笔数：${merchantPay30.orderCount}</p>
                <p>交易客单价：${merchantPay30.averageAmount}</p>
            </div>
        </div>
    </div>
    <div class="btn-box">
        <button class="btn" value="2">今天</button>
        <button class="btn" value="8">近七天</button>
        <button class="btn active" value="31">近30天</button>
    </div>
    <div id="main" style="width: 100%;height:300px;"></div>
</div>
</body>
</html>